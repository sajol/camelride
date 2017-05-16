package camelride.feed;

/**
 * Author: sazal
 * Date: 5/16/17
 */
public enum RSS {
    BBC("BBC", "http://feeds.bbci.co.uk/news/world/rss.xml"),
    NYT("NYT", "http://rss.nytimes.com/services/xml/rss/nyt/World.xml");

    private String name;
    private String url;

    RSS(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getLowerCaseName() {
        return name.toLowerCase();
    }

    public String getUrl() {
        return url;
    }
}
