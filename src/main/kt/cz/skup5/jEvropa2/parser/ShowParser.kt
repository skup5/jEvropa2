package cz.skup5.jEvropa2.parser

import cz.skup5.jEvropa2.data.Show
import org.jsoup.nodes.Element
import java.net.MalformedURLException
import java.net.URI
import java.net.URL

/**
 *
 * @author Skup5
 */
class ShowParser {

    /**
     *
     * @param element
     * @return
     */
    @Throws(MalformedURLException::class)
    fun parse(element: Element): Show {
        val url = element.absUrl("href")
        val title = element.text()
        return Show(title, URL(url))
    }

    fun parseNextPage(element: Element): URI {
        val href = element.select("a").attr("href")
        return URI(href)
    }
}
