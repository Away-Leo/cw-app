package com.cw.core.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* @Title: BaseRepository.java
* @Description: 基础数据操作库
* @author Away
* @Date: 2018/1/9 15:54
* @Copyright: 重庆平迅数据服务有限公司
* @version V1.0
*/
@NoRepositoryBean
@Transactional(readOnly=true)
public interface BaseRepository<T extends BaseEntity,ID extends Serializable> extends JpaRepository<T,ID> {


    /**
     * @Author: Away
     * @Title: findByHqlPage
     * @Description: 分页查询
     * @Param: pageable 分页信息
     * @Param: hql 查询hql
     * @Param: condition 查询条件
     * @Return: org.springframework.data.domain.Page<T>
     * @Date: 2018/4/11 14:03
     * @Version: 2018/4/11 14:03
     */
    public Page<T> findByHqlPage(Pageable pageable, String hql, Map<String, Object> condition);

    /**
     * @Author: Away
     * @Title: findByHql
     * @Description: 按照hql查找
     * @Param: hql hql语句
     * @Param: condition 查询条件
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 14:05
     * @Version: 2018/4/11 14:05
     */
    public List<T> findByHql(String hql, Map<String, Object> condition);

    /**
     * @Author: Away
     * @Title: findBySqlEntityPage
     * @Description: sql分页查询出实体集合
     * @Param: pageable 分页参数
     * @Param: sql sql
     * @Param: condition 查询条件
     * @Param: tClass 需要包装成的实体对象
     * @Return: org.springframework.data.domain.Page<T>
     * @Date: 2018/4/11 14:06
     * @Version: 2018/4/11 14:06
     */
    public Page<T> findBySqlEntityPage(Pageable pageable, String sql, Map<String, Object> condition, Class<T> tClass) throws Exception;

    /**
     * @Author: Away
     * @Title: findBySql
     * @Description: 多条件sql查询出集合
     * @Param: sql sql语句
     * @Param: condition 查询条件
     * @Param: tClass 需要封装成的实体
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 14:07
     * @Version: 2018/4/11 14:07
     */
    public List<T> findBySql(String sql, Map<String, Object> condition, Class<T> tClass) throws Exception;

    /**
     * @Author: Away
     * @Title: countAll
     * @Description: 获取当前查询条件下的总条数
     * @Param: hql hql语句
     * @Param: condition 查询条件
     * @Return: java.lang.Long
     * @Date: 2018/4/11 14:08
     * @Version: 2018/4/11 14:08
     */
    public Long countAll(String hql, Map<String, Object> condition);

    /**
     * @Author: Away
     * @Title: countAllSql
     * @Description: 当前sql查询条件下的总条数
     * @Param: sql sql语句
     * @Param: condition 查询条件
     * @Return: java.lang.Long
     * @Date: 2018/4/11 14:09
     * @Version: 2018/4/11 14:09
     */
    public Long countAllSql(String sql, Map<String, Object> condition);

    /**
     * @Author: Away
     * @Title: saveEntity
     * @Description: 保存域对象
     * @Param: t 待保存对象
     * @Return: T
     * @Date: 2018/4/11 15:07
     * @Version: 2018/4/11 15:07
     */
    public T saveEntity(T t);

    /**
     * @Author: Away
     * @Title: updateEntity
     * @Description: 更新数据
     * @Param: t 待更新数据
     * @Return: T
     * @Date: 2018/4/11 15:08
     * @Version: 2018/4/11 15:08
     */
    public T updateEntity(T t);

    /**
     * @Author: Away
     * @Title: logicDelete
     * @Description: 逻辑删除
     * @Param: t 待删除对象
     * @Return: T
     * @Date: 2018/4/11 15:09
     * @Version: 2018/4/11 15:09
     */
    public T logicDelete(T t);

    /**
     * @Author: Away
     * @Title: logicDelete
     * @Description: 逻辑删除
     * @Param: id 待删除对象ID
     * @Return: T
     * @Date: 2018/4/11 15:09
     * @Version: 2018/4/11 15:09
     */
    public T logicDelete(Long id);

    /**
     * @Author: Away
     * @Title: batchSave
     * @Description: 批量保存
     * @Param: list 待保存数据
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 15:09
     * @Version: 2018/4/11 15:09
     */
    public List<T> batchSave(List<T> list);

    /**
     * @Author: Away
     * @Title: batchUpdate
     * @Description: 批量更新
     * @Param: list 待更新数据
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 15:10
     * @Version: 2018/4/11 15:10
     */
    public List<T> batchUpdate(List<T> list);

    /**
     * @Author: Away
     * @Title: batchLogicDelete
     * @Description: 批量逻辑删除
     * @Param: list 待删除对象
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 15:11
     * @Version: 2018/4/11 15:11
     */
    public List<T> batchLogicDelete(List<T> list);

    /**
     * @Author: Away
     * @Title: batchLogicDelete
     * @Description: 批量物理删除
     * @Param: list 待删除对象
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 15:11
     * @Version: 2018/4/11 15:11
     */

    public List<T> batchDelete(List<T> list);
    /**
     * @Author: Away
     * @Title: batchLogicDelete
     * @Description: 批量物理删除
     * @Param: ids 待删除多个id（用逗号隔开）
     * @Return: java.util.List<T>
     * @Date: 2018/4/11 15:11
     * @Version: 2018/4/11 15:11
     */
    public List<T> batchDelete(String ids);
}
