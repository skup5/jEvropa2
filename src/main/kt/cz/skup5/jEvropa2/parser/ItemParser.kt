package cz.skup5.jEvropa2.parser

import cz.skup5.jEvropa2.Extractor
import cz.skup5.jEvropa2.data.E2Data
import cz.skup5.jEvropa2.data.Item
import cz.skup5.jEvropa2.data.MultiMediaType
import org.jsoup.nodes.Element
import java.io.PrintWriter
import java.io.StringWriter
import java.net.URI

/**
 * This class converts extracted [Element]s to [Item]s.
 * @see Extractor
 * @author Skup5
 */
class ItemParser {

    /**
     * Returns parsed audio [Item] from the specific [element].
     */
    fun parseAudio(element: Element): Item {
        val imgUri = URI(element.selectFirst("img").attr("abs:src"))
        val webSiteUri = URI(element.selectFirst("a").attr("abs:href"))
        val name = element.selectFirst("h3").text()
        var date = ""

        if (webSiteUri.query != null)
            date = Regex("(before=)(\\d+-\\d\\d?-\\d\\d?)").find(webSiteUri.query)?.groupValues?.get(2) ?: ""

        return Item(name, date, webSiteUri = webSiteUri, imgUri = imgUri, mediaType = MultiMediaType.AUDIO)
    }

    fun parseActiveAudio(element: Element): Item? {
        //        System.out.println(element);
        val imgUrl: URI
        var mp3Url = E2Data.EMPTY_URI
        val elements = element.select(".feed-player .item-active").select(".audio")
        val player: Element
        if (elements.isEmpty()) {
            return null
        }
        player = elements.first()
        val time = player.select(".time").first().text()
        val title = player.select(".content h2").first().text()
        val cover = element.select(".jPlayer .jMotiveCover").first().outerHtml()
        imgUrl = parseActiveImgUrl(cover)
        val scriptElement = Extractor.getPlayerScript(element.ownerDocument())
        if (scriptElement != null) {
            val script = scriptElement.html()
            if (!script.isEmpty())
                mp3Url = parseMp3Url(script)
        }
        return Item(title, imgUri = imgUrl, mediaUri = mp3Url, timestamp = time)
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
        return Item(title, timestamp = time, imgUri = imgUrl, mediaUri = mp4Url)
    }

    fun parseMp3Url(script: String): URI {
        val end = script.indexOf(".mp3") + 4
        val start = script.lastIndexOf("http", end)
        var url = script.substring(start, end)
        //url = url.replace("\\", "");
        url = unescapeJava(url)
        return URI(url)
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
