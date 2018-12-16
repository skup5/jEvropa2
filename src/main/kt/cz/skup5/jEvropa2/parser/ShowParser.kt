package cz.skup5.jEvropa2.parser

import cz.skup5.jEvropa2.Extractor
import cz.skup5.jEvropa2.data.Show
import org.jsoup.nodes.Element
import java.net.URI

/**
 * This class converts extracted [Element]s to [Show]s.
 * @see Extractor
 * @author Skup5
 */
class ShowParser {

    /**
     * Returns parsed [Show] from the specific [element].
     */
    fun parse(element: Element): Show {
        val uri = element.absUrl("href")
        val title = element.text()
        return Show(title, URI(uri))
    }

    fun parseNextPage(element: Element): URI {
        val href = element.select("a").attr("href")
        return URI(href)
    }
}
