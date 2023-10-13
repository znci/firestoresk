package dev.grcq.firestoresk.elements.effects;


import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.grcq.firestoresk.FirestoreSK;
import dev.grcq.firestoresk.annotations.SkPattern;
import dev.grcq.firestoresk.utils.Utils;
import org.bukkit.event.Event;

import java.util.Map;

@SkPattern("[firestore] (get|fetch) data from [the] document %string% in [collection] %string% [in]to %objects%")
public class FirebaseGetDataEffect extends Effect {

    private Expression<String> document;
    private Expression<String> collection;
    private VariableString variable;
    private boolean isLocal;

    @Override
    protected void execute(Event event) {
        String document = this.document.getSingle(event);
        String collection = this.collection.getSingle(event);
        String variableName = this.variable.toString(event).toLowerCase();

        if (document == null || collection == null) return;

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(collection).document(document);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if (!documentSnapshot.exists()) {
                return;
            }

            Map<String, Object> data = documentSnapshot.getData();
            JsonElement element = FirestoreSK.GSON.toJsonTree(data);

            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    handle(event, variableName.substring(0, variableName.length() - 3) + "::" + entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handle(Event e, String variableName, Object value) {
        if (value instanceof JsonObject) {
            JsonObject object = (JsonObject) value;
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                JsonElement element = entry.getValue();
                if (element.isJsonObject()) {
                    handle(e, variableName + "::" + entry.getKey(), element);
                    continue;
                }
            }
        } else {
            JsonPrimitive primitive = (JsonPrimitive) value;
            if (primitive.isBoolean()) {
                set(e, variableName, primitive.getAsBoolean());
                return;
            }

            if (primitive.isNumber()) {
                set(e, variableName, primitive.getAsNumber());
                return;
            }

            if (primitive.isString()) {
                set(e, variableName, primitive.getAsString());
                return;
            }

            if (primitive.isJsonNull()) {
                set(e, variableName, null);
                return;
            }

            if (primitive.isJsonArray()) {
                JsonArray array = primitive.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement element = array.get(i);
                    handle(e, variableName + "::" + i + 1, element);
                }
            }

            set(e, variableName, value);
        }
    }

    private void set(Event event, String name, Object value) {
        Variables.setVariable(name.toLowerCase(), value, event, isLocal);
    }

    @Override
    public String toString(Event event, boolean b) {
        return "put data into variable array";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        document = (Expression<String>) expressions[0];
        collection = (Expression<String>) expressions[1];

        Expression<?> expression = expressions[2];
        if (expression instanceof Variable) {
            Variable<?> var = (Variable<?>) expression;
            if (var.isList()) {
                variable = Utils.getName(var);
                isLocal = var.isLocal();
                return true;
            }
        }

        Skript.error("Given variable must be a list.");
        return false;
    }
}
