package cz.skup5.jEvropa2.data

import java.net.URL

/**
 *
 * @author Skup5
 */
class Item(name: String, var webSiteUrl: URL? = E2Data.EMPTY_URL, var imgUrl: URL? = EMPTY_URL, var mediaUrl: URL? = EMPTY_URL, val time: String) : E2Data(name = name) {

    fun hasImgUrl(): Boolean {
        return imgUrl !== E2Data.EMPTY_URL
    }

    fun hasMediaUrl(): Boolean {
        return mediaUrl !== E2Data.EMPTY_URL
    }

    fun hasWebSiteUrl(): Boolean {
        return webSiteUrl !== E2Data.EMPTY_URL
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
