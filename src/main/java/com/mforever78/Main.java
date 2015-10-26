package com.mforever78;

import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        HtmlParser html = new HtmlParser("http://www.online-literature.com/dh_lawrence/lady_chatterley_lover/");
        Vector<String> chapters = html.getAllChapters();
        FileManager.saveVecToFile(chapters, "lady_chatterley_lover.txt");
    }
}
