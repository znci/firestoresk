package dev.grcq.firestoresk.elements.effects;

import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.grcq.firestoresk.FirestoreSK;
import dev.grcq.firestoresk.annotations.SkPattern;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

@SkPattern("[firestore] (get|fetch) all data from [collection] %string% [in]to %objects%")
public class FirebaseGetAllDataEffect extends Effect {

    private Expression<String> collection;
    private VariableString variable;
    private boolean isLocal;

    @Override
    protected void execute(Event event) {
        String collection = this.collection.getSingle(event);
        String variableName = this.variable.toString(event).toLowerCase();

        if (collection == null) return;

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection(collection).get();
        try {
            List<QueryDocumentSnapshot> snapshots = future.get().getDocuments();
            for (QueryDocumentSnapshot snapshot : snapshots) {
                handleAll(event, variableName.substring(0, variableName.length() - 3) + "::" + snapshot.getId(), snapshot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAll(Event event, String name, QueryDocumentSnapshot snapshot) {
        Map<String, Object> data = snapshot.getData();
        JsonElement element = FirestoreSK.GSON.toJsonTree(data);

        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                handle(event, name + "::" + entry.getKey(), entry.getValue());
            }
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
        return "get all data from firestore collection";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.collection = (Expression<String>) expressions[0];
        Expression<?> expr = expressions[1];
        if (expr instanceof Variable) {
            Variable<?> var = (Variable<?>) expr;
            if (var.isList()) {
                variable = VariableString.newInstance(var.toString().replaceFirst("local", ""));
                isLocal = var.isLocal();
            } else throw new IllegalArgumentException("The last expression must be a list");
        } else {
            throw new IllegalArgumentException("The last expression must be a variable");
        }
        return false;
    }
}
