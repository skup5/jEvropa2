package jEvropa2.data;


import java.net.URL;



/**
 *
 * @author Roman Zelenik
 */
public abstract class E2Data {
    public static final URL EMPTY_URL = null;
    protected int id;
    protected String name;

    public E2Data (String name){
        this(0, name);
    }
    
    public E2Data(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
