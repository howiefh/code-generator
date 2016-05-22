package io.github.howiefh.generator.common.validation;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class RequiredValidator implements IValidator {
    @Override
    public boolean validate(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj.getClass() == String.class) {
            return ((String)obj).trim().length() != 0;
        }
        return true;
    }
}
