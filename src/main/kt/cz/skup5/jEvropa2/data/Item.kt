package cz.skup5.jEvropa2.data

import java.net.URI

/**
 * Represents an multimedia (audio or video) record.
 *
 * @author Skup5
 */
data class Item(
        /** The item title/label. */
        override val name: String,
        /** The date/time value parsed from web site. */
        var timestamp: String,
        /** The absolute url of item web site. */
        var webSiteUri: URI = EMPTY_URI,
        /** The img absolute url of item. */
        var imgUri: URI = EMPTY_URI,
        /** The multimedia absolute url of item. */
        var mediaUri: URI = EMPTY_URI,
        /** The multimedia type of item. It is relative to [mediaUri].*/
        var mediaType: MultiMediaType = MultiMediaType.UNKNOWN
) : E2Data(name = name) {

    fun hasImgUri(): Boolean {
        return imgUri !== EMPTY_URI
    }

    fun hasMediaUri(): Boolean {
        return mediaUri !== EMPTY_URI
    }

    fun hasWebSiteUri(): Boolean {
        return webSiteUri !== EMPTY_URI
    }

    /**
     * Returns text representation of current item. It is similar to a [toString] call.
     */
    fun info(): String {
        val sb = StringBuilder("Item{")
        with(sb) {
            append("name=").append(name)
            append(", timestamp='").append(timestamp).append('\'')
            append(", mediaType=").append(mediaType)
            append(", webSiteUri=").append(webSiteUri)
            append(", imgUri=").append(imgUri)
            append(", mediaUri=").append(mediaUri)
            append('}')
        }
        return sb.toString()
    }

}
