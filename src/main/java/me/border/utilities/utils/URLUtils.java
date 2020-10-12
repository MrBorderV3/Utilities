package me.border.utilities.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class URLUtils {
    private static final Pattern URL_QUICKMATCH = Pattern.compile("^\\p{Alpha}[\\p{Alnum}+.-]*:.*$");

    public static URL getURL(String url) {
        try {
            if (!URL_QUICKMATCH.matcher(url).matches()) {
                final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                URL resource;
                if (url.charAt(0) == '/') {
                    resource = contextClassLoader.getResource(url.substring(1));
                } else {
                    resource = contextClassLoader.getResource(url);
                }
                if (resource == null) {
                    throw new IllegalArgumentException("Invalid URL or resource not found");
                }
                return resource;
            }

            return new URL(url);
        } catch (IllegalArgumentException | MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
