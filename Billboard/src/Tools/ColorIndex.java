package Tools;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorIndex {

    public static final String[] COLORS = {
            "black",
            "white",
            "gray",
            "red",
            "blue",
            "yellow",
            "orange",
            "green",
            "purple"};

    private static final Color[] COLOR = {
            Color.black,
            Color.white,
            Color.gray,
            Color.red,
            Color.blue,
            Color.yellow,
            Color.orange,
            Color.green,
            Color.magenta
    };

    public static final Map<String, Color> color = new HashMap<>() {
        {
            put("black", Color.black);
            put("white", Color.white);
            put("gray", Color.gray);
            put("red", Color.red);
            put("blue", Color.blue);
            put("yellow", Color.yellow);
            put("orange", Color.orange);
            put("green", Color.green);
            put("purple", Color.magenta);
        }
    };

}
