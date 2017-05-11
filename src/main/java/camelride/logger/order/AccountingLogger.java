package camelride.logger.order;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class AccountingLogger implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Accounting received : " + exchange.getIn().getHeader("CamelFileName"));
    }
}
