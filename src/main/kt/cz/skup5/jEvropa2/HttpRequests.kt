package cz.skup5.jEvropa2

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.io.IOException

/**
 * Static class for sending http request to the server.
 *
 * @author Roman Zelenik
 */
object HttpRequests {

    @Throws(IOException::class)
    fun httpGetSite(url: String): Document {
        return Jsoup.connect(url).get()
    }

    @Throws(IOException::class)
    fun httpGetSite(url: String, vararg keysVals: String): Document {
        /* String rate, tag;
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
         }*/
        //return Jsoup.connect(url).data("rate", rate, "cat", cat, "tag", tag).post();
        return Jsoup.connect(url).data(*keysVals).get()
    }
}
