package camelride.quartz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Author: sazal
 * Date: 5/14/17.
 */

@Component
public class RandomNumberQuartzRouteBuilder extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        from("quartz2://report?cron=0/30 * * * * ?")
                .to("https://qrng.anu.edu.au/API/jsonI.php?length=1&type=uint8&#8217")
                .to("stream:out");
    }
}
