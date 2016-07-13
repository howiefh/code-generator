package io.github.howiefh.generator.ui.handle;

import io.github.howiefh.generator.common.util.StringUtils;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fenghao, 2016/7/13.
 * @version 1.0
 * @since 1.0
 */
public class CommonHandler {
    protected Map<String, FastMethod> getMethods = new HashMap<String, FastMethod>();
    protected Map<String, FastMethod> setMethods = new HashMap<String, FastMethod>();
    private static final String GET = "get";
    private static final String SET = "set";

    protected Object invokeGetMethod(Object object, String field) throws IntrospectionException, InvocationTargetException {
        Class clazz = object.getClass();
        String key = key(clazz, field);
        FastMethod fastMethod = getMethods.get(key);
        if (fastMethod == null) {
            fastMethod = cacheGetMethod(clazz, field);
        }

        return fastMethod.invoke(object, null);
    }

    protected Object invokeSetMethod(Object object, String field, Object param) throws IntrospectionException, InvocationTargetException {
        Class clazz = object.getClass();
        String key = key(clazz, field);
        FastMethod fastMethod = setMethods.get(key);
        if (fastMethod == null) {
            fastMethod = cacheSetMethod(clazz, field);
        }

        return fastMethod.invoke(object, new Object[]{param});
    }

    protected FastMethod cacheGetMethod(Class clazz, String field) throws IntrospectionException {
        String key = key(clazz, field);
        FastClass fastClass = FastClass.create(clazz);
        FastMethod fastMethod = fastClass.getMethod(GET + StringUtils.capitalize(field), null);
        getMethods.put(key, fastMethod);
        return fastMethod;
    }

    protected FastMethod cacheSetMethod(Class clazz, String field) throws IntrospectionException {
        return cacheSetMethod(clazz, field, List.class);
    }

    protected FastMethod cacheSetMethod(Class clazz, String field, Class...parameterTypes) throws IntrospectionException {
        String key = key(clazz, field);
        FastClass fastClass = FastClass.create(clazz);
        FastMethod fastMethod = fastClass.getMethod(SET + StringUtils.capitalize(field), parameterTypes);
        setMethods.put(key, fastMethod);
        return fastMethod;
    }

    private String key(Class clazz, String field) {
        String methodName = StringUtils.capitalize(field);
        return clazz + "#" + methodName;
    }
}
