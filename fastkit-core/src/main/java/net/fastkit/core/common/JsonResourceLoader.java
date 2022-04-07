package net.fastkit.core.common;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;

/**
 * @author sunnymix
 */
public class JsonResourceLoader {

    public static <T> T load(ResourceClassPath path, TypeReference<T> type) {
        if (Value.isEmpty(path) || type == null) {
            return null;
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream io = classloader.getResourceAsStream(path.value());
        String json = "";
        try {
            if (io != null) {
                json = Kit.Strings.from(io);
            }
        } catch (Throwable ignored) {
        }

        return Json.fromJson(json, new TypeReference<T>() {
        });
    }

    public static class ResourceClassPath implements Value<String> {

        private final String value;

        public ResourceClassPath(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

        @Override
        public boolean isEmpty() {
            return Kit.Strings.isBlank(value);
        }

    }

}
