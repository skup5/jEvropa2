package cz.skup5.jEvropa2.data


import java.net.URL

/**
 *
 * @author Skup5
 */
class Show(name: String, val webSiteUrl: URL) : E2Data(name = name) {

    fun info(): String {
        return name + " (" + webSiteUrl.toExternalForm() + ")"
    }
}
