package org.dmack.statsapp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.dmack.statsapp.domain.DomainObject;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 */
public class GenericRepositoryJPAImpl<T extends DomainObject> extends JpaDaoSupport implements Repository<T>
{

    private Class<T> domainClass;

    public GenericRepositoryJPAImpl(Class<T> domainClass)
    {
        this.domainClass = domainClass;
    }

    @Transactional
    public void delete(T object)
    {
        getJpaTemplate().remove(object);
    }

    @Transactional
    public T load(Serializable id)
    {
        return (T) getJpaTemplate().find(domainClass, id);
    }

    @Transactional
    public T save(T object)
    {
        return getJpaTemplate().merge(object);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return getJpaTemplate().find("select obj from " + domainClass.getName() + " obj");
    }

    public List<T> findAllDistinct()
    {
        Collection<T> result = new LinkedHashSet<T>(findAll());
        return new ArrayList<T>(result);
    }

    public Long countAll()
    {
        return getJpaTemplate().execute(new JpaCallback<Long>()
        {
            public Long doInJpa(EntityManager em) throws PersistenceException
            {
                Query queryObject = em.createQuery("select count(*) from " + domainClass.getName());
                getJpaTemplate().prepareQuery(queryObject);

                return (Long) queryObject.getSingleResult();
            }
        });
    }

    public T findByPK(Long id)
    {
        T entity = getJpaTemplate().find(domainClass, id);

        if (entity == null)
        {
            String msg = "Uh oh, '" + domainClass.getName() + "' object with id '" + id + "' not found...";
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    public boolean exists(Long id)
    {
        T entity = getJpaTemplate().find(domainClass, id);
        return entity != null;
    }

    public void flush()
    {
        getJpaTemplate().flush();
    }

    @SuppressWarnings("unchecked")
    public List<T> executeJpql(String query)
    {
        return getJpaTemplate().find(query);
    }

    @SuppressWarnings("unchecked")
    public List<T> executeJpqlByPositionalParams(final String queryString, final Object... values)
    {
        return getJpaTemplate().find(queryString, values);
    }

    @SuppressWarnings("unchecked")
    public List<T> executeJpqlByNamedParams(final String queryString, final Map<String, ?> params)
    {
        return getJpaTemplate().findByNamedParams(queryString, params);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName)
    {
        return getJpaTemplate().findByNamedQuery(queryName);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryByPositionalParams(final String queryName, final Object... values)
    {
        return getJpaTemplate().findByNamedQuery(queryName, values);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?> params)
    {
        return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?> params, final int maxResults)
    {
        return (List<T>) getJpaTemplate().executeFind(new JpaCallback<List<T>>()
        {
            public List<T> doInJpa(EntityManager em) throws PersistenceException
            {
                Query queryObject = em.createNamedQuery(queryName);
                queryObject.setMaxResults(maxResults);
                getJpaTemplate().prepareQuery(queryObject);

                if (params != null)
                {
                    for (Map.Entry<String, ?> entry : params.entrySet())
                    {
                        queryObject.setParameter(entry.getKey(), entry.getValue());
                    }
                }
                return (List<T>) queryObject.getResultList();
            }
        });
    }

    public T findInstanceByNamedQuery(final String queryName)
    {
        List<T> results = findByNamedQuery(queryName);
        return getUniqueResult(results, queryName);
    }

    public T findInstanceByNamedQueryByPositionalParams(final String queryName, final Object... args)
    {
        List<T> results = findByNamedQueryByPositionalParams(queryName, args);
        return (T) getUniqueResult(results, queryName);
    }

    public T findInstanceByNamedQueryAndNamedParams(final String queryName, final Map<String, ?> params)
    {
        List<T> results = findByNamedQueryAndNamedParams(queryName, params);
        return (T) getUniqueResult(results, queryName);
    }

    private T getUniqueResult(List<T> results, String queryName)
    {
        if (results.size() == 1)
        {
            return (T) results.get(0);
        }
        else if (results.size() == 0)
        {
            throw new NoResultException("No object found for the query: " + queryName);
        }
        else
        {
            throw new NonUniqueResultException("Multiple objects found for the query: " + queryName);
        }
    }

    public long count()
    {
        return countAll();
    }

    public T find(Long id)
    {
        return findByPK(id);
    }

    public List<T> findAll(int firstResult, int maxResults)
    {
        return findAll();
    }

    public Class<T> getRepositoryContentsClass()
    {
        return domainClass;
    }

    public void persist(T domainObject)
    {
        save(domainObject);
    }

    public void remove(T domainObject)
    {
        delete(domainObject);
    }
}