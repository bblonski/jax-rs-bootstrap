package com.bootstrap.models;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base model object that contains common primary key logic.
 *
 * @author Brian Blonski
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#toString()
    */
    @Override
    public String toString() {

        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#equals(java.lang.Object)
    */
    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        BaseModel that = (BaseModel) obj;

        return 0 != this.getId() && this.getId() == that.getId();
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += 0 == getId() ? 0 : getId() * 31;

        return hashCode;
    }

}
