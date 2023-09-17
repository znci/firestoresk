package dev.grcq.firestoresk;

import dev.grcq.firestoresk.elements.ElementHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirestoreSK extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        ElementHandler elementHandler = new ElementHandler();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
