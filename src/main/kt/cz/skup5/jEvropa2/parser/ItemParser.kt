package cz.skup5.jEvropa2.parser

import cz.skup5.jEvropa2.Extractor
import cz.skup5.jEvropa2.data.E2Data.Companion.EMPTY_URI
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.MultiMediaType
import cz.skup5.jEvropa2.extension.JsonUtil.toJsonObject
import cz.skup5.jEvropa2.extension.getJsonObject
import cz.skup5.jEvropa2.extension.getString
import org.jsoup.nodes.Element
import java.io.PrintWriter
import java.io.StringWriter
import java.net.URI

const val DATE_PATTERN = "(before=)(\\d+-\\d\\d?-\\d\\d?)"

/**
 * This class converts extracted [Element]s to [Item]s.
 * @see Extractor
 * @author Skup5
 */
class ItemParser {

    /**
     * Returns parsed audio [Item] from the specific [element].
     * This item doesn't contain [Item#mediaUri].
     */
    fun parseAudio(element: Element): Item {
        val imgUri = URI(element.selectFirst("img").attr("abs:src"))
        val webSiteUri = URI(element.selectFirst("a").attr("abs:href"))
        val name = element.selectFirst("h3").text()
        var date = ""

        if (webSiteUri.query != null)
            date = Regex(DATE_PATTERN).find(webSiteUri.query)?.groupValues?.get(2) ?: ""

        return Item(name = name, timestamp = date, webSiteUri = webSiteUri, imgUri = imgUri, mediaType = MultiMediaType.AUDIO)
    }

    /**
     * Returns parsed audio [Item] from the specific [element] (including [Item.mediaUri]).
     */
    fun parseActiveAudio(element: Element): Item {
        val audioItem = parseAudio(element.selectFirst(Extractor.ROOT_ELEMENT_ITEM))
        val mp3Uri = parseMediaUrl(element.selectFirst(Extractor.ROOT_ELEMENT_DATA_JSON))
        audioItem.mediaUri = mp3Uri
        return audioItem
    }

    fun parseActiveImgUrl(div: String): URI {
        /*<div class="jMotiveCover"
         style="background-image: url('https://m.static.lagardere.cz/evropa2/image/2016/01/Leos_Patrik-4-660x336.jpg');"></div>*/
        var end = div.indexOf(".jpg")
        if (end < 0) end = div.indexOf(".png")
        end += 4
        val start = div.lastIndexOf("http", end)
        val img = div.substring(start, end)
        return URI(img)
    }

    fun parseActiveVideo(element: Element): Item? {
        val imgUrl: URI
        val mp4Url: URI
        val elements = element.select(".feed-player .item-active").select(".video")
        val player: Element
        if (elements.isEmpty()) {
            return null
        }
        player = elements.first()
        val time = player.select(".time").first().text()
        val title = player.select(".content h2").first().text()
        val script = element.select(".jPlayer script").first().html()
        imgUrl = parseActiveImgUrl(script)
        mp4Url = parseMp4Url(script)
        return Item(name = title, timestamp = time, imgUri = imgUrl, mediaUri = mp4Url)
    }

    /**
     * Returns multimedia url of the active/current item or
     * [EMPTY_URI] if some error was occurred while parsing.
     */
    fun parseMediaUrl(dataJSONElement: Element): URI {
        val dataJSON = dataJSONElement.data().substringAfter('=', "")
        if (dataJSON.isBlank()) return EMPTY_URI

        val json = toJsonObject(dataJSON)

        return URI(
                json.getJsonObject("props")
                        ?.getJsonObject("pageProps")
                        ?.getJsonObject("currentPost")
                        ?.getJsonObject("meta_box")
                        ?.getString("mb_clanek_multimedialni_soubor") ?: EMPTY_URI.toString()
        )
    }

    fun parseMp4Url(script: String): URI {
        val end = script.indexOf(".mp4") + 4
        val start = script.lastIndexOf("http", end)
        var url = script.substring(start, end)
        //url = url.replace("\\", "");
        url = unescapeJava(url)
        return URI(url)
    }

    fun parseVideo(element: Element): Item {
        return parseAudio(element)
    }

    private fun unescapeJava(str: String): String {
        val out = StringPrintWriter(str.length)
        val sz = str.length
        val unicode = StringBuffer(4)
        var hadSlash = false
        var inUnicode = false

        for (i in 0 until sz) {
            val ch = str[i]
            if (inUnicode) {
                unicode.append(ch)
                if (unicode.length == 4) {
                    val nfe = Integer.parseInt(unicode.toString(), 16)
                    out.write(nfe.toChar().toInt())
                    unicode.setLength(0)
                    inUnicode = false
                    hadSlash = false
                }
            } else if (hadSlash) {
                hadSlash = false
                when (ch) {
                    '\"' -> out.write(34)
                    '\'' -> out.write(39)
                    '\\' -> out.write(92)
                    'b' -> out.write(8)
                    'f' -> out.write(12)
                    'n' -> out.write(10)
                    'r' -> out.write(13)
                    't' -> out.write(9)
                    'u' -> inUnicode = true
                    else -> out.write(ch.toInt())
                }
            } else if (ch.toInt() == 92) {
                hadSlash = true
            } else {
                out.write(ch.toInt())
            }
        }

        if (hadSlash) {
            out.write(92)
        }
        return out.string
    }

    internal inner class StringPrintWriter : PrintWriter {

        val string: String
            get() {
                this.flush()
                return (super.out as StringWriter).toString()
            }

        constructor() : super(StringWriter()) {}

        constructor(initialSize: Int) : super(StringWriter(initialSize)) {}
    }
}
