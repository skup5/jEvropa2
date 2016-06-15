package jEvropa2.parser;


import jEvropa2.data.Item;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Element;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roman
 */
public class ItemParser extends Parser {

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
    public Item parse(Element element) throws MalformedURLException {
       // System.out.println(element.outerHtml()+"=============================================================");
        String time = element.select(".time").first().text();
        Element content = element.select(".content").first();
        Element header = content.select("h2 a").first();
        URL url = new URL(header.attr("href"));
        String name = header.text();
        URL imgUrl = new URL(content.select("a.picture img").first().attr("src"));
        return new Item(name, url, imgUrl, time);
    }
}
