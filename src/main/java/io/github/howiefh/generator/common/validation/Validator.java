package io.github.howiefh.generator.common.validation;

import io.github.howiefh.generator.common.exception.ValidationException;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/22
 * @version 1.0
 * @since 1.0
 */
public class Validator {
    private static final String DEFAULT_GROUP = "default";
    private static Map<String, Validation> validations = new HashMap<String, Validation>();

    public static void register(Rule rule, Class clazz, Set<String> fields) throws IntrospectionException {
        register(rule, DEFAULT_GROUP, clazz, fields);
    }
    public static void register(Rule rule, String group, Class clazz, Set<String> fields) throws IntrospectionException {
        register(rule.getValidator(), group, clazz, fields);
    }
    public static void register(IValidator validator, Class clazz, Set<String> fields) throws IntrospectionException {
        register(validator, DEFAULT_GROUP, clazz, fields);
    }
    public static void register(IValidator validator, String group, Class clazz, Set<String> fields) throws IntrospectionException {
        String key = group + "." + clazz;
        Validation validation = new Validation(clazz, validator, fields);
        validations.put(key, validation);
    }
    public static boolean validate(Object obj) throws ValidationException {
        return validate(obj, DEFAULT_GROUP);
    }
    public static boolean validate(Object obj, String group) throws ValidationException {
        if (obj == null) {
            throw new ValidationException("Object is null!");
        }
        group = group == null ? DEFAULT_GROUP : group;
        Class clazz = obj.getClass();
        String key = group + "." + clazz;
        Validation validation = validations.get(key);
        // 没有<分组.对象类型>对应的验证器，无法验证对象返回false
        return validation != null && validation.validate(obj);
    }
}
