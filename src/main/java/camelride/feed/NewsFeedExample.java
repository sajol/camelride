package camelride.feed;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * Author: sazal
 * Date: 5/15/17
 */
@Component
public class NewsFeedExample extends RouteBuilder {

    private final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();


    @Override
    public void configure() throws Exception {
        configureRouteFor(RSS.BBC);
        configureRouteFor(RSS.NYT);
    }


    private RouteDefinition configureRouteFor(RSS rss) {
        return from("quartz2://" + rss.getLowerCaseName() + "?cron=0/15 * * * * ?")
                .to(rss.getUrl())
                .log("Downloaded " + rss.getName() + " xml feed")
                .marshal(xmlJsonFormat)
                .log(rss.getName() + " xml feed to json conversion completed")
                .to("file:data/inbox/feed/json/" + rss.getLowerCaseName())
                .log("Saved " + rss.getName() + " json file successfully");
    }
}
