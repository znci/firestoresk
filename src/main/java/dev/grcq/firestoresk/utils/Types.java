package dev.grcq.firestoresk.utils;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import dev.grcq.firestoresk.FirestoreSK;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class Types {

    public static void init() {
        Classes.registerClass(
                new ClassInfo<>(JsonElement.class, "fjson")
                        .user("fjson")
                        .name("fjson")
                        .description("json")
                        .since("2.7.0")
                        .parser(new Parser<JsonElement>() {
                            @Override
                            public String toString(JsonElement element, int i) {
                                return element.toString();
                            }

                            @Override
                            public String toVariableNameString(JsonElement element) {
                                return toString(element, 1);
                            }

                            @Override
                            public boolean canParse(ParseContext context) {
                                return false;
                            }
                        }).serializer(new Serializer<JsonElement>() {

                            @Override
                            public Fields serialize(JsonElement element) throws NotSerializableException {
                                Fields fields = new Fields();
                                fields.putObject("json", element.toString());
                                return fields;
                            }

                            @Override
                            public void deserialize(JsonElement element, Fields fields) throws StreamCorruptedException, NotSerializableException {
                                assert false;
                            }

                            @Override
                            protected JsonElement deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                                Object field = fields.getObject("json");
                                if (field == null) return JsonNull.INSTANCE;
                                fields.removeField("json");
                                return FirestoreSK.GSON.fromJson((String) field, JsonElement.class);
                            }

                            @Override
                            public boolean mustSyncDeserialization() {
                                return false;
                            }

                            @Override
                            protected boolean canBeInstantiated() {
                                return false;
                            }
                        })
        );
    }

}
