package camelride;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Author: sazal
 * Date: 5/6/17.
 */
public class DownloadLogger implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Downloaded file name : " +
                exchange.getIn().getHeader("CamelFileName") + " from " + exchange.getFromEndpoint().getEndpointUri());
    }
}
