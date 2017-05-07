package camelride.logger.order;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class XmlOrderLogger implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Received XML order : " + exchange.getIn().getHeader("CamelFileName"));
    }
}
