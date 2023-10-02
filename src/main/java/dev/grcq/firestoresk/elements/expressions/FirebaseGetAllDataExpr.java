package dev.grcq.firestoresk.elements.expressions;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dev.grcq.firestoresk.annotations.ExprType;
import dev.grcq.firestoresk.annotations.SkPattern;
import org.bukkit.event.Event;

@Name("Firebase Get All Data")
@Since("2.7.0")
@SkPattern("[firestore] (get|fetch) all data in [collection] %string%")
@ExprType(ExpressionType.COMBINED)
public class FirebaseGetAllDataExpr extends SimpleExpression<Object> {

    @Override
    protected Object[] get(Event event) {
        return new Object[0];
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "get all data from a collection";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
