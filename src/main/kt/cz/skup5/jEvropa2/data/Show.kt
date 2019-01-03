package cz.skup5.jEvropa2.data

import java.net.URI

/**
 * Represents a category of [Item]s.
 *
 * @author Skup5
 */
data class Show(
        /** The show unique id. */
        override val id: Int = ID_NONE,
        /** The show title/label. */
        override val name: String,
        /** The absolute url of show web site. */
        var webSiteUri: URI,
        var slug: String
) : E2Data(id, name) {

    fun hasWebSiteUri(): Boolean = webSiteUri !== EMPTY_URI

    fun info(): String {
        return "$name ($id${if (hasWebSiteUri()) ", $webSiteUri" else ""}${if (slug.isNotEmpty()) ", $slug" else ""})"
    }
}
