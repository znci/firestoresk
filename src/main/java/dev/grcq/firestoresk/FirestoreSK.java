/*
    Copyright 2023 znci

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package dev.grcq.firestoresk;


import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dev.grcq.firestoresk.elements.ElementHandler;
import dev.grcq.firestoresk.elements.effects.FirebaseInitEffect;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirestoreSK extends JavaPlugin {

    private SkriptAddon addon;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.addon = Skript.registerAddon(this);

        ElementHandler elementHandler = new ElementHandler();
        elementHandler.registerEffects(
                new FirebaseInitEffect()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
