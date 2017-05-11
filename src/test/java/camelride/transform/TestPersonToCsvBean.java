package camelride.transform;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.io.File;

/**
 * Author: sazal
 * Date: 5/8/17.
 */
public class TestPersonToCsvBean extends CamelTestSupport {

    @Test
    public void testPersonToCsvBean() throws Exception {

        String person = "Nafi:32:nafi.karim@cefalo.com";
        template.sendBodyAndHeader("direct:start", person, "Date", "2017-17-05");

        File file = new File("data/persons/report-2017-17-05.csv");
        assertTrue("File should exist", file.exists());

        String body = context.getTypeConverter().convertTo(String.class, file);
        assertEquals("Nafi,32,nafi.karim@cefalo.com", body);

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .bean(new PersonToCsvBean())
                        .to("file://data/persons?fileName=report-${header.Date}.csv");
            }
        };
    }
}
