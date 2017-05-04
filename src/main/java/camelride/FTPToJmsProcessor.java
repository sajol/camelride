package camelride;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Author: sazal
 * Date: 5/4/17.
 */
public class FTPToJmsProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("File name : " +
                exchange.getIn().getHeader("CamelFileName"));
    }
}
