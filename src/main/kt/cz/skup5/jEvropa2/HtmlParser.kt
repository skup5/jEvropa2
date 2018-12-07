package cz.skup5.jEvropa2

import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.parser.ItemParser
import cz.skup5.jEvropa2.parser.ShowParser
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * Creates sets of [Show] and [Item] from html code.
 *
 * @author Roman Zelenik
 */
class HtmlParser {

    private val showParser: ShowParser = ShowParser()
    private val itemParser: ItemParser = ItemParser()

    fun parseShows(elements: Elements): Set<Show> {
        val showsSet = LinkedHashSet<Show>()
        var newShow: Show
        for (element in elements) {
            try {
                newShow = showParser.parse(element)
                showsSet.add(newShow)
            } catch (ex: MalformedURLException) {
                ex.printStackTrace()
            }

        }
        return showsSet
    }

    fun parseAudioShowItems(elements: Elements): Set<Item> {
        val itemsSet = LinkedHashSet<Item>()
        var newItem: Item
        for (element in elements) {
            try {
                newItem = itemParser.parseAudio(element)
                itemsSet.add(newItem)
            } catch (ex: MalformedURLException) {
                ex.printStackTrace()
            }

        }
        return itemsSet
    }

    fun parseVideoShowItems(elements: Elements): Set<Item> {
        val itemsSet = LinkedHashSet<Item>()
        var newItem: Item
        for (element in elements) {
            try {
                newItem = itemParser.parseVideo(element)
                itemsSet.add(newItem)
            } catch (ex: MalformedURLException) {
                ex.printStackTrace()
            }

        }
        return itemsSet
    }

    /**
     *
     * @param element
     * @return Item without web site url
     */
    fun parseActiveAudioShowItem(element: Element): Item? {
        var item: Item? = null
        try {
            item = itemParser.parseActiveAudio(element)
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return item
    }

    fun parseActiveVideoShowItem(element: Element): Item? {
        var item: Item? = null
        try {
            item = itemParser.parseActiveVideo(element)
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return item
    }

    fun parseMp3Url(script: Element): URL? {
        var url: URL? = null
        try {
            url = itemParser.parseMp3Url(script.html())
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return url
    }

    fun parseMp4Url(script: Element): URL? {
        var url: URL? = null
        try {
            url = itemParser.parseMp4Url(script.html())
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return url
    }

    fun parseNextPageUrl(element: Element): URL? {
        var url: URL? = null
        try {
            url = showParser.parseNextPage(element)
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return url
    }
}
