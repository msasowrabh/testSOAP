var finesse = finesse || {};
finesse.modules = finesse.modules || {};

finesse.modules.Gadget = (function ($) {

    var user, media, mrdID = "", mediaDialogs, clientLogs;

    /* ==============================
       DIALOG HANDLERS
    ============================== */

    function handleDialogAdd(dialog) {
        if (dialog._data.mediaProperties.mediaId !== mrdID) {
            return;
        }

        clientLogs.log("Chat arrived!");

        var callVars = dialog._data.mediaProperties;
        clientLogs.log("Call Vars: " + JSON.stringify(callVars));
        clientLogs.log("expandedCallVariables: " + JSON.stringify(callVars.expandedCallVariables));
        var callVariablesArray = callVars.callvariables.CallVariable;
        clientLogs.log("callVariablesArray: " + JSON.stringify(callVariablesArray));

        // Helper function
        function getCallVariable(name) {
            var item = callVariablesArray.find(cv => cv.name === name);
            return item && item.value ? item.value : "Unknown";
        }

        var customerName = getCallVariable("callVariable1");
        var customerNumber = getCallVariable("callVariable2");
		var webPayload1 =getCallVariable("user.webPayload1");
		var webPayload2 = getCallVariable("user.webPayload2");
		var webPayload3 =getCallVariable("user.webPayload3");

        // Extract ECC webPayload variables
       /* var eccData = callVars.expandedCallVariables && callVars.expandedCallVariables.expandedCallVariable;
        var eccVars = eccData ? (Array.isArray(eccData) ? eccData : [eccData]) : [];
        clientLogs.log("eccVars: " + JSON.stringify(eccVars));

        function getECCVariable(name) {
            var item = eccVars.find(function(v) { return v.name === name; });
            return item && item.value ? item.value : "";
        }

        var webPayload1 = getECCVariable("user.webPayload1");
        var webPayload2 = getECCVariable("user.webPayload2");
        var webPayload3 = getECCVariable("user.webPayload3");*/

        clientLogs.log("webPayload1: " + webPayload1);
        clientLogs.log("webPayload2: " + webPayload2);
        clientLogs.log("webPayload3: " + webPayload3);
		
		

        var chatData=decodeAndMap(webPayload1, webPayload2, webPayload3);
        clientLogs.log("Decoded chat data: " + JSON.stringify(chatData));

        window.finesseBridge.setChatData(chatData);
        // Adjust height through bridge which will calculate based on content
        if (window.finesseBridge && window.finesseBridge.adjustHeight) {
          window.finesseBridge.adjustHeight();
        }
    }

    function handleDialogDelete(dialog) {
        clientLogs.log("Chat ended");

        window.finesseBridge.setChatData(null);
    }

    function handleDialogsLoad() {
        clientLogs.log("Dialogs loaded");

        var dialogs = mediaDialogs.getCollection();

        for (var id in dialogs) {
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