/*
    Copyright 2023 znci

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
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
