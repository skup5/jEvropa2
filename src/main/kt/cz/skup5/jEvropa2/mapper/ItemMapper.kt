package cz.skup5.jEvropa2.mapper

import cz.skup5.jEvropa2.dao.POST_TYPE_SLUG
import cz.skup5.jEvropa2.data.Item
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI

/**
 * Converts JSON objects to kotlin [Item] objects.
 *
 * @author Skup5
 * @since 2.0
 */

/**
 * Converts [item] in JSONObject format to [Item] object.
 */
fun toItem(item: JSONObject): Item {
    return Item(
            item.optInt("id"),
            item.optString("title"),
            item.optString("date_gmt"),
            webSiteUri = URI(generateWebSiteUri(item.optString("slug"))),
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
 * Creates [Item.webSiteUri] link by the given [slug].
 * @param slug defines the specific [Item]
 */
fun generateWebSiteUri(slug: String) = "https://www.evropa2.cz/$POST_TYPE_SLUG?video=$slug"

