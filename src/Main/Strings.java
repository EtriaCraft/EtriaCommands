package Main;

import java.util.List;

public enum Strings {

    PREFIX("[EtriaCommands] "),
    NO_PERMISSION("§cYou do not have permission to do that!"),
    INVALID_ARGUMENTS("§cInvalid arguments"),
    NOT_ENOUGH_ARGS("§cNot enough arguments"),
    TOO_MANY_ARGS("§cToo many arguments"),
    PLAYER_OFFLINE("§cPlayer is not online!");
    
    private static String[] RVALS = {"M", "CM", "D", "CD", "C", "XC", "L",
        "XL", "X", "IX", "V", "IV", "I"};

    private static int[] IVALS = {1000, 900, 500, 400, 100, 90,
    							  50, 40, 10, 9, 5, 4, 1};
    private String toString;

    private Strings(String s) {
        this.toString = s;
    }

    @Override
    public String toString() {
        return this.toString;
    }

    public static String buildString(String[] args, int start, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            sb.append(args[i]).append(delimiter);
        }
        return sb.toString().trim();
    }
    
    public static String buildString(@SuppressWarnings("rawtypes") List args, int start, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.size(); i++) {
            sb.append(args.get(i)).append(delimiter);
        }
        return sb.toString().trim();
    }

    public static String toTitle(String s) {
        StringBuilder sb = new StringBuilder();
        for (String str : s.toLowerCase().split("_")) {
            sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String toRomanNumeral(int value) {
        if (value <= 0) return "";
        String roman = "";
        for (int i = 0; i < RVALS.length; i++) {
            while (value >= IVALS[i]) {
                roman += RVALS[i];
                value -= IVALS[i];
            }
        }
        return roman;
    }
    
}