package com.rootls.base.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-5
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class IdEntity /*extends AbstractPersistable<Integer>*/
        implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id= id;
    }
}
