package cz.skup5.jEvropa2.parser

import cz.skup5.jEvropa2.Extractor
import cz.skup5.jEvropa2.data.Item
import org.jsoup.nodes.Element
import java.io.PrintWriter
import java.io.StringWriter
import java.net.MalformedURLException
import java.net.URL

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Roman
 */
class ItemParser {

    /**
     * <div class="item post-picture-effect audio clearfix">
     * <div class="source">
     * <span class="icon icon-play-triangle">
    </span> *
     * <span class="time">před 11 hod.</span>
    </div> *
     * <div class="content">
     * <h2>
     * [Leošovi
 * děti mají skvělé „dětské“ nápady :-)](https://www.evropa2.cz/shows/leosovi-deti-maji-skvele-detske-napady-1094868) </h2>
     * [
 * <img src="https://m.static.lagardere.cz/evropa2/image/2015/12/E2__DSC7864-kopie-490x327.jpg" alt=""></img>
 *
 *  <span class="col col-xs-8 text-left">Ranní
 * show</span>  ](https://www.evropa2.cz/shows/leosovi-deti-maji-skvele-detske-napady-1094868)
    </div> *
    </div> *
     *
     * @param element
     * @return
     */
    @Throws(MalformedURLException::class)
    fun parseAudio(element: Element): Item {
        // System.out.println(element.outerHtml()+"=============================================================");
        val time = element.select(".time").first().text()
        val content = element.select(".content").first()
        val header = content.select("h2 a").first()
        val url = URL(header.attr("href"))
        val name = header.text()
        val imgUrl = URL(content.select("a.picture img").first().attr("src"))
        return Item(name, url, imgUrl, time = time)
    }

    /*
     <div class="source">
     <span class="icon icon-play-triangle">
     <!-- --></span>
     <span class="time">před 10 hod.</span>
     </div>
     <div class="content js-showable">
     <div class="content-in">
     <h2>Na Zemi spadl sedmimetrový meteorit!!!</h2>
     <p class="prologue"></p>
     </div>
     <div class="adrBox">
     <div id="div-gpt-ad-1446672945391-6" style="width: 468px; margin: 0 auto; text-align: center;">
     <script type="text/javascript">
     googletag.display('div-gpt-ad-1446672945391-6');
     </script>
     </div>
     <span class="adrTitle">Reklama</span>
     </div>
     <!-- .adrBox -->
     <div class="commentsBox">
     <div class="fb-comments" data-href="https://www.evropa2.cz/shows/na-zemi-spadl-sedmi-metrovy-meteorit-1094872" data-width="100%" data-version="v2.3"></div>
     </div>
     </div>
     */
    @Throws(MalformedURLException::class)
    fun parseActiveAudio(element: Element): Item? {
        //        System.out.println(element);
        val imgUrl: URL
        var mp3Url: URL? = null
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
        return Item(title, imgUrl, mp3Url, time = time)
    }

    @Throws(MalformedURLException::class)
    fun parseActiveImgUrl(div: String): URL {
        /*<div class="jMotiveCover"
         style="background-image: url('https://m.static.lagardere.cz/evropa2/image/2016/01/Leos_Patrik-4-660x336.jpg');"></div>*/
        var end = div.indexOf(".jpg")
        if (end < 0) end = div.indexOf(".png")
        end += 4
        val start = div.lastIndexOf("http", end)
        val img = div.substring(start, end)
        return URL(img)
    }

    @Throws(MalformedURLException::class)
    fun parseActiveVideo(element: Element): Item? {
        val imgUrl: URL
        val mp4Url: URL
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
        return Item(title, imgUrl, mp4Url, time = time)
    }

    @Throws(MalformedURLException::class)
    fun parseMp3Url(script: String): URL {
        val end = script.indexOf(".mp3") + 4
        val start = script.lastIndexOf("http", end)
        var url = script.substring(start, end)
        //url = url.replace("\\", "");
        url = unescapeJava(url)
        return URL(url)
    }

    @Throws(MalformedURLException::class)
    fun parseMp4Url(script: String): URL {
        val end = script.indexOf(".mp4") + 4
        val start = script.lastIndexOf("http", end)
        var url = script.substring(start, end)
        //url = url.replace("\\", "");
        url = unescapeJava(url)
        return URL(url)
    }

    /*
    <div class="item post-picture-effect video clearfix">
    <div class="source">
        <span class="icon icon-play-triangle"><!-- --></span>
        <span class="time">před 2 dny</span>
    </div>
    <div class="content">
        <h2>
            <a href="https://www.evropa2.cz/shows/wow-evropa-2-unplugged-s-kapelou-mirai-bude-ti-behat-mraz-po-zadech-1105966">WOW! Evropa 2 Unplugged s kapelou MIRAI. Bude ti běhat mráz po zádech</a>
        </h2>
        <a href="https://www.evropa2.cz/shows/wow-evropa-2-unplugged-s-kapelou-mirai-bude-ti-behat-mraz-po-zadech-1105966" class="picture">
            <img src="https://m.static.lagardere.cz/evropa2/image/2016/06/vlcsnap-error716-490x283.png" alt="">
            <p class="infoBox cols">
                <span class="col col-xs-8 text-left">Evropa 2 Music Chart</span>
                            </p>
        </a>
    </div>
    </div>
    */
    @Throws(MalformedURLException::class)
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
