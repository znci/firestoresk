package dev.grcq.firestoresk.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import dev.grcq.firestoresk.annotations.SkPattern;
import dev.grcq.firestoresk.firebase.FirestoreFields;
import dev.grcq.firestoresk.utils.Utils;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Name("Firebase Add Data")
@Since("2.7.0")
@SkPattern("[firestore] add data %object% to [document] %string% in [collection] %string%")
public class FirebaseAddDataEffect extends Effect {

    private boolean isLocal;
    private VariableString variable;
    private Expression<String> document;
    private Expression<String> collection;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        Expression<?> expr = expressions[0];
        if (expr instanceof Variable) {
            Variable<?> var = (Variable<?>) expr;
            if (!var.isList()) {
                variable = Utils.getName(var);
                isLocal = var.isLocal();
            }
        } else {
            throw new IllegalArgumentException("The first expression must be a variable");
        }
        this.document = (Expression<String>) expressions[1];
        this.collection = (Expression<String>) expressions[2];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "add data to firestore document";
    }

    @Override
    protected void execute(Event event) {
        String document = this.document.getSingle(event);
        String collection = this.collection.getSingle(event);

        if (document == null || collection == null) return;

        Object o = Variables.getVariable(variable.toString(event), event, isLocal);
        FirestoreFields fields;
        if (o == null || !(o instanceof FirestoreFields)) {
            fields = new FirestoreFields();
        } else {
            fields = (FirestoreFields) o;
        }

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(collection).document(document);

        for (Map.Entry<String, Object> entry : fields.getFields().entrySet()) {
            documentReference.update(entry.getKey(), entry.getValue());
        }
    }
}
