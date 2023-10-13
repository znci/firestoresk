package dev.grcq.firestoresk.elements.effects;

import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import dev.grcq.firestoresk.annotations.SkPattern;
import dev.grcq.firestoresk.firebase.FirestoreFields;
import dev.grcq.firestoresk.utils.Utils;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;

@SkPattern("[firestore] add field[s] key %string% [and] value (%-string%|%-number%|%-boolean%) to %object%")
public class FirebaseAddDataToVarEffect extends Effect {

    private Expression<String> key;
    private Expression<String> valueString;
    private Expression<Number> valueNumber;
    private Expression<Boolean> valueBoolean;
    private VariableString variable;
    private boolean isLocal;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.key = (Expression<String>) expressions[0];

        this.valueString = (Expression<String>) expressions[1];
        this.valueNumber = (Expression<Number>) expressions[2];
        this.valueBoolean = (Expression<Boolean>) expressions[3];

        Expression<?> expr = expressions[4];
        if (expr instanceof Variable) {
            Variable<?> var = (Variable<?>) expr;
            if (!var.isList()) {
                variable = Utils.getName(var);
                isLocal = var.isLocal();
            }
        } else {
            throw new IllegalArgumentException("The last expression must be a variable");
        }
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "add data to a variable";
    }

    @Override
    protected void execute(Event event) {
        String key = this.key.getSingle(event);
        Object value = (valueString != null ? valueString.getSingle(event) : null) != null ? valueString.getSingle(event) : (valueNumber != null ? valueNumber.getSingle(event) : null) != null ? valueNumber.getSingle(event) : (valueBoolean != null ? valueBoolean.getSingle(event) : null);
        if (key == null || value == null) return;

        Object o = Variables.getVariable(variable.toString(event), event, isLocal);
        FirestoreFields fields;
        if (o == null || !(o instanceof FirestoreFields)) {
            fields = new FirestoreFields();
        } else {
            fields = (FirestoreFields) o;
        }

        fields.getFields().put(key, value);
        set(event, variable.toString(event), fields);
    }

    private void set(Event event, String name, Object value) {
        Variables.setVariable(name.toLowerCase(), value, event, isLocal);
    }
}
