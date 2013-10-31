<!DOCTYPE html>
<html>
<head>
    <title>Presenta - ${presentation_name}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dojo/dojo.js.uncompressed.js"></script>
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };

        dojo.require("dojox.cometd");

        var u = 'test1';
        var p = 'presentation1'

        var presentation = {

            _participant: null,
            _presentation: null,
            _connected: false,
            _disconnecting: false,
            _messagesSubscription: null,
            _membersSubscription: null,

            init: function() {
                presentation._participant = u;
                presentation._presentation = p;


                presentation.join(u);

                dojo.query("#sendButton").onclick(function(e) {
                    presentation.chat();
                });
            },

            join: function(participant) {
                presentation._disconnecting = false;

                // dojox.cometd.websocketEnabled = true;
                dojox.cometd.init("http://localhost:8080/cometd");

                presentation._participant = participant;
            },

            disconnect: function() {
                dojox.cometd.disconnect();
            },

            _unsubscribe: function() {
                if (presentation._messagesSubscription) {
                    dojox.cometd.unsubscribe(presentation._messagesSubscription);
                }
                presentation._messagesSubscription = null;

                if (presentation._membersSubscription) {
                    dojox.cometd.unsubscribe(presentation._membersSubscription);
                }
                presentation._membersSubscription = null;
            },

            _subscribe: function() {
                presentation._messagesSubscription = dojox.cometd.subscribe('/messages/' + presentation._presentation, presentation.receive);
                presentation._membersSubscription = dojox.cometd.subscribe('/members/' + presentation._presentation, presentation.members);
            },

            leave: function() {
                dojox.cometd.batch(function() {
                    dojox.cometd.publish("/messages/" + presentation._presentation, { user: presentation._participant, membership: 'leave', chat: presentation._participant + " has left" });
                    presentation._unsubscribe();
                });
                dojox.cometd.disconnect();

                presentation._participant = null;
                presentation._disconnecting = true;
            },

            chat: function() {
                var text = dojo.byId('message').value;
                dojox.cometd.publish('/messages/' + presentation._presentation, { user: presentation._participant, chat: text });
            },

            receive: function(message) {
                var fromUser = message.data.user;
                var membership = message.data.join || message.data.leave;
                var text = message.data.chat;

                dojo.create("li", { innerHTML: fromUser + ":" + text }, dojo.byId("messages"));
            },

            members: function(message) {
                var participantsList = dojo.byId("participants");
                participantsList.innerHTML = "";

                for(i in message.data) {
                    dojo.create("li", { innerHTML: message.data[i] }, participantsList);
                }
            },

            _connectionInitialized: function() {
                dojox.cometd.batch(function() {
                    presentation._subscribe();
                    dojox.cometd.publish('/service/messages', {user: presentation.username, message: presentation._participant + ' has joined'});
                });
            },

            _connectionEstablished: function() {
                presentation.receive({ data: { user: 'system', chat: 'Connection to Server Opened' } });

                dojox.cometd.publish('/service/members', { user: presentation._participant, presentation: '/messages/' + presentation._presentation });
            },

            _connectionBroken: function() {
                presentation.receive({ data: { user: 'system', chat: 'Connection to Server Broken' } });
            },

            _connectionClosed: function() {
                presentation.receive({ data: { user: 'system', chat: 'Connection to Server Closed' } });
            },

            _metaHandshake: function(message) {
                if (message.successful) {
                    presentation._connectionInitialized();
                }
            },

            _metaConnect: function(message) {
                if (presentation._disconnecting) {
                    presentation._connected = false;
                    presentation._connectionClosed();
                } else {
                    var wasConnected = presentation._connected;
                    presentation._connected = message.successful === true;
                    if (!wasConnected && presentation._connected) {
                        presentation._connectionEstablished();
                    } else if (wasConnected && !presentation._connected) {
                        presentation._connectionBroken();
                    }
                }
            }
        };

        dojo.addOnLoad(presentation, "init");
        dojox.cometd.addListener("/meta/handshake", presentation, presentation._metaHandshake);
        dojox.cometd.addListener("/meta/connect", presentation, presentation._metaConnect);
        dojo.addOnUnload(presentation, "disconnect");

    </script>
</head>
<body>
    <div width="100%" height="100%">
        <canvas id="presentation_canvas" width="100%" height="100%"/>
    </div>

    <ul id="messages">

    </ul>
    <ul id="participants">

    </ul>
    <div>
        <input id="message" type="text" />
        <button id="sendButton" class="button">Send</button>
    </div>
</body>
</html>
