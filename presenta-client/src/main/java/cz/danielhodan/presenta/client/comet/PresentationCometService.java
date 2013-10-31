package cz.danielhodan.presenta.client.comet;

import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;
import org.cometd.server.authorizer.GrantAuthorizer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named
@Singleton
@Service
public class PresentationCometService {

    @Inject
    BayeuxServer bayeux;
    @Session
    ServerSession serverSession;

    private final ConcurrentMap<String, Map<String, String>> _members = new ConcurrentHashMap<String, Map<String, String>>();

    @Configure( {"/messages/**", "/members/**"})
    public void configureMessage(ConfigurableServerChannel channel) {
        channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
    }

    @Configure("/service/members")
    public void configureMembers(ConfigurableServerChannel channel) {
        channel.setPersistent(true);
        channel.addAuthorizer(GrantAuthorizer.GRANT_PUBLISH);
    }

    @Listener("/service/members")
    public void listenerMembers(ServerSession client, ServerMessage message) {
        final Map<String, Object> data = message.getDataAsMap();

        final String presentation = ((String)data.get("presentation")).substring("/messages/".length());
        Map<String, String> roomMembers = _members.get(presentation);
        if (roomMembers == null) {
            Map<String, String> newRoom = new ConcurrentHashMap<String, String>();
            roomMembers = _members.putIfAbsent(presentation, newRoom);
            if (roomMembers == null) {
                roomMembers = newRoom;
            }
        }

        final Map<String, String> members = roomMembers;
        members.put((String)data.get("user"), client.getId());

        client.addListener(new ServerSession.RemoveListener() {
            public void removed(ServerSession session, boolean timeout) {
                members.values().remove(session.getId());

                ClientSessionChannel channel = serverSession.getLocalSession().getChannel("/members/" + presentation);
                channel.publish(members.keySet());
            }
        });

        ClientSessionChannel channel = serverSession.getLocalSession().getChannel("/members/" + presentation);
        channel.publish(members.keySet());
    }
}
