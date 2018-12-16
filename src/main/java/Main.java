import cz.skup5.jEvropa2.Extractor;
import cz.skup5.jEvropa2.HtmlParser;
import cz.skup5.jEvropa2.data.Item;
import cz.skup5.jEvropa2.data.Show;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Skup5
 */
public class Main {

    public static final String OFFLINE_HTML_SITE = "evropa2-shows.html";
    private static final String BASE_URI = "https://evropa2.cz";

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        final HtmlParser parser = new HtmlParser();
        Document site;

        site = loadSite(OFFLINE_HTML_SITE, "utf-8");
        site.setBaseUri(BASE_URI);

//        Elements shows = Extractor.getShowsList(site);
//        printShows(parser, Extractor.getShowsLabel(site), shows);

        Elements showItems = Extractor.getAudioItems(site);
        printItems(parser, showItems);

//        System.out.println(parser.parseActiveAudioShowItem(Extractor.getActiveItem(site)).info());

//        System.out.println("--------------------");
//        site = HttpRequests.httpGetSite(url);
//        System.out.println(parser.parseMp3Url(Extractor.getPlayerScript(site)));

        /*elements = Extractor.getVideoItems(site);
        for (Item item : parser.parseVideoShowItems(elements)) {
            System.out.println(item.info());
        }*/
//        System.out.println(parser.parseActiveVideoShowItem(Extractor.getActiveItem(site)).info());

//        Element nextShowItems = Extractor.INSTANCE.getNextShowItems(site);
//        System.out.println(parser.parseNextPageUrl(nextShowItems));
    }

    private static Document loadSite(String fileName, String charsetName) throws IOException {
        Document document;
        File htmlFile = new File(fileName);
        if (htmlFile.exists()) {
            document = Jsoup.parse(htmlFile, charsetName);
            return document;
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

    private static void printItems(HtmlParser parser, Elements showItems) {
        System.out.println("Items:");
//        printElements(showItems);
        for (Item item : parser.parseAudioShowItems(showItems)) {
            System.out.println(item);
        }
    }

    private static void printShows(HtmlParser parser, String title, Elements shows) {
        System.out.println(title + ":");
//        printElements(shows);
        for (Show show : parser.parseShows(shows)) {
            System.out.println(show.info());
        }
    }

    private static void printElements(Elements elements) {
        for (Element e : elements) {
            //if(link.attr("href", "/mp3-archiv/*"))
            //String s = e.attr("href");
            //if(s.contains("mp3-archiv")){
            System.out.println(e);
            //}

        }
    }

}
