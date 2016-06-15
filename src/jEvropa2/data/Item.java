package jEvropa2.data;


import java.net.URL;

/**
 *
 * @author Roman
 */
public class Item extends E2Data {

    private URL webSiteUrl, imgUrl, mp3Url;
    private String time;

    public Item(String name, URL webSiteUrl, URL imgUrl, String time) {
        super(name);
        this.webSiteUrl = webSiteUrl;
        this.imgUrl = imgUrl;
        this.time = time;
        this.mp3Url = EMPTY_URL;
    }

    public String info() {
        return time + " > "+getName()
                + " (" + webSiteUrl.toExternalForm() + ", "
                + imgUrl.toExternalForm() + ")";
    }
}
