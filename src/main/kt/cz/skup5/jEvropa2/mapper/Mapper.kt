package cz.skup5.jEvropa2.mapper

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import cz.skup5.jEvropa2.dao.POST_TYPE_SLUG
import cz.skup5.jEvropa2.data.E2Data.Companion.EMPTY_URI
import cz.skup5.jEvropa2.data.E2Data.Companion.ID_NONE
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.extension.getInt
import cz.skup5.jEvropa2.extension.getJsonObject
import cz.skup5.jEvropa2.extension.getString
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.plusAssign

/**
 * Converts JSON objects to kotlin [Show] or [Item] objects.
 *
 * @author Skup5
 * @since 2.0
 */

private val dateParser = SimpleDateFormat("y-M-d k:m:s", Locale("cs", "cz"))
private val datetimeRegex = Regex("(\\d+-\\d\\d?-\\d\\d?)T(\\d+:\\d\\d?:\\d\\d?)")

/**
 * Converts datetime string to timestamp in milliseconds.
 *
 * datetime example: 2018-12-21T11:10:22Z
 */
fun toTimestamp(datetime: String): Long {
    val result = datetimeRegex.find(datetime)
    val extractedDatetime = "${result?.groupValues?.get(1)} ${result?.groupValues?.get(2)}"
    return dateParser.parse(extractedDatetime).time
}

/**
 * Converts [item] in JsonObject format to [Item] object.
 */
fun toItem(item: JsonObject): Item {
    return Item(
            item.getInt("id", ID_NONE),
            item.getString("title"),
            toTimestamp(item.getString("date_gmt")),
            webSiteUri = URI(generateItemWebSiteUri(item.getString("slug"))),
            imgUri = URI(item.getString("featured_image_src_thumbnail")),
            mediaUri = URI(item.getJsonObject("meta_box")?.getString("mb_clanek_multimedialni_soubor"))
    )
}

/**
 * Converts [items] in JsonArray format to [List] of [Item]s.
 */
fun toListItem(items: JsonArray): List<Item> {
    val listItem = ArrayList<Item>(items.size())
    for (item in items) {
        listItem += toItem(item as JsonObject)
    }
    return listItem
}

/**
 * Converts [show] in JsonObject format to [Show] object.
 */
fun toShow(show: JsonObject): Show {
    return Show(
            show.getInt("id", ID_NONE),
            show.getString("name"),
            EMPTY_URI,
            show.getString("slug")
    )
}

/**
 * Converts [shows] in JsonArray format to [List] of [Show]s.
 */
fun toListShow(shows: JsonArray): List<Show> {
    val listShow = ArrayList<Show>(shows.size())
    for (show in shows) {
        listShow += toShow(show as JsonObject)
    }
    return listShow
}

/**
 * Creates [Item.webSiteUri] link by the given [slug].
 * @param slug defines the specific [Item]
 */
fun generateItemWebSiteUri(slug: String) = "https://www.evropa2.cz/$POST_TYPE_SLUG?video=$slug"