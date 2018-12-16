package cz.skup5.jEvropa2

import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.parser.ItemParser
import cz.skup5.jEvropa2.parser.ShowParser
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net.MalformedURLException
import java.net.URI
import java.util.*

/**
 * Creates sets of [Show] and [Item] from html code.
 *
 * @author Skup5
 */
class HtmlParser {

    private val showParser: ShowParser = ShowParser()
    private val itemParser: ItemParser = ItemParser()

    /**
     * Returns parsed set of [Show]s from the given [elements].
     */
    fun parseShows(elements: Elements): Set<Show> {
        val showsSet = LinkedHashSet<Show>()
        for (element in elements) {
            try {
                showsSet += showParser.parse(element)
            } catch (ex: MalformedURLException) {
                ex.printStackTrace()
            }
        }
        return showsSet
    }

    /**
     * Returns parsed set of audio [Item]s from the given [elements].
     */
    fun parseAudioShowItems(elements: Elements): Set<Item> {
        val itemsSet = LinkedHashSet<Item>()

        for (element in elements) {
            itemsSet += itemParser.parseAudio(element)
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

    fun parseMp3Url(script: Element): URI {
        return itemParser.parseMp3Url(script.html())
    }

    fun parseMp4Url(script: Element): URI {
        return itemParser.parseMp4Url(script.html())
    }

    fun parseNextPageUrl(element: Element): URI {
        return showParser.parseNextPage(element)
    }
}
