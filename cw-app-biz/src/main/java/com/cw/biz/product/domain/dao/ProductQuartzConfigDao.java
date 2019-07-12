package com.cw.biz.product.domain.dao;

import com.cw.biz.home.app.dto.AppDevDto;
import com.cw.biz.product.app.dto.ProductDto;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductQuartzConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
@Repository
public class ProductQuartzConfigDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询配置任务信息
     * @return
     */
    public List<ProductQuartzConfigDto> findAll(){
        String sql = " select * from cw_product_quartz_config where online_date >= now() or offline_date >= now() ";
        List<ProductQuartzConfigDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductQuartzConfigDto.class));
        return homeDto;
    }

    /**
     * 修改产品上下架状态
     * @param isValid
     * @return
     */
    public void updateProductConfig(Integer isValid,Long id){

        String sql = " update cw_product set is_valid='"+isValid+"'  where id = "+id;
        jdbcTemplate.update(sql);
    }

}
