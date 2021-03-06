package cz.skup5.jEvropa2.dao

import com.google.gson.JsonObject
import cz.skup5.jEvropa2.HttpRequest
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.MultiMediaType
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.extension.JsonUtil.toJsonObject
import cz.skup5.jEvropa2.extension.getJsonArray
import cz.skup5.jEvropa2.extension.getJsonObject
import cz.skup5.jEvropa2.mapper.toListItem

const val POST_TYPE_SLUG = "porady-zaznamy"
private const val TAXONOMY = "rubrika-porady"
private const val QUERY_URL = "https://www.evropa2.cz/graphql"

/**
 * This DAO provides interface to Evropa2 [Item] api.
 *
 * @author Skup5
 * @since 2.0
 */
object ItemDao {

    /**
     * Returns some items (count = [itemsPerPage], ordered descending by date)
     * depended on [page] and [show].
     *
     * If [show] is null, returns items from different shows.
     */
    fun get(show: Show? = null, page: Int, itemsPerPage: Int): List<Item> {
        val useCategory = show != null

//        System.out.println("data payload:\n"+data);
        val response = HttpRequest.httpPostJson(QUERY_URL, prepareQuery(useCategory, show, page, itemsPerPage))

        return processResponse(response, useCategory, show)
    }

    private fun processResponse(response: String, useCategory: Boolean, show: Show?): List<Item> {
        val responseJson = toJsonObject(response)
        var postsJson: JsonObject? = responseJson.getJsonObject("data") ?: return emptyList()

        if (useCategory) {
            postsJson = postsJson?.getJsonArray("categories")?.getJsonObject(0)
        }
        postsJson = postsJson?.getJsonObject("posts") ?: return emptyList()

        val items = postsJson.getJsonArray("items")?.let { toListItem(it) } ?: return emptyList()
        for (item in items) {
            item.mediaType = MultiMediaType.AUDIO
        }

        if (show != null) {
            for (item in items) {
                item.showId = show.id
            }
        }

        return items
    }

    private fun prepareQuery(useCategory: Boolean, show: Show?, page: Int, itemsPerPage: Int): String {
        val dataJson = JsonObject()
        val variables = JsonObject()
        val query: String
        val operationName: String

        if (useCategory) {
            query = videoCategoryMoreQuery()
            operationName = "VideoCategoryMoreQuery"
            variables.addProperty("categorySlug", show!!.slug)
            variables.addProperty("taxonomy", TAXONOMY)
        } else {
            query = videoMoreQuery()
            operationName = "VideoMoreQuery"
        }

        variables.addProperty("postTypeSlug", POST_TYPE_SLUG)
        variables.addProperty("page", page)
        variables.addProperty("per_page", itemsPerPage)

        dataJson.addProperty("operationName", operationName)
        dataJson.addProperty("query", query)
        dataJson.add("variables", variables)

        return dataJson.toString()
    }
}

/**
 * This Graphql query returns the mix of newest items. The items depends on params $page and $per_page.
 */
private fun videoMoreQuery() =
        "query VideoMoreQuery(\$postTypeSlug: String, \$page: Int, \$per_page: Int, \$before: String) {posts(postTypeSlug: \$postTypeSlug, page: \$page, per_page: \$per_page, before: \$before) {items {...PostFragment meta_box {mb_clanek_multimedialni_soubor} } meta {totalPages} }} fragment PostFragment on Post {id slug title date_gmt featured_image_src_thumbnail}"

/**
 * This Graphql query returns the newest items of specific show. The items depends on params $page, $per_page a and $categorySlug.
 */
private fun videoCategoryMoreQuery() =
        "query VideoCategoryMoreQuery(\$taxonomy: String, \$postTypeSlug: String, \$page: Int, \$per_page: Int, \$before: String, \$categorySlug: String) {categories: categoriesOrTags(taxonomy: \$taxonomy, slug: \$categorySlug) {...CategoriesFragment posts(postTypeSlug: \$postTypeSlug, page: \$page, per_page: \$per_page, before: \$before) {items {...PostFragment meta_box {mb_clanek_multimedialni_soubor} } meta {totalPages} }}} fragment CategoriesFragment on Category {id  name  link  slug} fragment PostFragment on Post {id slug title date_gmt featured_image_src_thumbnail}"