package cz.skup5.jEvropa2.parser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cz.skup5.jEvropa2.data.Show;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Element;

/**
 *
 * @author Roman
 */
public class ShowParser {

    /**
     * <li class="">
     * <a href="/shows/ranni-show/" title="Ranní show">
     * Ranní show
     * <span class="hidden js-show-parts-count" data-show="ranni-show">
     * nové:&nbsp;&nbsp;11919
     * </span>
     * </a>
     * </li>
     *
     * @param element
     * @return
     */
    public Show parse(Element element) throws MalformedURLException {
        element = element.select("a").first();
        String url = element.absUrl("href");
        String title = element.attr("title");
        return new Show(title, new URL(url));
    }

    /*
     <div class="item more clearfix loadNextPageAjax infiniteMedia">
     <div class="content text-center clearfix">
     <p class="pagenumber"><span class="label remainingPagesCount">477</span> Stran</p>
     <a href="https://www.evropa2.cz/shows/ranni-show/?pageIndex=2" title="" class="btn btnB">Další</a>
     </div>
     </div>
     */
    public URL parseNextPage(Element element) throws MalformedURLException {
        String href = element.select("a").attr("href");
        return new URL(href);
    }
}
