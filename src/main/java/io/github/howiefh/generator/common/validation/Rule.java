package io.github.howiefh.generator.common.validation;

/**
 * @author fenghao on 2016/5/22
 * @version 1.0
 * @since 1.0
 */
public enum Rule {
    REQUIRED("required", new RequiredValidator());

    private String name;
    private IValidator validator;

    Rule(String name, IValidator validator){
        this.name = name;
        this.validator = validator;
    }

    public Rule getRule(String name){
        if(name == null){
            return null;
        }
        for(Rule r : Rule.values()) {
            if (r.name.equals(name)) return r;
        }
        throw new IllegalArgumentException("No enum name '" + name + "'. " + Rule.class);
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return validator
     */
    public IValidator getValidator() {
        return validator;
    }

    /**
     * @param validator
     */
    public void setValidator(IValidator validator) {
        this.validator = validator;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
