package com.mforever78;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        String line;
        BufferedReader br = null;

        Vector<String> contents = new Vector<String>();
        Vector<String> authors = new Vector<String>();

        try {
            // open connection
            URLConnection connection = new URL("http://www.qiushibaike.com/").openConnection();
            // add proper header in order to get full content
            connection.addRequestProperty("Accept", "text/html");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
            // get inputStream
            InputStream stream = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(stream));

            Boolean contentFlag = false;
            Boolean authorFlag = false;

            while ((line = br.readLine()) != null) {
                if (line.matches("<div.*class=\"content\".*>")) {
                    contentFlag = true;
                    continue;
                }
                if (line.matches("<div.*class=\"author\".*>")) {
                    authorFlag = true;
                    continue;
                }
                if (contentFlag) {
                    StringBuilder tempContent = new StringBuilder();
                    for (; contentFlag; line = br.readLine()) {
                        if (line.matches("</div>")) {
                            contentFlag = false;
                            continue;
                        }
                        if (line.matches("<.*>") || line.length() == 0) {
                            continue;
                        }
                        tempContent.append(line.replaceAll("<br/>", "\n"));
                    }
                    contents.add(tempContent.toString());
                }
                if (authorFlag) {
                    StringBuilder tempAuthor = new StringBuilder();
                    for (; authorFlag; line = br.readLine()) {
                        if (line.matches("</div>")) {
                            authorFlag = false;
                            continue;
                        }
                        if (line.matches("<.*>") || line.length() == 0) {
                            continue;
                        }
                        tempAuthor.append(line);
                    }
                    authors.add(tempAuthor.toString());
                }
            }

            Iterator content = contents.iterator();

            for (Object author : authors) {
                System.out.println(author + " 说:");
                System.out.println(content.next());
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
