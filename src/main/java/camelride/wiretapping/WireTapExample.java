package camelride.wiretapping;

import camelride.FTPToJmsProcessor;
import camelride.logger.order.CSVOrderLogger;
import camelride.logger.order.XmlOrderLogger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class WireTapExample {
    public static void main(String[] args) throws Exception{
        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");

        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:data/inbox/recipients?noop=true")
                        .process(new FTPToJmsProcessor())
                        .to("jms:queue:incomingOrders");

                from("jms:queue:incomingOrders")
                        .wireTap("jms:orderAudit")
                        .choice()
                        .when(header("CamelFileName").endsWith(".xml"))
                        .to("jms:queue:xmlOrders")
                        .when(header("CamelFileName").endsWith(".csv"))
                        .to("jms:queue:csvOrders")
                        .otherwise()
                        .to("jms:queue:badOrders");

                from("jms:queue:csvOrders")
                        .process(new CSVOrderLogger());

                from("jms:queue:xmlOrders")
                        .process(new XmlOrderLogger());

            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
