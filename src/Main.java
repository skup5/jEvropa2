
import jEvropa2.Extractor;
import jEvropa2.HtmlParser;
import jEvropa2.data.Item;
//import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
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
        Elements elements;
        HtmlParser parser = new HtmlParser();

        /*site = loadSite("Shows - Evropa 2.html", "utf-8");
         site.setBaseUri(urlE2);
        
         Elements shows = Extractor.getShowsList(site);
         for (Show show : new HtmlParser().parseShows(shows)) {
         System.out.println(show.info());
         }
         */
        //site = loadSite("Ranni show - Evropa 2.html", "utf-8");
        //site = loadSite("Music chart - Evropa 2.html", "utf-8");
        site = loadSite("Fakt No Koment - Evropa 2.html", "utf-8");
        site.setBaseUri(urlE2);

        /* 
        Elements showItems = Extractor.getAudioItems(site);
         printElements(showItems);
         for (Item item : new HtmlParser().parseAudioShowItems(showItems)) {
         System.out.println(item.info());
         }
         */
        //System.out.println(new HtmlParser().parseActiveAudioShowItem(Extractor.getActiveItem(site)).info());
        
        /*elements = Extractor.getVideoItems(site);
        for (Item item : new HtmlParser().parseVideoShowItems(elements)) {
            System.out.println(item.info());
        }*/
        System.out.println(parser.parseActiveVideoShowItem(Extractor.getActiveItem(site)).info());
    }

    public static void playMP3(String source) throws MalformedURLException {
        File f = new File(source);
        if (f.exists()) {
            System.out.println("exists");
        }
        //new MP3Player(f).play();
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

    public static void printElements(Elements elements) {
        for (Element e : elements) {
            //if(link.attr("href", "/mp3-archiv/*"))
            //String s = e.attr("href");
            //if(s.contains("mp3-archiv")){
            System.out.println(e);
            //}

        }
    }

}