package cz.skup5.jEvropa2.data

import java.net.URI

/**
 *
 * @author Skup5
 */
class Item(
        name: String,
        var webSiteUrl: URI = EMPTY_URI,
        var imgUrl: URI = EMPTY_URI,
        var mediaUrl: URI = EMPTY_URI,
        val time: String
) : E2Data(name = name) {

    fun hasImgUrl(): Boolean {
        return imgUrl !== EMPTY_URI
    }

    fun hasMediaUrl(): Boolean {
        return mediaUrl !== EMPTY_URI
    }

    fun hasWebSiteUrl(): Boolean {
        return webSiteUrl !== EMPTY_URI
    }

    fun info(): String {
        val sb = StringBuilder("Item{")
        with(sb) {
            append("name=").append(name)
            append(", time='").append(time).append('\'')
            append(", webSiteUrl=").append(webSiteUrl)
            append(", imgUrl=").append(imgUrl)
            append(", mediaUrl=").append(mediaUrl)
            append('}')
        }
        return sb.toString()
    }

}
