package dev.grcq.firestoresk.elements.expressions;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.common.reflect.TypeToken;
import dev.grcq.firestoresk.annotations.ExprType;
import dev.grcq.firestoresk.annotations.SkPattern;
import dev.grcq.firestoresk.firebase.Firebase;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Name("Firebase Get Data")
@Since("2.7.0")
@SkPattern("[firestore] [(get|fetch)] data from [the] document %string% in [collection] %string%")
@ExprType(ExpressionType.COMBINED)
public class FirebaseGetDataExpr extends SimpleExpression<Object> {

    private Expression<String> document;
    private Expression<String> collection;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.document = (Expression<String>) expressions[0];
        this.collection = (Expression<String>) expressions[1];
        return true;
    }


    @Override
    protected Object[] get(Event event) {
        Firestore firestore = Firebase.getInstance().getFirestore();

        String collection = this.collection.getSingle(event);
        String document = this.document.getSingle(event);

        if (collection == null || document == null) return new Object[0];

        DocumentReference documentReference = firestore.collection(collection).document(document);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if (!documentSnapshot.exists()) {
                return new Object[0];
            }

            return new Object[] {documentSnapshot.getData()};
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
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
        return "get data from document";
    }
}
