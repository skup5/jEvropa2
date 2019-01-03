package cz.skup5.jEvropa2.mapper

import cz.skup5.jEvropa2.dao.POST_TYPE_SLUG
import cz.skup5.jEvropa2.data.E2Data.Companion.EMPTY_URI
import cz.skup5.jEvropa2.data.E2Data.Companion.ID_NONE
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.Show
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI

/**
 * Converts JSON objects to kotlin [Show] or [Item] objects.
 *
 * @author Skup5
 * @since 2.0
 */

/**
 * Converts [item] in JSONObject format to [Item] object.
 */
fun toItem(item: JSONObject): Item {
    return Item(
            item.optInt("id", ID_NONE),
            item.optString("title"),
            item.optString("date_gmt"),
            webSiteUri = URI(generateItemWebSiteUri(item.optString("slug"))),
            imgUri = URI(item.optString("featured_image_src_thumbnail")),
            mediaUri = URI(item.optJSONObject("meta_box")?.optString("mb_clanek_multimedialni_soubor"))
    )
}

/**
 * Converts [items] in JSONArray format to [List] of [Item]s.
 */
fun toListItem(items: JSONArray): List<Item> {
    val listItem = ArrayList<Item>(items.length())
    for (item in items) {
        listItem += toItem(item as JSONObject)
    }
    return listItem
}

/**
 * Converts [show] in JSONObject format to [Show] object.
 */
fun toShow(show: JSONObject): Show {
    return Show(
            show.optInt("id", ID_NONE),
            show.optString("name"),
            EMPTY_URI,
            show.optString("slug")
    )
}

/**
 * Converts [shows] in JSONArray format to [List] of [Show]s.
 */
fun toListShow(shows: JSONArray): List<Show> {
    val listShow = ArrayList<Show>(shows.length())
    for (show in shows) {
        listShow += toShow(show as JSONObject)
    }
    return listShow
}

/**
 * Creates [Item.webSiteUri] link by the given [slug].
 * @param slug defines the specific [Item]
 */
fun generateItemWebSiteUri(slug: String) = "https://www.evropa2.cz/$POST_TYPE_SLUG?video=$slug"