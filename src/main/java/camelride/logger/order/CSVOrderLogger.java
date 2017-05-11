package camelride.logger.order;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class CSVOrderLogger implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Received CSV order : " + exchange.getIn().getHeader("CamelFileName"));
    }
}
