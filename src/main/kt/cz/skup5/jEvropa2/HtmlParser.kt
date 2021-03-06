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
    @Deprecated("It works, but will be removed.",
            ReplaceWith("ShowDao.get()", "cz.skup5.jEvropa2.dao.ShowDao"))
    fun parseShows(elements: Elements): Set<Show> {
        val showsSet = LinkedHashSet<Show>()

        for (element in elements) {
            showsSet += showParser.parse(element)
        }
        return showsSet
    }

    /**
     * Returns parsed set of audio [Item]s from the given [elements].
     */
    @Deprecated("It works, but will be removed.",
            ReplaceWith("ItemDao.get(show, page, perPage)", "cz.skup5.jEvropa2.dao.ItemDao"))
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
     * Returns parsed active audio [Item] from the given [element].
     */
    @Deprecated("It works, but will be removed.", ReplaceWith("ItemDao.get(show, page, perPage)", "cz.skup5.jEvropa2.dao.ItemDao"))
    fun parseActiveAudioShowItem(element: Element): Item = itemParser.parseActiveAudio(element)

    @Deprecated("It doesn't work. Will be repaired.", level = DeprecationLevel.ERROR)
    fun parseActiveVideoShowItem(element: Element): Item? {
        var item: Item? = null
        try {
            item = itemParser.parseActiveVideo(element)
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        }

        return item
    }

    fun parseMp3Url(dataJSONElement: Element): URI {
        return itemParser.parseMediaUrl(dataJSONElement)
    }

    @Deprecated("It doesn't work. Will be repaired.", level = DeprecationLevel.ERROR)
    fun parseMp4Url(script: Element): URI {
        return itemParser.parseMp4Url(script.html())
    }

    @Deprecated("It doesn't work. Will be repaired.", level = DeprecationLevel.ERROR)
    fun parseNextPageUrl(element: Element): URI {
        return showParser.parseNextPage(element)
    }
}
