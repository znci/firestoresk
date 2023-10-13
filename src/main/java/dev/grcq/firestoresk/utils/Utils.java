package dev.grcq.firestoresk.utils;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;

import java.lang.reflect.Field;

public class Utils {

    static {
        Field field = null;
        try {
            field = Variable.class.getDeclaredField("name");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Skript.error("Field 'name' was not found in " + Variable.class.getName());
        }
        NAME_FIELD = field;
    }

    private static final Field NAME_FIELD;

    public static VariableString getName(Variable<?> var) {
        try {
            return (VariableString) NAME_FIELD.get(var);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
