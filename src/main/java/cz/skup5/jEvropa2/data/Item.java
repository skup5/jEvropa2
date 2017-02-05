package cz.skup5.jEvropa2.data;

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
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("name=").append(name);
        sb.append(", time='").append(time).append('\'');
        sb.append(", webSiteUrl=").append(webSiteUrl);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", mediaUrl=").append(mediaUrl);
        sb.append('}');
        return sb.toString();
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
