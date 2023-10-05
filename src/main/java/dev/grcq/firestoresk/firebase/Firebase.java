package dev.grcq.firestoresk.firebase;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.grcq.firestoresk.FirestoreSK;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Firebase {

    @Getter
    private static JsonObject serviceAccountFileFormat = (JsonObject) new JsonParser().parse(
            "{\n" +
            "  \"type\": \"\",\n" +
            "  \"project_id\": \"\",\n" +
            "  \"private_key_id\": \"\",\n" +
            "  \"private_key\": \"\",\n" +
            "  \"client_email\": \"\",\n" +
            "  \"client_id\": \"\",\n" +
            "  \"auth_uri\": \"\",\n" +
            "  \"token_uri\": \"\",\n" +
            "  \"auth_provider_x509_cert_url\": \"\",\n" +
            "  \"client_x509_cert_url\": \"\"\n" +
            "}"
    );

    private static Firebase instance;

    @Getter
    private FirebaseApp app;
    @Getter
    private Firestore firestore;

    public Firebase(String url) throws IOException {
        this(url, url.replace("https://", "").replace("http://", "").split("\\.")[0]);
    }

    public Firebase(String url, String projectId) throws IOException {
        FirestoreSK.getInstance().getLogger().severe("111");
        try {
            if (instance != null) {
                FirestoreSK.getInstance().getLogger().severe("222");
                instance.getFirestore().close();
                FirestoreSK.getInstance().getLogger().severe("333");
                instance.getApp().delete();
            }
        } catch (Exception ignored) {}

        FirestoreSK.getInstance().getLogger().severe("4");
        setInstance(this);

        FirestoreSK.getInstance().getLogger().severe("a");
        File file = new File(FirestoreSK.getInstance().getDataFolder(), "serviceAccount.json");
        FirestoreSK.getInstance().getLogger().severe("b");
        GoogleCredentials credentials = GoogleCredentials.fromStream(Files.newInputStream(file.toPath()));
        FirestoreSK.getInstance().getLogger().severe("c");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        try {
            FirestoreSK.getInstance().getLogger().severe("d");
            this.app = FirebaseApp.initializeApp(options);
            FirestoreSK.getInstance().getLogger().severe("e");
            this.firestore = FirestoreClient.getFirestore();
            FirestoreSK.getInstance().getLogger().severe("f");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Firebase getInstance() {
        System.out.println(instance);
        return instance;
    }

    public static void setInstance(Firebase instance) {
        Firebase.instance = instance;
    }
}
