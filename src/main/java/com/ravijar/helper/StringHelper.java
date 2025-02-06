package com.ravijar.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : input.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String toKebabCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append('-');
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String extractUrlParameter(String route) {
        String pattern = ".*/\\{(\\w+)}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(route);

        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }

        return null;
    }

    public static String toColonRoute(String route) {
        if (route == null || route.isEmpty()) {
            return route;
        }
        return route.replaceAll("\\{(\\w+)}", ":$1");
    }

    public static String toTemplateLiteralRoute(String route) {
        if (route == null || route.isEmpty()) {
            return route;
        }
        return route.replaceAll("\\{(\\w+)}", "\\${$1}");
    }
}
