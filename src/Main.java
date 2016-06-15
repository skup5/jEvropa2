
import jEvropa2.Extractor;
import jEvropa2.HtmlParser;
import jEvropa2.data.Item;
import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Skup5
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String url = "";
        String urlE2 = "https://evropa2.cz";
        String urlShows = "/shows/";
        String urlNextMp3 = "/srv/www/content/pub/cs/tym-a-porady/mp3-archiv-list/?cat=100&pg=2";
        String mp3Source = "https://m.static.lagardere.cz/evropa2/audio/2016/02/20160225-Meteorit.mp3";
        mp3Source = "20160225-Meteorit.mp3";
        Document site;

        /*site = loadSite("Shows - Evropa 2.html", "utf-8");
         site.setBaseUri(urlE2);
        
         Elements shows = Extractor.getShowsList(site);
         for (Show show : new HtmlParser().parseShows(shows)) {
         System.out.println(show.info());
         }
         */
        site = loadSite("Ranni show - Evropa 2.html", "utf-8");
        site.setBaseUri(urlE2);

        Elements showItems = Extractor.getShowItemsList(site);
        for (Item item : new HtmlParser().parseShowItems(showItems)) {
            System.out.println(item.info());
        }
        //printElements(showItems);
//        System.out.println(findMP3(Extractor.getPlayerScript(site).html()));
    }

    public static void playMP3(String source) throws MalformedURLException {
        File f = new File(source);
        if (f.exists()) {
            System.out.println("exists");
        }
        new MP3Player(f).play();
    }

    public static String findMP3(String script) {
        int end = script.indexOf(".mp3") + 4;
        int start = script.lastIndexOf("http", end);
        String url = script.substring(start, end);
        url = url.replace("\\", "");
        return url;
    }

    public static String findMP3_2(Document doc) {
        String script = doc.select(".jPlayerGui").first().parent().select("script").html();
        script = script.substring(script.indexOf("function setMediaFunc"));
        String url = script.substring(script.indexOf("http"), script.indexOf(".mp3") + 4);
        url = url.replace("\\", "");
        return url;
    }

    public static Document loadSite(String fileName, String charsetName) throws IOException {
        Document document;
        File htmlFile = new File(fileName);
        if (htmlFile.exists()) {
            document = Jsoup.parse(htmlFile, charsetName);
            return document;
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

    public static Document httpPostNextRecords(String url, String cat, String pg) throws IOException {
        String rate, tag;
        rate = tag = "";
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += "?";
        if (pg == null) {
            pg = "1";
        }
        url += "pg=" + pg;
        if (cat == null) {
            cat = "";
        } else {
            url += "&cat=" + cat;
        }
        return Jsoup.connect(url).data("rate", rate, "cat", cat, "tag", tag).post();
    }

    public static void printElements(Elements elements) {
        for (Element e : elements) {
            //if(link.attr("href", "/mp3-archiv/*"))
            //String s = e.attr("href");
            //if(s.contains("mp3-archiv")){
            System.out.println(e);
            //}

        }
    }

    public static void printLinksAndCategory(Document doc) {
        Elements links = doc.select("li a");
        Elements category = Extractor.getCategoryList(doc);
        Elements archive = Extractor.getArchiveCategory(doc);
        //printElements(doc.select("li[class=archive]"));
        System.out.println("links: (" + links.size() + ")\n");
        System.out.println("kategorie: (" + category.size() + ")");
        printElements(category);
        System.out.println("archive: (" + archive.size() + ")");
        printElements(archive);

    }
}
