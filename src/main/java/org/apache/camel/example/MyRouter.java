package org.apache.camel.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("activemq:client?").routeId("client")
                .inOut("activemq:server1?replyTo=client.reply")
                .inOut("activemq:server2?useMessageIDAsCorrelationID=true&replyTo=client.reply");

        from("activemq:clientUseMessageID").routeId("clientUseMessageID")
                .inOut("activemq:server1?useMessageIDAsCorrelationID=true&replyTo=client.reply")
                .inOut("activemq:server2?useMessageIDAsCorrelationID=true&replyTo=client.reply");

        from("activemq:clientUseMessageIdTempReplyQueue").routeId("clientUseMessageIdTempReplyQueue")
                .inOut("activemq:server1?useMessageIDAsCorrelationID=true")
                .inOut("activemq:server2?useMessageIDAsCorrelationID=true");

        from("activemq:clientUseMessageIdButRemoveCorrelationId").routeId("clientUseMessageIdButRemoveCorrelationId")
                .removeHeader("JMSCorrelationID")
                .inOut("activemq:server1?useMessageIDAsCorrelationID=true&replyTo=client.reply")
                .inOut("activemq:server2?useMessageIDAsCorrelationID=true&replyTo=client.reply");

        from("activemq:server1").routeId("server1")
                .setBody(constant("reply1"));

        from("activemq:server2?useMessageIDAsCorrelationID=true").routeId("server2")
                .setBody(constant("reply2"));
    }
}
