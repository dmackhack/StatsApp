package org.dmack.statsapp.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

//import org.hibernate.proxy.HibernateProxyHelper;

@MappedSuperclass
public abstract class DomainObject
{

    protected Long id;
    protected Integer version;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Column(name = "ID")
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion()
    {
        return this.version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    @Transient
    public boolean isNew()
    {
        return (getId() == null || getId().equals(0)) ? true : false;
    }

    @Override
    @Transient
    public boolean equals(Object object)
    {
        if (object != null && object instanceof DomainObject)
        {
            if (id != null)
            {
                return id.equals(((DomainObject) object).getId())
                        && this.getClass().equals(object.getClass());
                                //HibernateProxyHelper.getClassWithoutInitializingProxy(object));
            }
            else
            {
                return super.equals(object);
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Instance of:" + this.getClass().getSimpleName() + " Id:" + getId();
    }

//    @Transient
//    public DomainObjectHolder getUnproxiedHolder()
//    {
//        return new DomainObjectHolder();
//    }
//
//    public class DomainObjectHolder
//    {
//        public DomainObject getUnproxied()
//        {
//            return DomainObject.this;
//        }
//    }

}
