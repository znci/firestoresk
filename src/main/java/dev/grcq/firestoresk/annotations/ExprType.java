package dev.grcq.firestoresk.annotations;

import ch.njol.skript.lang.ExpressionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExprType {
    ExpressionType value();
}
