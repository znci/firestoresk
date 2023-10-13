/*
    Copyright 2023 znci

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package dev.grcq.firestoresk;


import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import dev.grcq.firestoresk.elements.ElementHandler;
import dev.grcq.firestoresk.elements.effects.*;
import dev.grcq.firestoresk.elements.expressions.FirebaseGetAllDataExpr;
import dev.grcq.firestoresk.elements.expressions.FirebaseGetDataExpr;
import dev.grcq.firestoresk.elements.expressions.FirebaseGetFromDataExpr;
import dev.grcq.firestoresk.firebase.Firebase;
import dev.grcq.firestoresk.utils.Types;
import io.grpc.LoadBalancerRegistry;
import io.grpc.internal.PickFirstLoadBalancerProvider;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FirestoreSK extends JavaPlugin {

    public static Gson GSON = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .setPrettyPrinting()
            .create();

    @Getter
    private static FirestoreSK instance;

    @Getter
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        File serviceAccountFile = new File(getDataFolder(), "serviceAccount.json");
        if (!serviceAccountFile.exists()) {
            try {
                serviceAccountFile.createNewFile();
                FileWriter writer = new FileWriter(serviceAccountFile);
                writer.write(GSON.toJson(Firebase.getServiceAccountFileFormat()));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        LoadBalancerRegistry.getDefaultRegistry().register(new PickFirstLoadBalancerProvider());

        this.addon = Skript.registerAddon(this);
        Types.init();

        ElementHandler elementHandler = new ElementHandler();
        elementHandler.registerEffects(
                new FirebaseGetAllDataEffect(),
                new FirebaseInitEffect(),
                new FirebaseGetDataEffect(),
                new FirebaseAddDataToVarEffect(),
                new FirebaseAddDataEffect()
        );
        elementHandler.registerExpressions(
                //new FirebaseGetDataExpr(),
                //new FirebaseGetAllDataExpr(),
                //new FirebaseGetFromDataExpr()
        );
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        try {
            FirebaseApp.getInstance().delete();
        } catch (Exception ignored) {}
    }
}
