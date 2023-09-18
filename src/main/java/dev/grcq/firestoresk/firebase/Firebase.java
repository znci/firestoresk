package dev.grcq.firestoresk.firebase;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.Getter;

public class Firebase {

    @Getter
    private static Firebase instance;

    public Firebase(String token) {
        GoogleCredentials credentials = GoogleCredentials.newBuilder()
                .setAccessToken(AccessToken.newBuilder()
                        .setTokenValue(token)
                        .build())
                .build();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);

        instance = this;
    }

}
