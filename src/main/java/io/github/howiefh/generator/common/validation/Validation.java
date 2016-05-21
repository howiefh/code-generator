package io.github.howiefh.generator.common.validation;

import io.github.howiefh.generator.common.exception.ValidationException;
import io.github.howiefh.generator.common.util.CollectionUtils;
import io.github.howiefh.generator.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class Validation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);
    private static final String DEFAULT_GROUP = "default";
    private static final String GET = "get";
    private static final String SET = "set";
    /** 缓存get方法 */
    private static Map<String, Method> getMethods = new HashMap<String, Method>();
    /** 缓存set方法 */
    private static Map<String, Method> setMethods = new HashMap<String, Method>();
    /** 验证器 */
    private static RequiredValidator requiredValidator = new RequiredValidator();

    private Set<String> required = new HashSet<String>();
    private Set<String> defaults = new HashSet<String>();

    private Class clazz;
    private String group;

    public Validation(Class clazz){
        this(clazz, DEFAULT_GROUP);
    }
    public Validation(Class clazz, String group){
        this.clazz = clazz;
        this.group = group;
    }
    /**
     * 注册clazz实例必填字段
     * @param required
     * @throws IntrospectionException
     */
    public void register(Set<String> required) throws IntrospectionException {
        register(required, null);
    }

    /**
     * 注册clazz实例必填字段，默认字段
     * @param required
     * @param defaults
     * @throws IntrospectionException
     */
    public void register(Set<String> required, Set<String> defaults) throws IntrospectionException {
        if (clazz == null) {
            return;
        }
        if (required != null) {
            doRegister(this.required, required);
        }
        if (defaults != null) {
            doRegister(this.defaults, defaults);
        }
    }
    private void doRegister(Set<String> rules, Set<String> fields) throws IntrospectionException {
        for (String field: fields) {
            doRegister(rules, field);
        }
    }
    private void doRegister(Set<String> rules, String field) throws IntrospectionException {
        rules.add(field);
        try {
            cacheMethod(clazz, field);
        } catch (IntrospectionException e) {
            LOGGER.warn("Can not find {} field,{} register fails. Exception:{}", field, field, e.getMessage());
            //缓存方法失败，将字段移除
            rules.remove(field);
            throw e;
        }
    }
    private static void cacheMethod(Class clazz, String field) throws IntrospectionException {
        String key = key(GET, clazz, field);
        PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
        //获得读方法
        Method getMethod = pd.getReadMethod();
        getMethods.put(key, getMethod);
        key = key(SET, clazz, field);
        //获得写方法
        Method setMethod = pd.getWriteMethod();
        setMethods.put(key, setMethod);
    }

    /**
     * 初始化默认项，验证已经注册的必填字段
     * @param obj
     * @param <T>
     * @return
     * @throws ValidationException
     */
    public <T> boolean validate(T obj) throws ValidationException {
        if (obj == null) {
            return false;
        }
        initDefaults(obj);
        return validateRequired(obj);
    }
    private <T> void initDefaults(T obj) {
        Class clazz = obj.getClass();
        if (CollectionUtils.isEmpty(defaults)){
            return;
        }
        for (String field : defaults) {
            String key = key(GET, clazz, field);
            Method getMethod = getMethods.get(key);
            key = key(SET, clazz, field);
            Method setMethod = setMethods.get(key);
            try {
                Object o = getMethod.invoke(obj, null);
                if (!requiredValidator.validate(o)){
                    setMethod.invoke(obj, o);
                }
            } catch (IllegalAccessException e) {
                LOGGER.warn("{} can not access, E:{}", key, e.getMessage());
            } catch (InvocationTargetException e) {
                LOGGER.warn("{} invoke error, E:{}", key, e.getMessage());
            }
        }
    }

    private <T> boolean validateRequired(T obj) throws ValidationException {
        if (obj == null) {
            return false;
        }
        boolean isValid = false;
        Class clazz = obj.getClass();
        if (CollectionUtils.isEmpty(required)){
            return true;
        }
        for (String field : required) {
            String key = key(GET, clazz, field);
            Method getMethod = getMethods.get(key);
            try {
                Object o = getMethod.invoke(obj, null);
                if (!(isValid = requiredValidator.validate(o))){
                    throw new ValidationException(clazz + "." + field + " is required！");
                }
            } catch (IllegalAccessException e) {
                LOGGER.warn("{} can not access, E:{}", key, e.getMessage());
            } catch (InvocationTargetException e) {
                LOGGER.warn("{} invoke error, E:{}", key, e.getMessage());
            }
        }
        return isValid;
    }

    private static String key(String prefix, Class clazz, String field){
        String methodName = prefix + StringUtils.capitalize(field);
        return clazz + "#" + methodName;
    }
}
