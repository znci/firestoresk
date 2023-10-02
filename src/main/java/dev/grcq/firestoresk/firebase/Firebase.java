package dev.grcq.firestoresk.firebase;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
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

    @Getter
    private static Firebase instance;

    @Getter
    private final FirebaseApp app;
    @Getter
    private final FirebaseDatabase database;
    @Getter
    private final Firestore firestore;

    public Firebase(String url) throws Exception {
        if (instance != null) {
            instance.app.delete();
            instance.firestore.close();
            instance = null;
        }

        File file = new File(FirestoreSK.getInstance().getDataFolder(), "serviceAccount.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(Files.newInputStream(file.toPath()));
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setDatabaseUrl(url)
                .build();

        this.app = FirebaseApp.initializeApp(options);
        this.database = FirebaseDatabase.getInstance(app);
        this.firestore = FirestoreClient.getFirestore(app);

        instance = this;
    }

}
