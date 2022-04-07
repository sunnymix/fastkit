package net.fastkit.core.common;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static net.fastkit.core.common.Kit.Beans.readPropValue;
import static net.fastkit.core.common.Kit.Strings.notBlank;

/**
 * @author sunnymix
 */
public class Kit {

    public static class Enums {

        @SuppressWarnings({"rawtypes"})
        public static <T extends Enum> T getEnumItem(Class<T> type, String propName, String propValue) {
            T[] enumItems = type.getEnumConstants();
            if (enumItems != null && enumItems.length > 0) {
                try {
                    for (T enumItem : enumItems) {
                        Object enumPropValue = readPropValue(enumItem, propName);
                        if (enumPropValue != null &&
                                enumPropValue.toString().equals(propValue)) {
                            return enumItem;
                        }
                        if (enumItem.name().equals(propValue)) {
                            return enumItem;
                        }
                    }
                } catch (Throwable ignored) {
                }
            }
            return null;
        }

    }

    public static class Beans {

        public static Object readPropValue(Object bean, String propName) {
            Method method = getReadMethod(bean, propName);
            if (method != null) {
                try {
                    method.setAccessible(true);
                    return method.invoke(bean);
                } catch (Throwable ignored) {
                }
            }
            return null;
        }

        public static Method getReadMethod(Object bean, String prop) {
            if (bean != null && notBlank(prop)) {
                String methodName = "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
                try {
                    return bean.getClass().getDeclaredMethod(methodName);
                } catch (Throwable ignored) {
                }
            }
            return null;
        }

    }

    public static class Strings {

        public static boolean isBlank(String str) {
            return str == null || str.trim().length() == 0;
        }

        public static boolean notBlank(String str) {
            return !isBlank(str);
        }

        public static String from(InputStream input) throws IOException {
            StringWriter writer = new StringWriter();
            InputStreamReader in = new InputStreamReader(input, StandardCharsets.UTF_8);
            char[] buffer = new char[4096];
            long count;
            int n;
            for (count = 0L; -1 != (n = in.read(buffer)); count += (long) n) {
                writer.write(buffer, 0, n);
            }
            return writer.toString();
        }

        public static class StringWriter extends Writer implements Serializable {

            private final StringBuilder builder;

            public StringWriter() {
                this.builder = new StringBuilder();
            }

            @Override
            public void write(char[] buffer, int off, int len) throws IOException {
                if (buffer != null) {
                    this.builder.append(buffer, off, len);
                }
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public void close() throws IOException {
            }

        }

    }

}
