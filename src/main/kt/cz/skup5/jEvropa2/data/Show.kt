package cz.skup5.jEvropa2.data

import java.net.URI

/**
 * Represents a category of [Item]s.
 *
 * @author Skup5
 */
data class Show(
        /** The show title/label. */
        override val name: String,
        /** The absolute url of show web site. */
        var webSiteUri: URI
) : E2Data(name = name) {

    fun info(): String {
        return "$name ($webSiteUri)"
    }
}
