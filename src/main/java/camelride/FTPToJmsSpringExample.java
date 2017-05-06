package camelride;

import org.apache.camel.builder.RouteBuilder;

/**
 * Author: sazal
 * Date: 5/6/17.
 */
public class FTPToJmsSpringExample extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        /*from("ftp:example.com/orders?username=***&password=***")
                        .process(new FTPToJmsProcessor())
                        .to("jms:incomingOrders");*/
        from("file:data/inbox?noop=true")
                .process(new FTPToJmsProcessor())
                .to("jms:queue:incomingOrders");
    }
}
