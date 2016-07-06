package io.github.howiefh.generator.common.util;

import io.github.howiefh.generator.GeneratorContext;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.ImplementCfg;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.exception.GeneratorException;

import java.util.*;

import static io.github.howiefh.generator.common.util.CollectionUtils.isEmpty;
import static io.github.howiefh.generator.common.util.CollectionUtils.isNotEmpty;
import static io.github.howiefh.generator.common.util.StringUtils.isNotBlank;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class GeneratorUtils {


    /**
     * 如果types集合中有和typeCfg相等的对象，就将他们合并，返回合并后的对象
     *
     * @param types
     * @param typeCfg
     * @return
     * @see #union(TypeCfg, TypeCfg)
     */
    public static TypeCfg unionIfContains(Set<TypeCfg> types, TypeCfg typeCfg) throws CloneNotSupportedException {
        if (isEmpty(types)) {
            return typeCfg;
        }
        for (TypeCfg type : types) {
            if (type.equals(typeCfg)) {
                return union(typeCfg, type);
            }
        }
        return typeCfg;
    }


    /**
     * 合并type1和type2，返回合并后的结果
     * <p/>
     * 合并规则：
     * 1. type2中的属性会覆盖type1的属性
     * 2. 只有dependencies属性是合并两个对象的
     * <pre>
     *     type1 == null, type2 == null; return null
     *     type1 == null, type2 != null; return type2
     *     type1 != null, type2 == null; return type1
     *     type1.name != type2.name; return type1
     *     type1 = {"name": "model","template": "Entity.java.ftl","target": "D:/","pkg": "io.github.howiefh.domain","suffix": ".java", "dependencies": ["foo"]}
     *     type2 = {"name": "model","template": "","target": "","pkg": "","suffix": "Entity.java", "dependencies": ["bar"]}:
     *     return {"name": "model","template": "Entity.java.ftl","target": "D:/","pkg": "io.github.howiefh.domain","suffix": "Entity.java", "dependencies": ["foo","bar"]}
     * </pre>
     *
     * @param type1
     * @param type2
     * @return
     */
    public static TypeCfg union(TypeCfg type1, TypeCfg type2) throws CloneNotSupportedException {
        if (type1 == null) {
            return type2;
        }
        if (type2 == null) {
            return type1;
        }
        if (!type1.equals(type2)) {
            return type1;
        }
        TypeCfg type = type1.clone();
        if (isNotBlank(type2.getTemplate())) {
            type.setTemplate(type2.getTemplate());
        }
        if (isNotBlank(type2.getTarget())) {
            type.setTarget(type2.getTarget());
        }
        if (isNotBlank(type2.getPkg())) {
            type.setPkg(type2.getPkg());
        }
        if (isNotBlank(type2.getSuffix())) {
            type.setSuffix(type2.getSuffix());
        }
        if (isNotEmpty(type2.getIgnoreImpls())) {
            type.setIgnoreImpls(type2.getIgnoreImpls());
        }
        if (isNotEmpty(type2.getImpls())) {
            type.setImpls(type2.getImpls());
        }
        if (isNotEmpty(type2.getImpls())) {
            type.setImpls(type2.getImpls());
        }
        Set<String> dependencies1 = type1.getDependencies();
        Set<String> dependencies2 = type2.getDependencies();
        if (dependencies1 != null && dependencies2 != null) {
            dependencies1.addAll(dependencies2);
        } else if (dependencies2 != null) {
            dependencies1 = dependencies2;
        }
        type.setDependencies(dependencies1);
        return type;
    }

    /**
     * 解析Type的实际依赖包，如果config.types中定义了type.dependencies中的依赖foo（foo是另一个type实例的name），就用foo.pkg替换原来type.dependencies中的依赖foo；
     * 然后将config.types[foo.name]和config.tableCfg.types[foo.name]合并得到t，t.impls加入到返回的map中
     * <p/>
     * 比如有
     * <pre>
     * {
     * "types":[
     * {
     * "name": "model",
     * "template": "Model.java.ftl",
     * "pkg": "io.github.howiefh.model"
     * },
     * {
     * "name": "dao",
     * "template": "Dao.java.ftl",
     * "pkg": "io.github.howiefh.dao",
     * "dependencies":["model"]
     * }
     * ]
     * "tables":[
     * {
     * "name": "sys_user",
     * types:[
     * {
     * "name": "model",
     * "impls": [{"name": "User","columns":["username","email"]}]
     * }
     * }
     * ]
     * }
     * </pre>
     * 那么解析之后dao类型的返回map将会是
     * {
     * "dependencies":["io.github.howiefh.model.*"],
     * "User":["username","email"]
     * }
     *
     * @param context
     * @return map {"dependencies":"...", "${impl.name}":"${impl.columns}"}
     */
    public static Map<String, Set<String>> parseDependencies(GeneratorContext context) throws GeneratorException {
        try {
            TableCfg tableCfg = context.getTableCfg();
            TypeCfg typeCfg = context.getTypeCfg();
            if (typeCfg != null && isNotEmpty(typeCfg.getDependencies())) {
                Map<String, Set<String>> result = new HashMap<String, Set<String>>();
                Set<String> dependencies = new HashSet<String>();
                for (String dep : typeCfg.getDependencies()) {
                    TypeCfg tmpType = new TypeCfg();
                    tmpType.setName(dep);
                    tmpType = CollectionUtils.search(Configuration.getConfig().getTypes(), tmpType);
                    if (tmpType != null) {
                        // 解析依赖包
                        dependencies.add(tmpType.getPkg() + ".*");
                        // 将type.impls加入到返回的map中
                        TypeCfg tmpType2 = CollectionUtils.search(tableCfg.getTypes(), tmpType);
                        tmpType = union(tmpType, tmpType2);
                        if (isNotEmpty(tmpType.getImpls())) {
                            for (ImplementCfg implementCfg : tmpType.getImpls()) {
                                result.put(implementCfg.getName(), implementCfg.getColumns());
                            }
                        }
                    } else {
                        dependencies.add(dep);
                    }
                }
                result.put("dependencies", dependencies);
                return result;
            }
        } catch (CloneNotSupportedException e) {
            throw new GeneratorException(e);
        }
        return null;
    }

}
