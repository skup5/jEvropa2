package jEvropa2;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Static class with functions for searching in hmtl document.
 *
 * @author Skup5
 */
public class Extractor {

    public static Elements getShowsList(Document doc) {
        return doc.select("#main").select(".show-selector ul.js-shows-list").select("li");
    }

    public static Elements getAudioItems(Document doc) {
        //return doc.select("#main .js-equalize .feed-player").select(".item ~ .audio").not(".item-active, .more");
        return doc.select("#main .js-equalize .feed-player .paginableContainer .item").select(".audio");
    }

    public static Elements getVideoItems(Document doc) {
       // return doc.select("#main .js-equalize .feed-player").select(".item ~ .video").not(".item-active, .more");
        return doc.select("#main .js-equalize .feed-player .paginableContainer .item").select(".video");
    }

    public static Element getActiveItem(Document doc) {
        return doc.select("#main .js-equalize-children").first();
    }

    public static Element getNextShowItems(Document doc) {
        return doc.select("#main .js-equalize .feed-player").select(".item ~ .more").first();
    }

    public static Element getPlayerScript(Document doc) {
        return doc.select(".jPlayerGui").first().parent().select("script").first();
    }

    private Extractor() {
    }
}
