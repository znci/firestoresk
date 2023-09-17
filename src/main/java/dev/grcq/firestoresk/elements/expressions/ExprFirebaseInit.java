package dev.grcq.firestoresk.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Statement;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class ExprFirebaseInit extends SimpleExpression<Void> {
    /* never used skript api in java before, so don't criticise me pls */

    @Override
    protected Void[] get(Event event) {
        return new Void[0];
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Void> getReturnType() {
        return null;
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return false;
    }
}
