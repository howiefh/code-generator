package io.github.howiefh.generator.common.config;

import io.github.howiefh.generator.common.exception.ConfigInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author fenghao on 2016/5/22
 * @version 1.0
 * @since 1.0
 */
public class DefaultConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfig.class);

    public static Config initDefaultConfig(Config config) throws ConfigInitException {
        try {
            if (config == null) {
                config = new Config();
            }
            setGet(config);
            initDefaultTypes(config.getTypes());
            for (TableCfg tableCfg : config.getTables()) {
                setGet(tableCfg);
                initDefaultTypes(tableCfg.getTypes());
            }
        } catch (IllegalAccessException e) {
            error(e);
        } catch (InstantiationException e) {
            error(e);
        } catch (IntrospectionException e) {
            error(e);
        } catch (InvocationTargetException e) {
            error(e);
        }
        return config;
    }

    private static void initDefaultTypes(Set<TypeCfg> typeCfgs) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        for (TypeCfg typeCfg : typeCfgs) {
            setGet(typeCfg);
            for (ImplementCfg implementCfg : typeCfg.getImpls()) {
                setGet(implementCfg);
            }
        }
    }

    private static void error(Throwable e) throws ConfigInitException {
        LOGGER.error("Error:{}", e.getMessage());
        throw new ConfigInitException("Init default value error.", e);
    }

    private static void setGet(Object obj) throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        Class clazz = obj.getClass();
        //获得类的所有属性
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }

            PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
            //获得写方法
            Method setMethod = pd.getWriteMethod();
            //获得读方法
            Method getMethod = pd.getReadMethod();
            //获得set方法的参数
            Class[] classes = setMethod.getParameterTypes();
            //判断参数不为空且值唯一
            if (classes != null && classes.length == 1) {
                //调用set方法，设置get方法到的值，为null的话就会设置默认值。
                setMethod.invoke(obj, getMethod.invoke(obj, null));
            }
        }
    }
}
