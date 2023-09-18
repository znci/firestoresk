/*
    Copyright 2023 znci

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/

package dev.grcq.firestoresk.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.events.EvtScript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import dev.grcq.firestoresk.annotations.SkPattern;
import dev.grcq.firestoresk.firebase.Firebase;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerLoadEvent;

@Name("Firebase Init")
@Description("Initialise firebase to be ready for use.")
@Examples({
        "on script load:",
        "init firestore using access token \"token\""
})
@Since("2.7.0")
@SkPattern("(init|authenticate) firestore (with|using) [access] token %string% [with scopes %strings%] [and expiry %date%]")
public class FirebaseInitEffect extends Effect {

    private Expression<String> accessToken;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        accessToken = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }

    @Override
    protected void execute(Event event) {
        //if ((event instanceof EvtScript))

        new Firebase(accessToken.getSingle(event));
    }
}
