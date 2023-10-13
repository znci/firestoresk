package dev.grcq.firestoresk.firebase;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class FirestoreFields {

    @Getter
    private final Map<String, Object> fields = new HashMap<>();

}
