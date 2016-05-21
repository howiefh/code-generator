package io.github.howiefh.generator.common.entity;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class BasicEntity extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1575521071086410539L;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return super.isNew();
    }
}
