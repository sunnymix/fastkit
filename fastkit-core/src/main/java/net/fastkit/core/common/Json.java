package net.fastkit.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimeZone;

/**
 * @author sunnymix
 */
public class Json extends ObjectMapper {
    public static final Json JSON = new Json();

    public Json() {
        setTimeZone(TimeZone.getDefault());
        setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new EnumModule());
    }

    public static String toJson(Object bean) {
        try {
            return JSON.writeValueAsString(bean);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJson(final String json, final TypeReference<T> type) {
        try {
            return JSON.readValue(json, type);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJson(final String json, final Class<T> type) {
        try {
            return JSON.readValue(json, type);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static <T> T fromJsonResource(final String resource, final TypeReference<T> type) {
        if (Kit.Strings.notBlank(resource) || type == null) {
            return null;
        }
        String json = Kit.Resources.load(resource);
        return fromJson(json, new TypeReference<T>() {
        });
    }

    public static class EnumModule extends SimpleModule {

        private static final String[] propNames = {"value", "code"};

        public EnumModule() {
            addSerializer(Enum.class, new Serializer());
            addDeserializer(Enum.class, new Deserializer());
        }

        @SuppressWarnings({"rawtypes"})
        public static class Serializer extends JsonSerializer<Enum> {

            public Serializer() {
            }

            @Override
            public void serialize(Enum enumItem, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (enumItem == null) {
                    gen.writeNull();
                    return;
                }
                try {
                    for (String prop : EnumModule.propNames) {
                        Method readMethod = Kit.Beans.getReadMethod(enumItem, prop);
                        if (readMethod != null) {
                            readMethod.setAccessible(true);
                            gen.writeObject(readMethod.invoke(enumItem));
                            return;
                        }
                    }
                    gen.writeString(enumItem.name());
                } catch (IllegalAccessException | InvocationTargetException err) {
                    throw new RuntimeException(err.getCause());
                }
            }

        }

        @SuppressWarnings({"rawtypes"})
        public static class Deserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

            private Class<Enum> enumClass;

            public Deserializer() {
            }

            @Override
            public Enum deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
                String text = parser.getText();
                for (String prop : EnumModule.propNames) {
                    Enum enumItem = Kit.Enums.getEnumItem(enumClass, prop, text);
                    if (enumItem != null) {
                        return enumItem;
                    }
                }
                return null;
            }

            @Override
            @SuppressWarnings({"rawtypes", "unchecked"})
            public JsonDeserializer createContextual(DeserializationContext ctx, BeanProperty property) {
                Class rawClass = ctx.getContextualType().getRawClass();
                Class<Enum> enumClass = (Class<Enum>) rawClass;
                Deserializer clone = new Deserializer();
                clone.setEnumClass(enumClass);
                return clone;
            }

            public void setEnumClass(Class<Enum> enumClass) {
                this.enumClass = enumClass;
            }

        }
    }
}
