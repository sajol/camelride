package camelride;

import camelride.logger.order.AccountingLogger;
import camelride.logger.order.CSVOrderLogger;
import camelride.logger.order.ProductionLogger;
import camelride.logger.order.XmlOrderLogger;
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
                        .filter(xpath("/order[not(@test)]"))
                        .process(new XmlOrderLogger())
                        .multicast()
                        .to("jms:queue:accounting","jms:queue:production");

                /*from("jms:queue:xmlOrders")
                        .filter(xpath("/order[not(@test)]"))
                        .process(new XmlOrderLogger())
                        .multicast()
                        .parallelProcessing().executorService(ParallelMultiCastExecutor.executor)
                        .to("jms:queue:accounting","jms:queue:production");*/


                from("jms:queue:csvOrders")
                        .process(new CSVOrderLogger());

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
