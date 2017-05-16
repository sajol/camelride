package camelride.quartz;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Author: sazal
 * Date: 5/14/17.
 */

@Component
public class RandomNumberQuartzRouteBuilder extends RouteBuilder{
    @Override
    public void configure() throws Exception {

        XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
        xmlJsonFormat.setEncoding("UTF-8");
        xmlJsonFormat.setForceTopLevelObject(true);
        xmlJsonFormat.setTrimSpaces(true);
        xmlJsonFormat.setRootName("random");
        xmlJsonFormat.setSkipNamespaces(true);
        xmlJsonFormat.setRemoveNamespacePrefixes(true);
        xmlJsonFormat.setExpandableProperties(Arrays.asList("d", "value"));

        from("quartz2://report?cron=0/15 * * * * ?")
                .to("https://qrng.anu.edu.au/API/jsonI.php?length=1&type=uint8&#8217")
                .process(exchange -> System.out.println(exchange.getIn().getBody()))
                .unmarshal(xmlJsonFormat)
                .to("file:data/inbox/random/xml");
    }
}
