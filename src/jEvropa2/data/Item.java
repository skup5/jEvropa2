package jEvropa2.data;

import java.net.URL;

/**
 *
 * @author Roman
 */
public class Item extends E2Data {

    private URL webSiteUrl, imgUrl, mediaUrl;
    private String time;

    public Item(String name, URL imgUrl, String time) {
        this(name, EMPTY_URL, imgUrl, EMPTY_URL, time);
    }

    public Item(String name, URL imgUrl, URL mediaUrl, String time){
        this(name, EMPTY_URL, imgUrl, mediaUrl, time);
    }
   
    public Item(String name,  String time, URL webSiteUrl, URL imgUrl) {
        this(name, webSiteUrl, imgUrl, EMPTY_URL, time);
    }

    public Item(String name, URL webSiteUrl, URL imgUrl, URL mediaUrl, String time) {
        super(name);
        this.webSiteUrl = webSiteUrl;
        this.imgUrl = imgUrl;
        this.mediaUrl = mediaUrl;
        this.time = time;
    }

    public URL getWebSiteUrl() {
        return webSiteUrl;
    }

    public URL getImgUrl() {
        return imgUrl;
    }

    public URL getMediaUrl() {
        return mediaUrl;
    }

    public String getTime() {
        return time;
    }

    public boolean hasImgUrl() {
        return imgUrl != EMPTY_URL;
    }

    public boolean hasMediaUrl() {
        return mediaUrl != EMPTY_URL;
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
        if (hasMediaUrl()) {
            info += ", " + mediaUrl.toExternalForm();
        }
        info += ")";
        return info;
    }

    public void setImgUrl(URL imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public void setMediaUrl(URL mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setWebSiteUrl(URL webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

}
