package io.github.howiefh.generator.common.entity;

import java.io.Serializable;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = -1673249709005412262L;

    protected ID id;

    public ID getId() {
        return id;
    }

    /**
     * Set the id of the entity
     *
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }

    public boolean isNew() {
        return null == getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Entity " + getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbstractEntity<?>)) {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;

        result += null == getId() ? 0 : getId().hashCode() * 31;

        return result;
    }
}
