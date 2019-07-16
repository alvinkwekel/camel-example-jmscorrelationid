Incoming JMSCorrelationID is passed along when useMessageIDAsCorrelationID

+-+            +-+            +-+
| | +-> Q1 +-> | | +-> Q3 +-> | |
|A|            |B|            |C|
| | <-+ Q2 <-+ | | <-+ Q4 <-+ | |
+-+            +-+            +-+

JMS request goes from A to B to C and the reply back from C to B to A.

A useMessageIDAsCorrelationID=false
B useMessageIDAsCorrelationID=true
C useMessageIDAsCorrelationID=false

The JMSCorrelationID assigned by A is passed along to C via Camel message headers when useMessageIDAsCorrelationID is set on B's request.
B is expecting the JMSMessageID to be used as JMSCorrelationID on the reply but since the JMSCorrelationID
is actually provided C could decide to give precedence to the JMSCorrelationID rendering B's message selector to not match.

The only way this would work as expected is when C is also set to useMessageIDAsCorrelationID
to always prefer the JMSMessageID. But consider situations where C is not under your control
and/or implemented in a different technology than Camel.

I'd argue the JMSCorrelationID should be made null when the useMessageIDAsCorrelationID is set on a provider.

This issue seems to be closely related to https://issues.apache.org/jira/browse/CAMEL-2249

