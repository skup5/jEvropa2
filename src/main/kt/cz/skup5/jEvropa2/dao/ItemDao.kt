package cz.skup5.jEvropa2.dao

import cz.skup5.jEvropa2.HttpRequest
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.MultiMediaType
import cz.skup5.jEvropa2.data.Show
import cz.skup5.jEvropa2.mapper.toListItem
import org.json.JSONObject

const val POST_TYPE_SLUG = "porady-zaznamy"
private const val TAXONOMY = "rubrika-porady"
private const val QUERY_URL = "https://www.evropa2.cz/graphql"

/**
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

        return processResponse(response, useCategory)
    }

    private fun processResponse(response: String, useCategory: Boolean): List<Item> {
        val responseJson = JSONObject(response)
        var postsJson = responseJson.getJSONObject("data")
        if (useCategory) {
            postsJson = postsJson.getJSONArray("categories").getJSONObject(0)
        }
        postsJson = postsJson.getJSONObject("posts")

        val items = toListItem(postsJson.getJSONArray("items"))
        for (item in items) {
            item.mediaType = MultiMediaType.AUDIO
        }

        return items
    }

    private fun prepareQuery(useCategory: Boolean, show: Show?, page: Int, itemsPerPage: Int): String {
        val dataJson = JSONObject()
        val variables = JSONObject()
        val query: String
        val operationName: String

        if (useCategory) {
            query = videoCategoryMoreQuery()
            operationName = "VideoCategoryMoreQuery"
            variables
                    .put("categorySlug", show!!.slug)
                    .put("taxonomy", TAXONOMY)
        } else {
            query = videoMoreQuery()
            operationName = "VideoMoreQuery"
        }

        variables
                .put("postTypeSlug", POST_TYPE_SLUG)
                .put("page", page)
                .put("per_page", itemsPerPage)

        dataJson
                .put("operationName", operationName)
                .put("query", query)
                .put("variables", variables)

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