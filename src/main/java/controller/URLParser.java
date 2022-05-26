package controller;

public class URLParser {
    public static String parseURLByLevel(String url, int level){
        return url.split("/")[level];
    }
}
