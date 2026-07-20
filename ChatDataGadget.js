var finesse = finesse || {};
finesse.modules = finesse.modules || {};

finesse.modules.Gadget = (function ($) {

    var graylog = {
        init: function() {},
        log: function() {},
        info: function() {},
        warn: function() {},
        error: function() {},
        debug: function() {}
    };

    var user, media, mrdID = "", mediaDialogs;
    var scriptUrl;

    var storedCallData = null;
    var activeDialogCounts = {}; // { dialogId: 'chat' | 'call' }
    var webhookSent = false; // guards against the chat-end webhook firing more than once per chat

    // Webex Connect inbound webhook — fired when an agent ends a chat (dialog → WRAP_UP).
    // NOTE: the Key ships in client-side code and is publicly visible; keep it scoped to this
    // single inbound flow. Replace the placeholder below with the provided service key.

    function postChatEndWebhook(dialog) {
        // The dialog reports WRAPPING_UP on more than one change event (enter wrap-up and
        // again on end wrap-up), so send only on the first one per chat.
        if (webhookSent) {
            graylog.log("[chat-end webhook] already sent for this chat, skipping");
            return;
        }
        webhookSent = true;

        // All fields originate from the chat webPayload (same data sent to CCE on chat arrival),
        // which is cached in storedCallData. mapWebPayload expands short keys (I→decryptedLoanNumber,
        // S→fullName) and passes conversationId/customerName/userId/threadId through as-is.
        var chat = storedCallData ? mapWebPayload(storedCallData.webPayload) : {};

        var agentId = "";
        try {
            agentId = (finesse.gadget.Config && finesse.gadget.Config.id) || "";
        } catch (e) {
            graylog.warn("[chat-end webhook] agentId unavailable: " + e.message);
        }

        // If the customer ended the chat, Manage Digital Channel already sent
        // $$$$CLOSECHAT$$$$. Send action:"close" so the survey flow suppresses its own
        // (2nd) $$$$CLOSECHAT$$$$. On agent-end there was no prior CLOSECHAT, so we omit
        // action and let the survey flow send it to close the customer's chat window.
        var endedByCustomer = !!(dialog && dialog._endedByCustomer);

        var body = {
            conversationId:    chat.conversationId || (dialog && dialog._data && dialog._data.id) || "",
            loanNumber:        chat.decryptedLoanNumber || "",
            customerName:      chat.customerName || "",
            userId:            chat.userId || "",
            threadId:          chat.threadId || "",
            fullNamePlainText: chat.fullName || "",
            agentId:           agentId
        };

        if (endedByCustomer) {
            body.action = "close";
        }

        graylog.log("[chat-end webhook] posting: " + JSON.stringify(body));

        fetch(CHAT_END_WEBHOOK_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Key": CHAT_END_WEBHOOK_KEY
            },
            body: JSON.stringify(body),
            keepalive: true
        })
        .then(function (res) { graylog.log("[chat-end webhook] status: " + res.status); })
        .catch(function (err) { graylog.error("[chat-end webhook] failed: " + (err && err.message)); });
    }

    function countAdd(dialog) {
        var state = dialog._data.state;
        if (state === 'FAILED' || state === 'DROPPED') return;
        var mediaId = dialog._data.mediaProperties && dialog._data.mediaProperties.mediaId;
        activeDialogCounts[dialog._data.id] = (mediaId === mrdID) ? 'chat' : 'call';
    }

    function countRemove(dialogId) {
        delete activeDialogCounts[dialogId];
    }

    function publishCounts() {
        var chats = 0, calls = 0;
        Object.keys(activeDialogCounts).forEach(function(id) {
            if (activeDialogCounts[id] === 'chat') chats++;
            else calls++;
        });
        graylog.log('[counts] activeChats=' + chats + ' activeCalls=' + calls);
        if (window.finesseBridge && window.finesseBridge.setMediaCounts) {
            window.finesseBridge.setMediaCounts(chats, calls);
        }
    }

    function initializeChatDataLogger() {
        var graylogFactory = window.graylogLogger;
        if (graylogFactory && typeof graylogFactory.createLoggerFactory === "function") {
            graylog = graylogFactory.createLoggerFactory({
                gadgetName: "ChatDataGadget",
                remoteEnabled: true,
                level: "log",
                remoteTransport: "pubsub",
                getContext: function() {
                    return window.logContext ? Object.assign({}, window.logContext) : {};
                }
            }).createClient("chatDataGadget");
            graylog.init = function() {};
            return;
        }

        var fallbackClient = finesse && finesse.cslogger && finesse.cslogger.ClientLogger;
        if (fallbackClient && typeof fallbackClient.log === "function") {
            graylog = {
                init: function(hub, gadgetName) {
                    if (typeof fallbackClient.init === "function") {
                        fallbackClient.init(hub, gadgetName || "ChatDataGadget");
                    }
                },
                log: function(msg) { fallbackClient.log(msg); },
                info: function(msg) { fallbackClient.log(msg); },
                warn: function(msg) { fallbackClient.log(msg); },
                error: function(msg) { fallbackClient.log(msg); },
                debug: function(msg) { fallbackClient.log(msg); }
            };
            return;
        }

        graylog = {
            init: function() {},
            log: function() {},
            info: function() {},
            warn: function() {},
            error: function() {},
            debug: function() {}
        };
    }

    function callUpdate(fieldId, newValue) {
        if (!storedCallData) {
            graylog.info("[callUpdate] No stored call data, skipping");
            return;
        }
        var shortKey = getShortKey(fieldId);
        storedCallData.webPayload[shortKey] = newValue;
        graylog.info("[callUpdate] fieldId=" + fieldId + " shortKey=" + shortKey + " newValue=" + newValue);

        var UPDATE_URL     = scriptUrl + "/softphone/callData/update";
        fetch(UPDATE_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-Correlation-Id": storedCallData.callId || ""
            },
            body: JSON.stringify(storedCallData)
        })
        .then(function (response) {
            if (!response.ok) throw new Error("HTTP " + response.status);
            return response.text();
        })
        .then(function (text) {
            if (!text) {
                graylog.info("[callUpdate] Update success (empty response)");
                return;
            }
            var data = JSON.parse(text);
            graylog.info("[callUpdate] Update success: " + JSON.stringify(data));
            storedCallData = data;
            var chatData = mapWebPayload(data.webPayload);
            if (window.finesseBridge) {
                window.finesseBridge.setChatData(chatData);
            }
        })
        .catch(function (err) {
            graylog.error("[callUpdate] Update failed: " + err.message);
        });
    }

    /* ==============================
       DIALOG HANDLERS
    ============================== */

    function handleDialogAdd(dialog) {
        var dialogMediaId = dialog._data.mediaProperties.mediaId;
        graylog.info("[handleDialogAdd] Dialog arrived - dialogMediaId=" + dialogMediaId + ", expected mrdID=" + mrdID);

        countAdd(dialog);
        publishCounts();

        if (dialogMediaId !== mrdID) {
            graylog.error("[handleDialogAdd] Skipping dialog — mediaId mismatch");
            return;
        }

        graylog.info("Chat arrived!");
		webhookSent = false; // new chat — allow the chat-end webhook to fire again
		dialog.addHandler("change", handleDialogChange);

        // single chat at a time — hand the dialog to the bridge so its CLOSECHAT
        // handler can move it to WRAP_UP when the customer ends the chat
        if (window.finesseBridge && window.finesseBridge.setActiveDialog) {
            window.finesseBridge.setActiveDialog(dialog);
        }

        var callVars = dialog._data.mediaProperties;
        graylog.log("Call Vars: " + JSON.stringify(callVars));

        var callVariablesRaw = callVars.callvariables;
        graylog.log("[handleDialogAdd] callvariables raw: " + JSON.stringify(callVariablesRaw));

        var callVariablesArray = callVars.callvariables.CallVariable;
        if (!Array.isArray(callVariablesArray)) {
            graylog.warn("[handleDialogAdd] WARNING: CallVariable is not an array, got type=" + typeof callVariablesArray + ", value=" + JSON.stringify(callVariablesArray));
        } else {
            graylog.info("[handleDialogAdd] CallVariable count: " + callVariablesArray.length);
        }
        graylog.log("callVariablesArray: " + JSON.stringify(callVariablesArray));

        // Helper function
        function getCallVariable(name) {
            var item = Array.isArray(callVariablesArray) && callVariablesArray.find(cv => cv.name === name);
            var value = item && item.value ? item.value : "";
            graylog.log("[getCallVariable] " + name + " = " + (value || "(empty)"));
            return value;
        }

        var mediaResourceId = getCallVariable("user_DR_MediaResourceID");
        graylog.info("user_DR_MediaResourceID: " + mediaResourceId);

        if (!mediaResourceId) {
            graylog.warn("[handleDialogAdd] WARNING: mediaResourceId is empty — API call will proceed with empty callId");
        }

        var requestBody = JSON.stringify({ callId: mediaResourceId });
        var RETRIEVE_URL = scriptUrl + "/softphone/callData/retrieve";
        graylog.log("[handleDialogAdd] Calling Retrieve API - URL=" + RETRIEVE_URL + " X-Correlation-Id=" + mediaResourceId + " body=" + requestBody);

		fetch(RETRIEVE_URL, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-Correlation-Id": mediaResourceId
                    },
                    body: requestBody
            })
            .then(function(response) {
                if (!response.ok) throw new Error("HTTP " + response.status);
                return response.json();
            })
            .then(function(apiData) {
                graylog.log("API response: " + JSON.stringify(apiData));
                graylog.log("[handleDialogAdd] apiData keys: " + Object.keys(apiData).join(", "));

                var webPayload = apiData.webPayload;
                graylog.log("[handleDialogAdd] webPayload present=" + (webPayload != null) + " value=" + JSON.stringify(webPayload));

                storedCallData = apiData;

                var chatData = mapWebPayload(webPayload);
                graylog.log("Mapped chat data: " + JSON.stringify(chatData));

                if (window.finesseBridge) {
                    window.finesseBridge.setChatData(chatData);
                    window.finesseBridge._updateHandler = callUpdate;
                    if (window.finesseBridge.adjustHeight) {
                        graylog.debug("[handleDialogAdd] Calling finesseBridge.adjustHeight...");
                        window.finesseBridge.adjustHeight();
                    }
                }
                graylog.log("[handleDialogAdd] Data flow complete");
            })
            .catch(function(err) {
                graylog.error("Failed to fetch chat data: " + err.message);
                graylog.error("[handleDialogAdd] Error stack: " + (err.stack || "unavailable"));
            });
    }

    function handleDialogChange(dialog) {
        var dialogMediaId = dialog._data && dialog._data.mediaProperties && dialog._data.mediaProperties.mediaId;
        if (dialogMediaId !== mrdID) return;
        var state = dialog._data.state;
        graylog.info("[handleDialogChange] dialog state=" + state + " id=" + dialog._data.id);
        if (state === 'WRAPPING_UP') {
            graylog.log("agent end caught — dialog entered WRAPPING_UP id=" + dialog._data.id);
            postChatEndWebhook(dialog);
        }
    }

    function handleDialogDelete(dialog) {
        graylog.info("Chat ended");

        countRemove(dialog._data.id);
        publishCounts();

        storedCallData = null;
        if (window.finesseBridge) {
            window.finesseBridge._updateHandler = null;
            window.finesseBridge.setChatData(null);
            if (window.finesseBridge.setActiveDialog) {
                window.finesseBridge.setActiveDialog(null);
            }
             if (window.finesseBridge.adjustHeight) {
                        graylog.debug("[handleDialogAdd] Calling finesseBridge.adjustHeight...");
                        window.finesseBridge.adjustHeight();
            }
        }
    }

    function handleDialogsLoad() {
        graylog.info("Dialogs loaded");

        var dialogs = mediaDialogs.getCollection();
        var dialogIds = Object.keys(dialogs);
        graylog.info("[handleDialogsLoad] Dialog count: " + dialogIds.length + " ids=[" + dialogIds.join(", ") + "]");

        for (var id in dialogs) {
            graylog.log("[handleDialogsLoad] Processing dialog id=" + id);
            handleDialogAdd(dialogs[id]);
        }
    }

    function loadDialogs() {
        graylog.log("Loading dialogs...");

        mediaDialogs = media.getMediaDialogs({
            onCollectionAdd: handleDialogAdd,
            //onCollectionChange: handleDialogChange,
            onCollectionDelete: handleDialogDelete,
            onLoad: handleDialogsLoad
        });
    }




    /* ==============================
       MEDIA HANDLERS
    ============================== */

    function handleMediaLoad(_media) {
        graylog.log("Media loaded");

        media = _media;
        loadDialogs();
    }

    function handleMediaChange(_media) {
        if (_media._data.id === mrdID) {
            graylog.info("Media state changed: " + _media.getState());
        }
    }

    function handleMediaError(err) {
        graylog.error("Media error: " + JSON.stringify(err));
    }

    /* ==============================
       USER HANDLERS
    ============================== */

    function handleUserLoad() {
        graylog.log("User loaded");
        //window.finesseBridge.setTeam("sales")

        user.getMediaList({
            onLoad: function (mediaList) {

                graylog.log("MediaList loaded");

                mediaList.getMedia({
                    id: mrdID,
                    onLoad: handleMediaLoad,
                    onChange: handleMediaChange,
                    onError: handleMediaError
                });

            }
        });
    }

    function handleUserChange() {
        var state = user.getState();
        graylog.info("User state changed: " + state);

        if (state === 'WRAP_UP' && storedCallData !== null) {
            graylog.info("agent moved to WRAP_UP — confirmed caused by chat end, callId=" + storedCallData.callId);
        }
    }


    /* ==============================
       INIT
    ============================== */

    return {
        init: function (chatMrdId, _scriptUrl) {

            scriptUrl = _scriptUrl || "";
			mrdID = chatMrdId;
            finesse.clientservices.ClientServices.init(finesse.gadget.Config);

            initializeChatDataLogger();
            graylog.init(gadgets.Hub, "ChatDataGadget");
			graylog.info("MrdID:"+mrdID);
            graylog.info("chat Data Gadget initializing...");
             if (window.__vueHubConnected) {
                        window.__vueHubConnected();
             }


            user = new finesse.restservices.User({
                id: finesse.gadget.Config.id,
                onLoad: handleUserLoad,
                onChange: handleUserChange
            });


        }
    };

}(jQuery));