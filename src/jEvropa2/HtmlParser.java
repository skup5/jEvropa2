package jEvropa2;

import jEvropa2.parser.ShowParser;
import jEvropa2.parser.ItemParser;
import jEvropa2.data.Item;
import jEvropa2.data.Show;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Creates sets of {@link Show} and {@link Item} from html code.
 *
 * @author Roman Zelenik
 */
public class HtmlParser {

    ShowParser showParser;
    ItemParser itemParser;

    public HtmlParser() {
        this.showParser = new ShowParser();
        this.itemParser = new ItemParser();
    }

    public Set<Show> parseShows(Elements elements) {
        Set<Show> showsSet = new LinkedHashSet<>();
        Show newShow;
        for (Element element : elements) {
            try {
                newShow = showParser.parse(element);
                showsSet.add(newShow);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return showsSet;
    }

    public Set<Item> parseAudioShowItems(Elements elements) {
        Set<Item> itemsSet = new LinkedHashSet<>();
        Item newItem;
        for (Element element : elements) {
            try {
                newItem = itemParser.parseAudio(element);
                itemsSet.add(newItem);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return itemsSet;
    }

    public Set<Item> parseVideoShowItems(Elements elements) {
        Set<Item> itemsSet = new LinkedHashSet<>();
        Item newItem;
        for (Element element : elements) {
            try {
                newItem = itemParser.parseVideo(element);
                itemsSet.add(newItem);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return itemsSet;
    }

    /**
     *
     * @param element
     * @return Item without web site url
     */
    public Item parseActiveAudioShowItem(Element element) {
        Item item = null;
        try {
            item = itemParser.parseActiveAudio(element);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public Item parseActiveVideoShowItem(Element element) {
        Item item = null;
        try {
            item = itemParser.parseActiveVideo(element);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public URL parseMp3Url(Element script) {
        URL url = null;
        try {
            url = itemParser.parseMp3Url(script.html());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }

    public URL parseMp4Url(Element script) {
        URL url = null;
        try {
            url = itemParser.parseMp4Url(script.html());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }

    public URL parseNextPageUrl(Element element) {
        URL url = null;
        try {
            url = showParser.parseNextPage(element);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }
}
