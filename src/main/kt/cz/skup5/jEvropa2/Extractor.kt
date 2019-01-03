package cz.skup5.jEvropa2

import cz.skup5.jEvropa2.data.Item
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Static class with functions for searching in Evropa2 HTML Document.
 *
 * @author Skup5
 */
object Extractor {

    /** The name of HTML element which contains one of show items.  */
    const val ROOT_ELEMENT_ITEM = "article"

    /** The name of HTML element which contains data json like a string. */
    const val ROOT_ELEMENT_DATA_JSON = "script"

    /** The property name of data json in script element. */
    const val DATA_JSON_PREFIX = "__NEXT_DATA__"

    /**
     * Returns title of the show list. The title is extracted from the [doc] Document.
     * @since 2.0
     */
    @JvmStatic
    fun getShowsLabel(doc: Document): String = doc.select("footer a[href^='/porady'].title-link").text()

    /**
     * Returns [Elements] that contains Evropa2 shows. The elements are extracted from the [doc] Document.
     */
    @Deprecated("It works, but will be removed. The shows doesn't contain id.",
            ReplaceWith("ShowDao.get(doc)", "cz.skup5.jEvropa2.dao.ShowDao"))
    @JvmStatic
    fun getShowsList(doc: Document): Elements = doc.select("footer a[href^='/porady'].link")

    /**
     * Returns [Elements] that contains Evropa2 audio [Item]s. The elements are extracted from the [doc] Document.
     */
    @JvmStatic
    fun getAudioItems(doc: Document): Elements = doc.select("$ROOT_ELEMENT_ITEM[role='article'].article-small").not(".active")

    @Deprecated("It doesn't work. Will be repaired.", level = DeprecationLevel.ERROR)
    @JvmStatic
    fun getVideoItems(doc: Document): Elements {
        // return doc.select("#main .js-equalize .feed-player").select(".item ~ .video").not(".item-active, .more");
        return doc.select("#main .js-equalize .feed-player .paginableContainer .item").select(".video")
    }

    /**
     * Returns [Element] that contains current active [Item]. The element is extracted from the [doc] Document.
     */
    @Deprecated("It works, but will be removed.",
            ReplaceWith("ItemDao.get(show, page, perPage)", "cz.skup5.jEvropa2.dao.ItemDao"),
            DeprecationLevel.ERROR)
    @JvmStatic
    fun getActiveItem(doc: Document): Element {
        val activeItemElement = doc.selectFirst("$ROOT_ELEMENT_ITEM[role='article'].article-small.active")
        val dataElement = getDataJSON(doc)
        return Element("div")
                .appendChild(activeItemElement)
                .appendChild(dataElement)
    }

    @Deprecated("The next items aren't loaded and parsed from another document any more.",
            ReplaceWith("ItemDao.get(show, page, perPage)", "cz.skup5.jEvropa2.dao.ItemDao"),
            DeprecationLevel.ERROR)
    @JvmStatic
    fun getNextShowItems(doc: Document): Element {
        return Element("div")
    }

    /**
     * Returns [Element] that contains data json like a String from current [doc] Document.
     * @since 2.0
     */
    @JvmStatic
    fun getDataJSON(doc: Document): Element {
        var dataJSONElement = Element(ROOT_ELEMENT_DATA_JSON)
        for (scriptElement in doc.body().getElementsByTag(ROOT_ELEMENT_DATA_JSON)) {
            if (scriptElement.data().trimStart().startsWith(DATA_JSON_PREFIX)) {
                dataJSONElement = scriptElement
            }
        }
        return dataJSONElement
    }
}
