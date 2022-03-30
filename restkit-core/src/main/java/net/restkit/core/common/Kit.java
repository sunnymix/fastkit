package net.restkit.core.common;

import java.lang.reflect.Method;

import static net.restkit.core.common.Kit.Beans.readPropValue;
import static net.restkit.core.common.Kit.Strings.notBlank;

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
        public static boolean notBlank(String str) {
            return str != null && str.trim().length() > 0;
        }
    }

}
