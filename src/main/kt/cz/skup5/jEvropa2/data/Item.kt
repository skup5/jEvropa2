package cz.skup5.jEvropa2.data

import java.net.URI

/**
 * Represents an multimedia (audio or video) record.
 *
 * @author Skup5
 */
data class Item(
        /** The item unique id. */
        override val id: Int = ID_NONE,
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
        var mediaType: MultiMediaType = MultiMediaType.UNKNOWN,
        /** The show id which this item belongs to. */
        var showId: Int = ID_NONE
) : E2Data(id, name) {

    fun hasImgUri(): Boolean = imgUri !== EMPTY_URI

    fun hasMediaUri(): Boolean = mediaUri !== EMPTY_URI

    fun hasWebSiteUri(): Boolean = webSiteUri !== EMPTY_URI

    /**
     * Returns text representation of current item.
     */
    fun info(): String {
        val sb = StringBuilder(name)
        with(sb) {
            append(" (")
            append(mediaType).append(" ")
            append(timestamp)
            append(", web=").append(webSiteUri)
            append(", img=").append(imgUri)
            append(", media=").append(mediaUri)
            append(')')
        }
        return sb.toString()
    }

}
