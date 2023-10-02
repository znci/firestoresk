package dev.grcq.firestoresk.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dev.grcq.firestoresk.annotations.SkPattern;
import org.bukkit.event.Event;

@Name("Firebase Add Data")
@Since("2.7.0")
@SkPattern("[firestore] add data %objects% (with name|named|to document) %string% in [collection] %string%")
public class FirebaseAddDataEffect extends Effect {

    private Expression<Object> objects;
    private Expression<String> document;
    private Expression<String> collection;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.objects = (Expression<Object>) expressions[0];
        this.document = (Expression<String>) expressions[1];
        this.collection = (Expression<String>) expressions[2];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @Override
    protected void execute(Event event) {

    }
}
