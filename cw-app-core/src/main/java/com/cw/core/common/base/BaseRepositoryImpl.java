package com.cw.core.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by konghao on 2016/12/7.
 */
public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T,ID>
        implements BaseRepository<T,ID> {

	static Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);
	private final EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByHql(String hql, Map<String, Object> condition) {
		// 获得query对象
		Query q = entityManager.createQuery(hql);
		// 将查询条件注入HQL语句中
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		// 执行查询
		List<T> sourceData = (List<T>) q.getResultList();
		return sourceData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findByHqlPage(Pageable pageable, String hql, Map<String, Object> condition) {
		if (pageable == null) {
			throw new IllegalArgumentException("pageable 不能为空!");
		}
		// 创建query
		Query q = entityManager.createQuery(hql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		//分页参数
		Integer pageSize = pageable.getPageSize();
		Integer pageNo = pageable.getPageNumber();
		q.setFirstResult(pageSize * pageNo);
		q.setMaxResults(pageable.getPageSize());
		// 申明分页数据
		Page<T> pager = new PageImpl<T>(q.getResultList(),pageable,this.countAll(hql, condition));
		return pager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<T> findBySqlEntityPage(Pageable pageable, String sql, Map<String, Object> condition, Class<T> tClass)
			throws Exception {
		if (pageable == null) {
			throw new IllegalArgumentException("pageable 不能为空!");
		}
		Query query=entityManager.createNativeQuery(sql,tClass);
//		Session session = entityManager.unwrap(Session.class);
//		SQLQuery query = session.createSQLQuery(sql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			query.setParameter(key.toString(), condition.get(key));
		}
		// 设置分页条件
		Integer pageSize = pageable.getPageSize();
		Integer pageNo = pageable.getPageNumber();
		query.setFirstResult(pageSize * pageNo);
		query.setMaxResults(pageable.getPageSize());
		// 申明分页数据
		Page<T> pager = new PageImpl<T>(query.getResultList(),pageable,this.countAllSql(sql, condition));
		return pager;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findBySql(String sql, Map<String, Object> condition, Class<T> tClass) throws Exception {
		Query query = entityManager.createNativeQuery(sql,tClass);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			query.setParameter(key.toString(), condition.get(key));
		}
		return query.getResultList();
	}

	@Override
	public Long countAll(String hql, Map<String, Object> condition) {
		if (hql == null) {
			return 0l;
		}
		String tmpHql = hql.toLowerCase();
		hql = "select count(*) " + hql.substring(tmpHql.indexOf("from"));
		logger.debug("count(*) hql ---->" + hql);
		// 创建query
		Query q = entityManager.createQuery(hql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		Long result = (Long) q.getResultList().get(0);
		return result;
	}

	@Override
	public Long countAllSql(String sql, Map<String, Object> condition) {
		if (sql == null) {
			return 0l;
		}
		String tmpHql = sql.toLowerCase();
		sql = "select count(*) " + sql.substring(tmpHql.indexOf("from"));
		logger.debug("count(*) sql ---->" + sql);
		// 创建query
		Query q = entityManager.createNativeQuery(sql);
		// 设置查询条件
		for (Serializable key : condition.keySet()) {
			q.setParameter(key.toString(), condition.get(key));
		}
		String resultStr = q.getResultList().get(0).toString();
		return Long.valueOf(resultStr);
	}

	@Override
	public T saveEntity(T t) {
	    t.setRawAddTime(new Date());
		return this.saveAndFlush(t);
	}

	@Override
	public T updateEntity(T t) {
		t.setRawUpdateTime(new Date());
	    return this.saveAndFlush(t);
	}

	@Override
	public T logicDelete(T t) {
		t.setDeleted(true);
	    return this.entityManager.merge(t);
	}

	@Override
	public T logicDelete(Long id) {
		T t=findOne((ID) id);
		return this.logicDelete(t);
	}

	@Override
	@Transactional
	public List<T> batchSave(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			entityManager.persist(list.get(i));
			if (i % 1000 == 0 || i==(list.size()-1)) { // 每1000条数据执行一次，或者最后不足1000条时执行
				entityManager.flush();
				entityManager.clear();
			}
		}
		return list;
	}

	@Override
	@Transactional
	public List<T> batchUpdate(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			entityManager.merge(list.get(i));
            if (i % 1000 == 0 || i==(list.size()-1)) { // 每1000条数据执行一次，或者最后不足1000条时执行
                entityManager.flush();
                entityManager.clear();
            }
		}
		return list;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<T> batchLogicDelete(List<T> list) {
		for (int j = 0; j < list.size(); j++) {
			entityManager.merge(list.get(j));
            if (j % 100 == 0 || j==(list.size()-1)) { // 每100条数据执行一次，或者最后不足1000条时执行
                entityManager.flush();
                entityManager.clear();
            }
		}
		return list;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<T> batchDelete(List<T> list) {
		for (int j = 0; j < list.size(); j++) {
			entityManager.remove(list.get(j));
			if (j % 100 == 0 || j==(list.size()-1)) { // 每100条数据执行一次，或者最后不足1000条时执行
				entityManager.flush();
				entityManager.clear();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<T> batchDelete(String ids) {
		String[] idsArray=ids.split(",");
		for(int j = 0; j < idsArray.length; j++){
			entityManager.remove(this.findOne((ID) Long.valueOf(idsArray[j])));
			if (j % 100 == 0 || j==(idsArray.length-1)) { // 每100条数据执行一次，或者最后不足1000条时执行
				entityManager.flush();
				entityManager.clear();
			}
		}
		return null;
	}

	/**
	 * 去掉下划线
	 *
	 * @param attrName
	 * @return
	 */
	private String removeUnderLine(String attrName) {
		// 去掉数据库字段的下划线
		if (attrName.contains("_")) {
			String[] names = attrName.split("_");
			String firstPart = names[0];
			String otherPart = "";
			for (int i = 1; i < names.length; i++) {
				String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());
				otherPart += word;
			}
			attrName = firstPart + otherPart;
		}
		return attrName;
	}

//	/**
//	 * 把实体对象list转换为目标对象List
//	 */
//	public static <T, S extends BaseEntity> List<T> map(List<S> list, Class<T> clazz) {
//		if (list == null || list.isEmpty()) {
//			return  Lists.newArrayList();
//		}
//		List<T> ts = new ArrayList<>(list.size());
//		for (S s : list) {
//			ts.add(s.to(clazz));
//		}
//		return ts;
//	}
//
//	/**
//	 * 转换Page对象中的集合类型S为T
//	 */
//	public static <S extends BaseEntity, T> Page<T> map(Page<S> page, Class<T> clazz) {
//		return Pages.map(page, clazz);
//	}

}
