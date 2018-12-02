package cz.skup5.jEvropa2.data


import java.net.URL


/**
 *
 * @author Roman Zelenik
 */
abstract class E2Data(val id: Int = 0, val name: String) {

    companion object {
        val EMPTY_URL: URL? = URL("")
    }
}
