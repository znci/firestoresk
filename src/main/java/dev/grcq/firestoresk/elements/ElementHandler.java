/*
    Copyright 2023 znci

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package dev.grcq.firestoresk.elements;


import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Statement;
import ch.njol.skript.lang.util.SimpleExpression;
import dev.grcq.firestoresk.annotations.ExprType;
import dev.grcq.firestoresk.annotations.SkPattern;

public class ElementHandler {
    public void registerExpressions(Expression<?>... expressions) {
        for (Expression<?> expression : expressions) {
            registerExpression(expression);
        }
    }

    public void registerExpression(Expression<?> expression) {
        SkPattern pattern = expression.getClass().getDeclaredAnnotation(SkPattern.class);
        if (pattern == null) {
            throw new RuntimeException("Could not find '@SkPattern' annotation on " + expression.getClass().getName());
        }

        ExprType type = expression.getClass().getDeclaredAnnotation(ExprType.class);
        if (type == null) {
            throw new RuntimeException("Could not find '@ExprType' annotation on " + expression.getClass().getName());
        }

        Skript.registerExpression(expression.getClass(), expression.getReturnType(), type.value(), pattern.value());
    }

    public void registerEffects(Effect... effects) {
        for (Effect effect : effects) {
            registerEffect(effect);
        }
    }

    public void registerEffect(Effect effect) {
        SkPattern pattern = effect.getClass().getDeclaredAnnotation(SkPattern.class);
        if (pattern == null) {
            throw new RuntimeException("Could not find '@SkPattern' annotation on " + effect.getClass().getName());
        }

        Skript.registerEffect(effect.getClass(), pattern.value());
    }

}
