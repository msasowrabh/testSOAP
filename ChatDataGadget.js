var finesse = finesse || {};
finesse.modules = finesse.modules || {};

finesse.modules.Gadget = (function ($) {

    var user, media, mrdID = "", mediaDialogs, clientLogs;
    var cachedToken = null;
    var tokenExpiry = 0;

    var OKTA_TOKEN_URL = "https://assurant.oktapreview.com/oauth2/aus26n6j3p4xj3UEE0h8/v1/token";
    var RETRIEVE_URL   = "https://intra-api-gl-ccd-model.assurant.com/ccd/shared/liason/v1/retrieve";
    var SUBSCRIPTION_KEY = "bfb35b140a194dd7b88f8305d45366bc";
    var CLIENT_ID      = "0oa26n6exiojXTKSC0h8";
    var CLIENT_SECRET  = "UO8E5wbWsqTimRnXGyInnMweUGAISZj11WIXxTBQXDMg_iTDBXTqnTh2N8TPfY7R";

    function getAuthToken() {
        if (cachedToken && Date.now() < tokenExpiry - 60000) {
            clientLogs.log("[getAuthToken] Using cached token, expires in " + Math.round((tokenExpiry - Date.now()) / 1000) + "s");
            return Promise.resolve(cachedToken);
        }

        clientLogs.log("[getAuthToken] No valid cached token, fetching new token from Okta...");

        var params = new URLSearchParams();
        params.append("grant_type", "client_credentials");
        params.append("client_id", CLIENT_ID);
        params.append("client_secret", CLIENT_SECRET);
        params.append("scope", "claim");

        return fetch(OKTA_TOKEN_URL, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params
        })
        .then(function(response) {
            clientLogs.log("[getAuthToken] Okta response status: " + response.status);
            if (!response.ok) throw new Error("Okta token request failed: " + response.status);
            return response.json();
        })
        .then(function(tokenData) {
            cachedToken = tokenData.access_token;
            tokenExpiry = Date.now() + (tokenData.expires_in * 1000);
            clientLogs.log("[getAuthToken] Token received, expires_in=" + tokenData.expires_in + "s, token_type=" + tokenData.token_type);
            return cachedToken;
        });
    }

    /* ==============================
       DIALOG HANDLERS
    ============================== */

    function handleDialogAdd(dialog) {
        var dialogMediaId = dialog._data.mediaProperties.mediaId;
        clientLogs.log("[handleDialogAdd] Dialog arrived - dialogMediaId=" + dialogMediaId + ", expected mrdID=" + mrdID);

        if (dialogMediaId !== mrdID) {
            clientLogs.log("[handleDialogAdd] Skipping dialog — mediaId mismatch");
            return;
        }

        clientLogs.log("Chat arrived!");

        var callVars = dialog._data.mediaProperties;
        clientLogs.log("Call Vars: " + JSON.stringify(callVars));

        var callVariablesRaw = callVars.callvariables;
        clientLogs.log("[handleDialogAdd] callvariables raw: " + JSON.stringify(callVariablesRaw));

        var callVariablesArray = callVars.callvariables.CallVariable;
        if (!Array.isArray(callVariablesArray)) {
            clientLogs.log("[handleDialogAdd] WARNING: CallVariable is not an array, got type=" + typeof callVariablesArray + ", value=" + JSON.stringify(callVariablesArray));
        } else {
            clientLogs.log("[handleDialogAdd] CallVariable count: " + callVariablesArray.length);
        }
        clientLogs.log("callVariablesArray: " + JSON.stringify(callVariablesArray));

        // Helper function
        function getCallVariable(name) {
            var item = Array.isArray(callVariablesArray) && callVariablesArray.find(cv => cv.name === name);
            var value = item && item.value ? item.value : "";
            clientLogs.log("[getCallVariable] " + name + " = " + (value || "(empty)"));
            return value;
        }

        var mediaResourceId = getCallVariable("user_DR_MediaResourceID");
        clientLogs.log("user_DR_MediaResourceID: " + mediaResourceId);

        if (!mediaResourceId) {
            clientLogs.log("[handleDialogAdd] WARNING: mediaResourceId is empty — API call will proceed with empty callId");
        }

        getAuthToken()
            .then(function(token) {
                var requestBody = JSON.stringify({ callId: mediaResourceId });
                clientLogs.log("[handleDialogAdd] Calling Retrieve API - URL=" + RETRIEVE_URL + " X-Correlation-Id=" + mediaResourceId + " body=" + requestBody);
                return fetch(RETRIEVE_URL, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + token,
                        "Ocp-Apim-Subscription-Key": SUBSCRIPTION_KEY,
                        "X-Correlation-Id": mediaResourceId
                    },
                    body: requestBody
                });
            })
            .then(function(response) {
                clientLogs.log("[handleDialogAdd] Retrieve API response status: " + response.status + " ok=" + response.ok);
                if (!response.ok) throw new Error("Retrieve API responded with status " + response.status);
                return response.json();
            })
            .then(function(apiData) {
                clientLogs.log("API response: " + JSON.stringify(apiData));
                clientLogs.log("[handleDialogAdd] apiData keys: " + Object.keys(apiData).join(", "));

                var webPayload = apiData.webPayload;
                clientLogs.log("[handleDialogAdd] webPayload present=" + (webPayload != null) + " value=" + JSON.stringify(webPayload));

                var chatData = mapWebPayload(webPayload);
                clientLogs.log("Mapped chat data: " + JSON.stringify(chatData));
                clientLogs.log("[handleDialogAdd] chatData keys: " + Object.keys(chatData).join(", "));

                clientLogs.log("[handleDialogAdd] Calling finesseBridge.setChatData...");
                window.finesseBridge.setChatData(chatData);
                if (window.finesseBridge && window.finesseBridge.adjustHeight) {
                    clientLogs.log("[handleDialogAdd] Calling finesseBridge.adjustHeight...");
                    window.finesseBridge.adjustHeight();
                }
                clientLogs.log("[handleDialogAdd] Data flow complete");
            })
            .catch(function(err) {
                clientLogs.log("Failed to fetch chat data: " + err.message);
                clientLogs.log("[handleDialogAdd] Error stack: " + (err.stack || "unavailable"));
            });
    }

    function handleDialogDelete(dialog) {
        clientLogs.log("Chat ended");

        window.finesseBridge.setChatData(null);
    }

    function handleDialogsLoad() {
        clientLogs.log("Dialogs loaded");

        var dialogs = mediaDialogs.getCollection();
        var dialogIds = Object.keys(dialogs);
        clientLogs.log("[handleDialogsLoad] Dialog count: " + dialogIds.length + " ids=[" + dialogIds.join(", ") + "]");

        for (var id in dialogs) {
            clientLogs.log("[handleDialogsLoad] Processing dialog id=" + id);
            handleDialogAdd(dialogs[id]);
        }
    }

    function loadDialogs() {
        clientLogs.log("Loading dialogs...");

        mediaDialogs = media.getMediaDialogs({
            onCollectionAdd: handleDialogAdd,
            onCollectionDelete: handleDialogDelete,
            onLoad: handleDialogsLoad
        });
    }




    /* ==============================
       MEDIA HANDLERS
    ============================== */

    function handleMediaLoad(_media) {
        clientLogs.log("Media loaded");

        media = _media;
        loadDialogs();
    }

    function handleMediaChange(_media) {
        if (_media._data.id === mrdID) {
            clientLogs.log("Media state changed: " + _media.getState());
        }
    }

    function handleMediaError(err) {
        clientLogs.log("Media error: " + JSON.stringify(err));
    }

    /* ==============================
       USER HANDLERS
    ============================== */

    function handleUserLoad() {
        clientLogs.log("User loaded");
        //window.finesseBridge.setTeam("sales")

        user.getMediaList({
            onLoad: function (mediaList) {

                clientLogs.log("MediaList loaded");

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
        clientLogs.log("User state changed: " + user.getState());
    }


    /* ==============================
       INIT
    ============================== */

    return {
        init: function (chatMrdId) {

			mrdID = chatMrdId;
            finesse.clientservices.ClientServices.init(finesse.gadget.Config);

            // ✅ Initialize logger FIRST
            clientLogs = finesse.cslogger.ClientLogger;
            clientLogs.init(gadgets.Hub, "MSAChatDataGadget");
			clientLogs.log("MrdID:"+mrdID);
            clientLogs.log("chat Data Gadget initializing...");
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