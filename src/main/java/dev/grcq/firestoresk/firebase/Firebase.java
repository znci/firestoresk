package dev.grcq.firestoresk.firebase;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.v1.FirestoreSettings;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.LazyStringArrayList;
import dev.grcq.firestoresk.FirestoreSK;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Deprecated
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
        try {
            if (instance != null) {
                instance.getApp().delete();
            }
        } catch (Exception ignored) {}

        setInstance(this);

        File file = new File(FirestoreSK.getInstance().getDataFolder(), "serviceAccount.json");

        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
            this.firestore = FirestoreClient.getFirestore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.firestore == null) {
            FirestoreSK.getInstance().getLogger().severe("Firestore initialization failed!");
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
