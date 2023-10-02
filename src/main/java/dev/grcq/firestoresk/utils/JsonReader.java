package dev.grcq.firestoresk.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class JsonReader {

    public static JsonObject read(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));

        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return (JsonObject) new JsonParser().parse(sb.toString());
    }

}
