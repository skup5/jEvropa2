package cz.skup5.jEvropa2

import cz.skup5.jEvropa2.dao.ShowDao.get
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * @author Skup5
 */
object Main {
    private const val OFFLINE_HTML_SITE_SHOWS = "evropa2-shows.html"
    private const val OFFLINE_HTML_SITE_ITEM = "evropa2-zpatky-do-minulosti.html"
    private const val BASE_URI = "https://evropa2.cz"
    /**
     * @param args the command line arguments
     * @throws java.io.IOException if some error was occurred while file reading or parsing
     */
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val parser = HtmlParser()
        var site: Document
        //        site = loadSite(OFFLINE_HTML_SITE_SHOWS, "utf-8");
//        site.setBaseUri(BASE_URI);
//        printShows(Extractor.getShowsLabel(site), site);
//        Elements showItems = Extractor.getAudioItems(site);
//        printItems(parser, showItems);
//        System.out.println(parser.parseActiveAudioShowItem(Extractor.getActiveItem(site)).info());
//        Show ranniShow = new Show(17, "Ranní show", E2Data.getEMPTY_URI(), "ranni-show");
//        for (Item item : ItemDao.INSTANCE.get(ranniShow, 1, 6)) {
//            System.out.println(item.info());
//        }
//        System.out.println(parser.parseNextPageUrl(nextShowItems));
    }

    @Throws(FileNotFoundException::class, IOException::class)
    private fun loadSite(fileName: String, charsetName: String): Document {
        val document: Document
        val htmlFile = File(fileName)
        return if (htmlFile.exists()) {
            document = Jsoup.parse(htmlFile, charsetName)
            document
        } else {
            throw FileNotFoundException(fileName)
        }
    }

    private fun printItems(parser: HtmlParser, showItems: Elements) {
        println("Items:")
        //        printElements(showItems);
        for (item in parser.parseAudioShowItems(showItems)) {
            println(item.info())
        }
    }

    private fun printShows(title: String, document: Document) {
        println("$title:")
        //        printElements(shows);
        for (show in get()) {
            println(show.info())
        }
    }

    private fun printElements(elements: Elements) {
        for (e in elements) { //if(link.attr("href", "/mp3-archiv/*"))
//String s = e.attr("href");
//if(s.contains("mp3-archiv")){
            println(e)
            //}
        }
    }
}