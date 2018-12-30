import cz.skup5.jEvropa2.HtmlParser;
import cz.skup5.jEvropa2.dao.ItemDao;
import cz.skup5.jEvropa2.data.E2Data;
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

    private static final String OFFLINE_HTML_SITE_SHOWS = "evropa2-shows.html";
    private static final String OFFLINE_HTML_SITE_ITEM = "evropa2-zpatky-do-minulosti.html";
    private static final String BASE_URI = "https://evropa2.cz";
    private static final String DATA_QUERY_PATH = "graphql";

    /**
     * @param args the command line arguments
     * @throws java.io.IOException if some error was occurred while file reading or parsing
     */
    public static void main(String[] args) throws IOException {
        final HtmlParser parser = new HtmlParser();
        Document site;

//        site = loadSite(OFFLINE_HTML_SITE_ITEM, "utf-8");
//        site.setBaseUri(BASE_URI);

//        Elements shows = Extractor.getShowsList(site);
//        printShows(parser, Extractor.getShowsLabel(site), shows);

//        Elements showItems = Extractor.getAudioItems(site);
//        printItems(parser, showItems);

//        System.out.println(parser.parseActiveAudioShowItem(Extractor.getActiveItem(site)).info());

        Show ranniShow = new Show(17, "Ranní show", E2Data.getEMPTY_URI(), "ranni-show");
        for (Item item : ItemDao.INSTANCE.get(ranniShow, 1, 6))
            System.out.println(item.info());

//        System.out.println(parser.parseNextPageUrl(nextShowItems));
    }

    private static Document loadSite(String fileName, String charsetName) throws FileNotFoundException, IOException {
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
            System.out.println(item.info());
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
