package cz.skup5.jEvropa2.dao

import cz.skup5.jEvropa2.Extractor
import cz.skup5.jEvropa2.HttpRequest
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.mapper.toListShow
import org.json.JSONObject
import org.jsoup.nodes.Document

private const val DOCUMENT_URL = "https://www.evropa2.cz"

/**
 * This DAO provides interface to Evropa2 [Show] api.
 *
 * @author Skup5
 * @since 2.0
 */
object ShowDao {

    /**
     * Returns actual show list from Evropa2 website.
     */
    fun get(): List<Show> = get(HttpRequest.httpGetSite(DOCUMENT_URL))

    /**
     * Returns show list from the current [document].
     */
    fun get(document: Document): List<Show> {
        val response = Extractor.getDataJSON(document)
        val dataJSON = response.data().substringAfter('=', "")
        return processResponse(dataJSON)
    }

    private fun processResponse(response: String): List<Show> {
        val responseJson = JSONObject(response)
        val showsJsonArray = responseJson
                .getJSONObject("props")
                .getJSONObject("pageProps")
                .getJSONObject("footerMenu")
                .getJSONObject("taxonomies")
                .getJSONArray("porady")

        return toListShow(showsJsonArray)
    }
}