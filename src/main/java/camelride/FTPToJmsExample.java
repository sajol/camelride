package camelride;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * Author: sazal
 * Date: 5/4/17.
 */
public class FTPToJmsExample {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");

        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                    /*from("ftp:example.com/orders?username=***&password=***")
                        .process(new FTPToJmsProcessor())
                        .to("jms:incomingOrders");*/
                from("file:data/inbox?noop=true")
                        .process(new FTPToJmsProcessor())
                        .to("jms:queue:incomingOrders");

                from("jms:queue:incomingOrders")
                        .choice()
                        .when(header("CamelFileName").endsWith(".xml"))
                        .to("jms:queue:xmlOrders")
                        .when(header("CamelFileName").endsWith(".csv"))
                        .to("jms:queue:csvOrders");

                from("jms:queue:xmlOrders")
                        .process(exchange ->
                                System.out.println("Received XML order : " + exchange.getIn().getHeader("CamelFileName")));

                from("jms:queue:csvOrders")
                        .process(exchange ->
                                System.out.println("Received CSV order : " + exchange.getIn().getHeader("CamelFileName")));

            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
