package org.dmack.statsapp.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dmack.statsapp.domain.DomainObject;


/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 * 
 */
public interface Repository<T extends DomainObject>
{
    public void delete(T o);

    public T load(Serializable id);

    public T save(T o);

    public List<T> findAll();

    public List<T> findAllDistinct();

    public Long countAll();

    public T findByPK(Long id);

    public boolean exists(Long id);

    public void flush();

    public List<T> executeJpql(String query);

    public List<T> executeJpqlByPositionalParams(final String queryString, final Object... values);

    public List<T> executeJpqlByNamedParams(final String queryString, final Map<String, ?> params);

    public List<T> findByNamedQuery(String queryName);

    public List<T> findByNamedQueryByPositionalParams(final String queryName,
            final Object... values);

    public List<T> findByNamedQueryAndNamedParams(final String queryName,
            final Map<String, ?> params);

    public List<T> findByNamedQueryAndNamedParams(final String queryName,
            final Map<String, ?> params, final int maxResults);

    public T findInstanceByNamedQuery(final String queryName);

    public T findInstanceByNamedQueryByPositionalParams(final String queryName,
            final Object... args);

    public T findInstanceByNamedQueryAndNamedParams(final String queryName,
            final Map<String, ?> params);
}
