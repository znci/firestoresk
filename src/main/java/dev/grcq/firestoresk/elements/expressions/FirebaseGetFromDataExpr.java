package dev.grcq.firestoresk.elements.expressions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.grcq.firestoresk.FirestoreSK;
import dev.grcq.firestoresk.annotations.ExprType;
import dev.grcq.firestoresk.annotations.SkPattern;
import org.bukkit.event.Event;

import java.util.concurrent.ExecutionException;

@Name("Firebase Retrieve Value from Json")
@Since("2.7.0")
@SkPattern("[firestore] get [value] %string% from %fjson%")
@ExprType(ExpressionType.COMBINED)
public class FirebaseGetFromDataExpr extends SimpleExpression<Object> {

    private Expression<String> key;
    private Expression<JsonElement> json;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.key = (Expression<String>) expressions[0];
        this.json = (Expression<JsonElement>) expressions[1];
        return true;
    }


    @Override
    protected Object[] get(Event event) {
        String key = this.key.getSingle(event);
        JsonElement json = this.json.getSingle(event);

        if (key == null || json == null) return new Object[0];

        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has(key)) {
            JsonElement value = jsonObject.get(key);
            if (value.isJsonPrimitive()) {
                if (value.getAsJsonPrimitive().isBoolean()) {
                    return new Object[]{value.getAsBoolean()};
                } else if (value.getAsJsonPrimitive().isNumber()) {
                    return new Object[]{value.getAsNumber()};
                } else if (value.getAsJsonPrimitive().isString()) {
                    return new Object[]{value.getAsString()};
                }
            }
        }

        return new Object[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "get value from json";
    }
}

