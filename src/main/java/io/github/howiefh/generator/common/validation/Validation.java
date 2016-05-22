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

    private static final String GET = "get";
    /** 缓存get方法 */
    private static Map<String, Method> getMethods = new HashMap<String, Method>();

    private Set<String> fields = new HashSet<String>();
    /** 验证器 */
    private IValidator validator;
    private Class clazz;

    public Validation(Class clazz, IValidator validator) throws IntrospectionException {
        this(clazz, validator, null);
    }

    public Validation(Class clazz, IValidator validator, Set<String> fields) throws IntrospectionException {
        this.clazz = clazz;
        this.validator = validator;
        register(fields);
    }

    /**
     * 注册clazz实例必填字段
     * @param fields
     * @throws IntrospectionException
     */
    public void register(Set<String> fields) throws IntrospectionException {
        if (clazz == null) {
            return;
        }
        if (fields != null) {
            doRegister(fields);
        }
    }

    private void doRegister(Set<String> fields) throws IntrospectionException {
        for (String field: fields) {
            doRegister(field);
        }
    }
    private void doRegister(String field) throws IntrospectionException {
        try {
            cacheMethod(clazz, field);
            fields.add(field);
        } catch (IntrospectionException e) {
            LOGGER.warn("Can not find {} field,{} register fails. Exception:{}", field, field, e.getMessage());
            throw e;
        }
    }
    private static void cacheMethod(Class clazz, String field) throws IntrospectionException {
        String key = key(GET, clazz, field);
        PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
        //获得读方法
        Method getMethod = pd.getReadMethod();
        getMethods.put(key, getMethod);
    }

    /**
     * 验证已经注册的字段
     * @param obj
     * @return
     * @throws ValidationException
     */
    boolean validate(Object obj) throws ValidationException {
        if (obj == null) {
            return false;
        }
        boolean isValid = false;
        Class clazz = obj.getClass();
        if (CollectionUtils.isEmpty(fields)){
            return true;
        }
        for (String field : fields) {
            String key = key(GET, clazz, field);
            Method getMethod = getMethods.get(key);
            try {
                Object o = getMethod.invoke(obj, null);
                if (!(isValid = validator.validate(o))){
                    throw new ValidationException(clazz + "." + field + " is fields！");
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
