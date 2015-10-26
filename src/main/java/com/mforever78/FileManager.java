package com.mforever78;

import java.io.*;
import java.util.Vector;

/**
 * Created by MForever78 on 15/10/26.
 */
public class FileManager {
    public static void saveVecToFile(Vector<String> contents, String filename) {
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for (String content: contents) {
                osw.append(content);
                osw.append("\n\n");
            }
            osw.close();
            fos.close();
            System.out.println("Write to file " + filename + " succeed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
