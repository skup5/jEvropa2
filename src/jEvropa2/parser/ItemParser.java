package jEvropa2.parser;

import jEvropa2.data.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roman
 */
public class ItemParser {

    /**
     * <div class="item post-picture-effect audio clearfix">
     * <div class="source">
     * <span class="icon icon-play-triangle">
     * <!-- --></span>
     * <span class="time">před 11 hod.</span>
     * </div>
     * <div class="content">
     * <h2>
     * <a href="https://www.evropa2.cz/shows/leosovi-deti-maji-skvele-detske-napady-1094868">Leošovi
     * děti mají skvělé „dětské“ nápady :-)</a> </h2>
     * <a href="https://www.evropa2.cz/shows/leosovi-deti-maji-skvele-detske-napady-1094868" class="picture">
     * <img src="https://m.static.lagardere.cz/evropa2/image/2015/12/E2__DSC7864-kopie-490x327.jpg" alt="">
     * <p class="infoBox cols"> <span class="col col-xs-8 text-left">Ranní
     * show</span> </p> </a>
     * </div>
     * </div>
     *
     * @param element
     * @return
     */
    public Item parseAudio(Element element) throws MalformedURLException {
        // System.out.println(element.outerHtml()+"=============================================================");
        String time = element.select(".time").first().text();
        Element content = element.select(".content").first();
        Element header = content.select("h2 a").first();
        URL url = new URL(header.attr("href"));
        String name = header.text();
        URL imgUrl = new URL(content.select("a.picture img").first().attr("src"));
        return new Item(name, time, url, imgUrl);
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
    public Item parseActiveAudio(Element element) throws MalformedURLException {
        URL imgUrl, mp3Url;
        Elements elements = element.select(".feed-player").select(".item-active").select(".audio");
        Element player;
        if (elements.isEmpty()) {
            return null;
        }
        player = elements.first();
        String time = player.select(".time").first().text();
        String title = player.select(".content h2").first().text();
        String cover = element.select(".jPlayer .jMotiveCover").first().outerHtml();
        imgUrl = parseImgUrl(cover);
        String script = element.select(".jPlayer script").first().html();
        mp3Url = parseMp3Url(script);
        return new Item(title, imgUrl, mp3Url, time);
    }

    public URL parseImgUrl(String div) throws MalformedURLException {
        /*<div class="jMotiveCover" 
         style="background-image: url('https://m.static.lagardere.cz/evropa2/image/2016/01/Leos_Patrik-4-660x336.jpg');"></div>*/
        int end = div.indexOf(".jpg") + 4;
        int start = div.lastIndexOf("http", end);
        String img = div.substring(start, end);
        return new URL(img);
    }

    public URL parseMp3Url(String script) throws MalformedURLException {
        int end = script.indexOf(".mp3") + 4;
        int start = script.lastIndexOf("http", end);
        String url = script.substring(start, end);
        //url = url.replace("\\", "");
        url = unescapeJava(url);
        return new URL(url);
    }

    public Item parseVideo(Element element) {
        throw new UnsupportedOperationException();
    }

    private String unescapeJava(String str) {
        StringPrintWriter out = new StringPrintWriter(str.length());
        int sz = str.length();
        StringBuffer unicode = new StringBuffer(4);
        boolean hadSlash = false;
        boolean inUnicode = false;

        for (int i = 0; i < sz; ++i) {
            char ch = str.charAt(i);
            if (inUnicode) {
                unicode.append(ch);
                if (unicode.length() == 4) {
                    int nfe = Integer.parseInt(unicode.toString(), 16);
                    out.write((char) nfe);
                    unicode.setLength(0);
                    inUnicode = false;
                    hadSlash = false;
                }
            } else if (hadSlash) {
                hadSlash = false;
                switch (ch) {
                    case '\"':
                        out.write(34);
                        break;
                    case '\'':
                        out.write(39);
                        break;
                    case '\\':
                        out.write(92);
                        break;
                    case 'b':
                        out.write(8);
                        break;
                    case 'f':
                        out.write(12);
                        break;
                    case 'n':
                        out.write(10);
                        break;
                    case 'r':
                        out.write(13);
                        break;
                    case 't':
                        out.write(9);
                        break;
                    case 'u':
                        inUnicode = true;
                        break;
                    default:
                        out.write(ch);
                }
            } else if (ch == 92) {
                hadSlash = true;
            } else {
                out.write(ch);
            }
        }

        if (hadSlash) {
            out.write(92);
        }
        return out.getString();
    }

    class StringPrintWriter extends PrintWriter {

        public StringPrintWriter() {
            super(new StringWriter());
        }

        public StringPrintWriter(int initialSize) {
            super(new StringWriter(initialSize));
        }

        public String getString() {
            this.flush();
            return ((StringWriter) super.out).toString();
        }
    }
}
