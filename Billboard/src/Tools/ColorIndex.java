package Tools;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorIndex {

    public static final String[] COLOR_STRINGS = {
            "black",
            "white",
            "gray",
            "red",
            "blue",
            "yellow",
            "orange",
            "green",
            "purple"};

    private static final Color[] COLORS = {
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

    private static final Map<String, Color> CFS = new HashMap<>() {
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

    private static final Map<Color, String> SFC = new HashMap<>() {
        {
            put(Color.black, "black");
            put(Color.white, "white");
            put(Color.gray, "gray");
            put(Color.red, "red");
            put(Color.blue, "blue");
            put(Color.yellow, "yellow");
            put(Color.orange, "orange");
            put(Color.green, "green");
            put(Color.magenta, "purple");
        }
    };

    public static Color colorFromString(String string) {
        return CFS.get(string);
    }

    public static String stringFromColor(Color color) {
        return SFC.get(color);
    }
}