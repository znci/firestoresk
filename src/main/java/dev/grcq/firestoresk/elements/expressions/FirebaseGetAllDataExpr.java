package dev.grcq.firestoresk.elements.expressions;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import dev.grcq.firestoresk.annotations.ExprType;
import dev.grcq.firestoresk.annotations.SkPattern;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Name("Firebase Get All Data")
@Since("2.7.0")
@SkPattern("[firestore] (get|fetch) all data in [collection] %string%")
@ExprType(ExpressionType.COMBINED)
public class FirebaseGetAllDataExpr extends SimpleExpression<Map<String, Object>> {

    private Expression<String> collection;

    @Override
    protected Map<String, Object>[] get(Event event) {
        String collection = this.collection.getSingle(event);
        if (collection == null) return new Map[0];

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection(collection).get();
        try {
            List<QueryDocumentSnapshot> snapshots = future.get().getDocuments();
            Map<String, Object> dataMap = new HashMap<>();

            for (QueryDocumentSnapshot snapshot : snapshots) {
                dataMap.put(snapshot.getId(), snapshot.getData());
            }

            System.out.println(dataMap);

            return new Map[]{dataMap};
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Map[0];
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Map<String, Object>> getReturnType() {
        return (Class<? extends Map<String, Object>>) new HashMap<String, Object>().getClass();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "get all data from a collection";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.collection = (Expression<String>) expressions[0];
        return true;
    }
}
