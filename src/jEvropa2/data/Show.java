package jEvropa2.data;


import java.net.URL;

/**
 *
 * @author Skup5
 */
public class Show extends E2Data {

    private URL webSiteUrl;

    public Show(String name, URL webSiteUrl) {
        super(name);
        this.webSiteUrl = webSiteUrl;
    }

    public URL getWebSiteUrl() {
        return webSiteUrl;
    }

    public String info() {
        return getName() + " (" + getWebSiteUrl().toExternalForm() + ")";
    }
}
