# Code Generator

## 模板变量

变量               | 类型                                                   | 说明
---                | ---                                                    | ---
 statics           | freemarker.ext.beans.StaticModels                      | 可以通过statics调用静态方法，用法：`<#assign util = statics["io.github.howiefh.generator.common.util.StringUtils"] ><#assign property = util.toCapitalizeCamelCase(c) >`
 date              | String                                                 | 当前日期，格式：`yyyy/MM/dd`
 serialVersion     | String                                                 | 随机生成的一个Long值，可用于设置serialVersionUID
 config            | io.github.howiefh.generator.common.config.Config       | json配置文件对应的根对象。
 author            | String                                                 | 配置文件中的作者
 version           | String                                                 | 配置文件中的版本
 since             | String                                                 | 配置文件中的since
 configAttrs       | `Map<String, Object>`                                  | 配置文件对应的config实例的扩展字段
 type.name + 'Pkg' | String                                                 | 配置文件中类型的目标文件的类包
 tableCfg          | io.github.howiefh.generator.common.config.TableCfg     | 配置文件中的当前表配置
 typeCfg           | io.github.howiefh.generator.common.config.TypeCfg      | 配置文件中的当前类型配置
 implementCfg      | io.github.howiefh.generator.common.config.ImplementCfg | 配置文件中的当前类型的实现配置
 tableAttrs        | `Map<String, Object>`                                  | 配置文件中当前表配置的扩展字段
 typeAttrs         | `Map<String, Object>`                                  | 配置文件中当前类型配置的扩展字段
 implementAttrs    | `Map<String, Object>`                                  | 配置文件中当前类型实现配置的扩展字段
 package           | String                                                 | 配置文件中当前类型的类包
 target            | String                                                 | 配置文件中当前类型的目标文件存放目录
 dependencies      | `Set<String>`                                          | 当前类型的依赖包集合
 impls             | `Map<String, Set<String>>`                             | 当前类型的实现名和列名集合映射
 implColumns       | `Set<String>`                                          | 当前类型实现的列名集合
 implClassName     | String                                                 | 当前类型实现的类名
 className         | String                                                 | 当前表对应实体类名，首字母小写
 ClassName         | String                                                 | 当前表对应实体类名，首字母大写
 comments          | String                                                 | 当前表功能，表的注释
 basePackage       | String                                                 | 基础包名
 baseTarget        | String                                                 | 基础输出目录
 table             | io.github.howiefh.generator.entity.Table               | 当前表实体
