package org.apache.camel.example;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("it")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DisableJmx
public class MyRouteIT {

    @EndpointInject(uri = "activemq:client")
    ProducerTemplate tester;

    @Test
    public void testMessageIdAsCorrelationId() throws Exception {
        String reply = tester.requestBody("activemq:clientUseMessageID", "request", String.class);
        Assert.assertEquals("reply2", reply);
    }

    @Test
    public void testMessageIdAsCorrelationIdTempReplyQueue() throws Exception {
        String reply = tester.requestBody("activemq:clientUseMessageIdTempReplyQueue", "request", String.class);
        Assert.assertEquals("reply2", reply);
    }

    @Test
    public void testCorrelationId() throws Exception {
        String reply = tester.requestBody("activemq:client", "request", String.class);
        Assert.assertEquals("reply2", reply);
    }

    @Test
    public void testRemoveCorrelationId() throws Exception {
        String reply = tester.requestBody("activemq:clientUseMessageIdButRemoveCorrelationId", "request", String.class);
        Assert.assertEquals("reply2", reply);
    }

    @Test
    public void testRemoveExplisitCorrelationId() {
        Exchange replyExchange = tester.request("activemq:clientUseMessageIdButRemoveCorrelationId", e -> {
            e.getIn().setBody("request");
            e.getIn().setHeader("JMSCorrelationID", "abc");
        });
        String reply = replyExchange.getOut().getBody(String.class);
        Assert.assertEquals("reply2", reply);
    }
}
