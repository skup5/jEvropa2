package cz.skup5.jEvropa2.parser

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cz.skup5.jEvropa2.data.Show
import org.jsoup.nodes.Element
import java.net.MalformedURLException
import java.net.URL

/**
 *
 * @author Roman
 */
class ShowParser {

    /**
     *  *
     * [
 * Ranní show
 * <span class="hidden js-show-parts-count" data-show="ranni-show">
 * nové:&nbsp;&nbsp;11919
</span> *
](/shows/ranni-show/) *
     *
     *
     * @param element
     * @return
     */
    @Throws(MalformedURLException::class)
    fun parse(element: Element): Show {
        var element = element
        element = element.select("a").first()
        val url = element.absUrl("href")
        val title = element.attr("title")
        return Show(title, URL(url))
    }

    /*
     <div class="item more clearfix loadNextPageAjax infiniteMedia">
     <div class="content text-center clearfix">
     <p class="pagenumber"><span class="label remainingPagesCount">477</span> Stran</p>
     <a href="https://www.evropa2.cz/shows/ranni-show/?pageIndex=2" title="" class="btn btnB">Další</a>
     </div>
     </div>
     */
    @Throws(MalformedURLException::class)
    fun parseNextPage(element: Element): URL {
        val href = element.select("a").attr("href")
        return URL(href)
    }
}
