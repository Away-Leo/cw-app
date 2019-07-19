package com.cw.core.common.base;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: BaseDomainService.java
 * @Description: 基础应用服务
 * @Author: Away
 * @Date: 2018/4/11 10:04
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Transactional
public abstract class BaseAppService<BDS extends BaseDomainService> {

    @Autowired
    public BDS BDS;

    /**
     * @Author: Away
     * @Title: findById
     * @Description: 按照ID查找数据
     * @Param: id 待查找ID
     * @Return: BE
     * @Date: 2018/4/11 16:17
     * @Version: 2018/4/11 16:17
     */
    public <BD extends BaseDto> BD findById(Long id, Class<BD> tclass) throws Exception{
        return (BD) BDS.findById(id,tclass);
    }

    /**
     * @Author: Away
     * @Title: saveData
     * @Description: 公共保存方法
     * @Param: toSaveData 待保存dto数据
     * @Param: tClass 待保存数据对应的entity
     * @Return: BD 保存后的dto数据
     * @Date: 2018/4/11 15:25
     * @Version: 2018/4/11 15:25
     */
    public <BD extends BaseDto,BE extends BaseEntity> BD saveData(BD toSaveData, Class<BE> tClass)throws Exception{
        return (BD) BDS.saveData(toSaveData,tClass);
    }

    /**
     * @Author: Away
     * @Title: updateData
     * @Description: 更新数据
     * @Param: toUpdateData 待更新数据
     * @Param: tClass dto对应域对象class
     * @Return: BD
     * @Date: 2018/4/11 16:11
     * @Version: 2018/4/11 16:11
     */
    public <BD extends BaseDto,BE extends BaseEntity> BD updateData(BD toUpdateData, Class<BE> tClass)throws Exception{
        return (BD) this.BDS.updateData(toUpdateData,tClass);
    }

    /**
     * @Author: Away
     * @Title: saveOrUpdateData
     * @Description: 更细或保存数据
     * @Param: data 待操作数据
     * @Param: tClass dto对应域对象
     * @Return: BD
     * @Date: 2018/4/11 16:44
     * @Version: 2018/4/11 16:44
     */
    public <BD extends BaseDto,BE extends BaseEntity> BD saveOrUpdateData(BD data, Class<BE> tClass) throws Exception{
        return (BD) this.BDS.saveOrUpdateData(data,tClass);
    }


    /**
     * @Method:  findAllList
     * @Author: Away
     * @Version: v1.0
     * @See: 查询所有集合
     * @Param: tClass
     * @Return: java.util.List<BD>
     * @Date: 2018/5/31 15:41
     */
    public <BD extends BaseDto> List<BD> findAllList(Class<BD> tClass){
        return this.BDS.findAllList(tClass);
    }

    /**
     * @Method:  physicalDelete
     * @Author: Away
     * @Version: v1.0
     * @See: 物理删除
     * @Param: id
     * @Return: void
     * @Date: 2018/6/1 10:45
     */
    public void physicalDelete(Long id) {
        this.BDS.physicalDelete(id);
    }

    /**
     * @Method:  logicalDelete
     * @Author: Away
     * @Version: v1.0
     * @See: 逻辑删除
     * @Param: id
     * @Return: void
     * @Date: 2018/6/3 15:15
     */
    public void logicalDelete(Long id){
        this.BDS.logicalDelete(id);
    }

    /**
     * @Method:  batchSave
     * @Author: Away
     * @Version: v1.0
     * @See: 批量保存
     * @Param: sources
     * @Param: tClass
     * @Return: java.util.List<BD>
     * @Date: 2018/6/3 15:22
     */
    public <BD extends BaseDto,BE extends BaseEntity> List<BD> batchSave(List<BD> sources,Class<BD> tClass,Class<BE> beClass) throws Exception {
        return BDS.batchSave(BDS.toEntityList(sources,beClass),tClass);
    }

    /**
     * @Method:  batchDelete
     * @Author: Away
     * @Version: v1.0
     * @See: 批量删除
     * @Param: ids
     * @Return: void
     * @Date: 2018/6/3 16:33
     */
    public void batchDelete(String ids) throws Exception{
        this.BDS.batchDeleteByIds(ids);
    }

    /**
     * @Method:  deleteAll
     * @Author: Away
     * @Version: v1.0
     * @See: 删除所有
     * @Param:
     * @Return: void
     * @Date: 2018/7/5 18:27
     */
    public void deleteAll() throws Exception{
        this.BDS.deleteAll();
    }

}
