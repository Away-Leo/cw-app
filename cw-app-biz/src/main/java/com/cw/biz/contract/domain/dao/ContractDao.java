package com.cw.biz.contract.domain.dao;

import com.cw.biz.contract.app.dto.ContractDto;
import com.cw.biz.home.app.dto.ReportSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同服务
 */
@Repository
public class ContractDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有合同
     * @param searchDto
     * @return
     */
    public List<ContractDto> findAll(ReportSearchDto searchDto){
        String sql=" select a.account_name as accountName," +
                "      a.invoice_link_address as invoiceLinkAddress," +
                "      invoice_link_phone as invoiceLinkPhone," +
                "      a.id as productId," +
                "      a.name as productName," +
                "      b.express," +
                "      b.post_no as postNo," +
                "      date_format(b.post_date,'%Y-%m-%d') as postDate \n" +
                "   from cw_product a \n" +
                " left join cw_contract b on a.id = b.product_id " +
                " where 1=1 and a.channel!='重庆平讯数据服务有限公司' ";
        if(searchDto.getProductName()!=null && !"".equals(searchDto.getProductName())){
            sql+=" and a.name like '%"+searchDto.getProductName()+"%'";
        }
        List<ContractDto> contractList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ContractDto.class));
        return contractList;
    }
}
