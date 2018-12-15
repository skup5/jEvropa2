package cz.skup5.jEvropa2

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Static class with functions for searching in Evropa2 HTML Document.
 *
 * @author Skup5
 */
object Extractor {

    /**
     * Returns title of the show list. The title is extracted from the [doc] Document.
     * @since 2.0
     */
    @JvmStatic
    fun getShowsLabel(doc: Document): String {
        return doc.select("footer a[href^='/porady'].title-link").text()
    }

    /**
     * Returns [Elements] that contains Evropa2 shows. The list is extracted from the [doc] Document.
     */
    @JvmStatic
    fun getShowsList(doc: Document): Elements {
        return doc.select("footer a[href^='/porady'].link")
    }

    fun getAudioItems(doc: Document): Elements {
        //return doc.select("#main .js-equalize .feed-player").select(".item ~ .audio").not(".item-active, .more");
        return doc.select("#main .js-equalize .feed-player .paginableContainer .item").select(".audio")
    }

    fun getVideoItems(doc: Document): Elements {
        // return doc.select("#main .js-equalize .feed-player").select(".item ~ .video").not(".item-active, .more");
        return doc.select("#main .js-equalize .feed-player .paginableContainer .item").select(".video")
    }

    fun getActiveItem(doc: Document): Element {
        return doc.select("#main .js-equalize-children .player").first()
    }

    fun getNextShowItems(doc: Document): Element {
        return doc.select("#main .js-equalize .feed-player").select(".item.more").first()
    }

    /**
     * Returns script element with javascript player source.
     *
     * @param doc
     * @return element or null
     */
    fun getPlayerScript(doc: Document): Element? {
        val scripts = doc.select("#main [id^='jPlayer'] script")
        for (scriptElement in scripts) {
            if (scriptElement.html().contains("jPlayer")) {
                return scriptElement
            }
        }
        return null
    }
}
