package com.mforever78;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Vector;

/**
 * Created by MForever78 on 15/10/13.
 */

public class HtmlParser{
    private Document doc;

    public HtmlParser(String url) {
        int maxRetry = 10;
        int tried = 0;
        try {
            while(true) {
                try {
                    doc = Jsoup.connect(url).userAgent("Mozilla").get();
                    return;
                } catch (SocketTimeoutException e) {
                    System.out.println("Timed out, retrying...");
                    if (tried++ == maxRetry) {
                        throw e;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Your network is down. Please check and try again.");
        }
    }

    public Vector<String> getAllChapters() {
        Vector<String> chapterVec = new Vector<>();
        Elements chapters = doc.select("#chapters a");
        doc.outputSettings().indentAmount(4);
        for (Element chapter : chapters) {
            String chapterUrl = chapter.attr("href");
            System.out.println("Getting: " + chapterUrl);
            HtmlParser html = new HtmlParser(chapterUrl);
            chapterVec.add(html.getContent());
        }
        return chapterVec;
    }

    public String getContent() {
        Element content = doc.select(".padding-sides").first();
        content.select(".breadcrumb").first().html("");
        Elements paragraphs = content.select("p");
        StringBuilder passage = new StringBuilder();
        for (Element paragraph: paragraphs) {
            passage.append(paragraph.text()).append("\n");
        }
        return passage.toString();
    }

    public void print() {
        System.out.print(doc.toString());
    }

    public void printId(String id) {
        System.out.print(doc.getElementById(id).text());
    }
}
