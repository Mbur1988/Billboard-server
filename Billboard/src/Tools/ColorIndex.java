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

    /**
     * Converts a String in a Hex form and spits out a 9 number string representing rrrgggbbb
     *
     * @param string String that represents a hex colour Ex %FFFFFF
     * @return String representing red green blue Ex 255255255
     */
    public static String ConvertStringToRGB(String string) {
        String Out;
        int R =(Integer.parseInt(string.substring(1,3),16));
        int G =(Integer.parseInt(string.substring(3,5),16));
        int B =(Integer.parseInt(string.substring(5,7),16));
        return Out = "" + R + G + B;
    }

    /**
     * Converts a RGB 9 number string into a Hex String.
     * @param r = red   (0-255)
     * @param g = green (0-255)
     * @param b = blue  (0-255)
     * @return String of the entered RGB as a Hex representation in a string. Ex #FFFFFF
     */
    public static String ConvertRGBtoString(int r, int g, int b) {
        String hex = String.format("#%02X%02X%02X", r, g, b);
        return hex;
    }

}