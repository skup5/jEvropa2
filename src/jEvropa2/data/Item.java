package jEvropa2.data;

import java.net.URL;

/**
 *
 * @author Roman
 */
public class Item extends E2Data {

    private URL webSiteUrl, imgUrl, mp3Url;
    private String time;

    public Item(String name, URL imgUrl, String time) {
        this(name, EMPTY_URL, imgUrl, EMPTY_URL, time);
    }

    public Item(String name, URL imgUrl, URL mp3Url, String time){
        this(name, EMPTY_URL, imgUrl, mp3Url, time);
    }
    public Item(String name,  String time, URL webSiteUrl, URL imgUrl) {
        this(name, webSiteUrl, imgUrl, EMPTY_URL, time);
    }

    public Item(String name, URL webSiteUrl, URL imgUrl, URL mp3Url, String time) {
        super(name);
        this.webSiteUrl = webSiteUrl;
        this.imgUrl = imgUrl;
        this.mp3Url = mp3Url;
        this.time = time;
    }

    
    
    public URL getWebSiteUrl() {
        return webSiteUrl;
    }

    public URL getImgUrl() {
        return imgUrl;
    }

    public URL getMp3Url() {
        return mp3Url;
    }

    public String getTime() {
        return time;
    }

    public boolean hasImgUrl() {
        return imgUrl != EMPTY_URL;
    }

    public boolean hasMp3Url() {
        return mp3Url != EMPTY_URL;
    }

    public boolean hasWebSiteUrl() {
        return webSiteUrl != EMPTY_URL;
    }

    public String info() {
        String info = getTime() + " > " + getName() + " (";
        if (hasWebSiteUrl()) {
            info += webSiteUrl.toExternalForm();
        }
        if (hasImgUrl()) {
            info += ", " + imgUrl.toExternalForm();
        }
        if (hasMp3Url()) {
            info += ", " + mp3Url.toExternalForm();
        }
        info += ")";
        return info;
    }

    public void setImgUrl(URL imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public void setMp3Url(URL mp3Url) {
        this.mp3Url = mp3Url;
    }

    public void setWebSiteUrl(URL webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

}
