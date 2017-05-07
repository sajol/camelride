package camelride.recipientlist;

import camelride.FTPToJmsProcessor;
import camelride.logger.order.AccountingLogger;
import camelride.logger.order.ProductionLogger;
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
public class RecipientListExample {
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
                        .choice()
                        .when(header("CamelFileName").endsWith(".xml"))
                        .to("jms:queue:xmlOrders");

                from("jms:queue:xmlOrders")
                        .bean(RecipientListBean.class);


                from("jms:queue:accounting")
                        .process(new AccountingLogger());

                from("jms:queue:production")
                        .process(new ProductionLogger());

            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
