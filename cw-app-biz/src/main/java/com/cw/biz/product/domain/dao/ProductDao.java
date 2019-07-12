package com.cw.biz.product.domain.dao;

import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.AppDevDto;
import com.cw.biz.product.app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/9/5.
 */
@Repository
public class ProductDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询安卓、苹果、信息流发展数据
     * @param productListDto
     * @return
     */
    public Boolean saveProductSort(ProductListDto productListDto,String appSysType){

        String sql = " update cw_product set " ;
                     if("android".equals(appSysType)) {
                         sql+= " show_order = " + productListDto.getShowOrder() + "," ;
                     }else{
                         sql+=" show_order_jqw = " +productListDto.getShowOrderJqw()+",";
                     }
                    sql+="     show_order_xsd = " +productListDto.getShowOrderXsd()+
//                     "         show_order_jds = " +productListDto.getShowOrderJds()+","+
//                     "         show_order_dsqb = " +productListDto.getShowOrderDsqb()+","+
//                     "         show_order_dkqb = " +productListDto.getShowOrderDkqb()+","+
//                     "         show_order_lyb = " +productListDto.getShowOrderLyb()+","+
//                     "         show_order_pxqb = " +productListDto.getShowOrderPxqb()+","+
//                     "         show_order_lsqd = " +productListDto.getShowOrderLsqd()+","+
//                    "         show_order_syd = " +productListDto.getShowOrderSyd()+","+
//                    "         show_order_srd = " +productListDto.getShowOrderSrd()+","+
//                    "         show_order_jdb = " +productListDto.getShowOrderJdb()+","+
//                    "         show_order_dql = " +productListDto.getShowOrderDql()+","+
//                    "         show_order_dkzg = " +productListDto.getShowOrderDkzg()+","+
//                    "         show_order_jjk = " +productListDto.getShowOrderJjk()+","+
//                    "         show_order_xed = " +productListDto.getShowOrderXed()+","+
//                    "         show_order_jqs = " +productListDto.getShowOrderJqs()+","+
//                    "         show_order_dkh = " +productListDto.getShowOrderDkh()+","+
//                    "         show_order_jqhh = " +productListDto.getShowOrderJqhh()+","+
//                    "         show_order_jdjq = " +productListDto.getShowOrderJdjq()+","+
//                    "         show_order_dkt = " +productListDto.getShowOrderDkt()+","+
//                    "         show_order_kjw = " +productListDto.getShowOrderKjw()+","+
//                    "         show_order_mdb = " +productListDto.getShowOrderMdb()+","+
//                    "         show_order_hdb = " +productListDto.getShowOrderHdb()+","+
//                    "         show_order_dkss = " +productListDto.getShowOrderDkss()+","+
//                    "         show_order_jqf = " +productListDto.getShowOrderJqf()+","+
//                    "         show_order_yjx = " +productListDto.getShowOrderYjx()+
                     "  where id = '"+productListDto.getId()+"' ";

         jdbcTemplate.update(sql);

        return Boolean.TRUE;
    }

    /**
     * 修改产品流量控制配置
     * @param productDto
     * @return
     */
    public Boolean updateProductConfig(ProductDto productDto){

        String sql = " update cw_product set " +
                "         limit_user_top = " +productDto.getLimitUserTop()+","+
                "         is_hidden = " +productDto.getIsHidden()+","+
                "         jump_url = '" +productDto.getJumpUrl()+"'"+
                "  where id = '"+productDto.getId()+"'";
        jdbcTemplate.update(sql);

        return Boolean.TRUE;
    }


    /**
     * 查询产品当天申请人数
     * @param productId
     * @return
     */
    public AppDevDto findProductUserById(Long productId){

        String sql = " select count(distinct user_id) as applyNum \n" +
                "   from cw_log \n" +
                "  where product_id = "+productId+"  and type=2 \n" +
                "    and DATE_FORMAT(apply_date,'%y-%m-%d')=DATE_FORMAT(NOW(),'%y-%m-%d')\n" +
                "    and user_id in (select id from pf_se_user where type='user' and register_date is not null)   \n" +
                "    and channel_no in (select code from cw_channel union all select code from cw_app_market)";

        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto.get(0);
    }

    /**
     * 查询审核渠道
     * @param channelNo
     * @param appName
     * @return
     */
    public Boolean findAuditChannel(String channelNo,String appName){
        String vivoAuditSql = "select * from cw_audit_channel where channel_no='"+channelNo+"' and app_name='"+appName+"'";
        List<AuditChannelDto> auditChannelDtoList = jdbcTemplate.query(vivoAuditSql, new BeanPropertyRowMapper(AuditChannelDto.class));
        return auditChannelDtoList.size()>0?Boolean.TRUE:Boolean.FALSE;

    }

    /**
     * 申请产品查询推荐产品信息
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findProductRecommendByProductId(Long productId,String versionNo,String channelNo){

        String sqls="select a.*,a.is_android as androidFlag,a.is_apple as appleFlag from cw_product a where id="+productId;
        List<ProductDto> productDtoList = jdbcTemplate.query(sqls, new BeanPropertyRowMapper(ProductDto.class));
        if(productDtoList.size()==0){
            CwException.throwIt("产品不存在");
        }

        //判断苹果审核条件展示推荐数据
        String auditSql = "select data_version as dataVersion,is_audit as isAudit,app_name as appName" +
                "    from cw_audit_version where data_version='"+versionNo+"'";
        List<ProductAuditVersionDto> productVersionDtoList = jdbcTemplate.query(auditSql, new BeanPropertyRowMapper(ProductAuditVersionDto.class));
        String sql = "";
        if(productVersionDtoList.size()>0) {
            //查询需要审核的渠道
            Boolean isAudit = findAuditChannel(channelNo,versionNo);
            if (!channelNo.toUpperCase().contains("IOS".toUpperCase()) && isAudit ) {
                String vivoAuditSql = "select data_version as dataVersion,is_audit as isAudit,app_name as appName" +
                        "    from cw_audit_version where data_version='"+versionNo+"'";
                productVersionDtoList = jdbcTemplate.query(vivoAuditSql, new BeanPropertyRowMapper(ProductAuditVersionDto.class));
                sql+=createSql(productVersionDtoList,productDtoList,productId);
            }else if(channelNo.toUpperCase().contains("IOS".toUpperCase())){//IOS审核
                sql+=createSql(productVersionDtoList,productDtoList,productId);
            }else{//安卓非VIVO渠道审核sql
                sql+=createAuditSql(productDtoList,productId);
            }
        }else{
            sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,6850000 as loanAmount " +
                    "  from cw_product a " +
                    " where is_valid=1 and channel='重庆平讯数据服务有限公司'";
            sql += "    and is_android = 0 ";
            sql += "    and is_apple = 0 and belong_app like '%MBD%'";
            sql += "  limit 6 ";
        }
        //后台配置处理
        if("backend".equals(channelNo)){
            sql = " select a.recommend_img as recommendImg,a.name,a.img,a.id,b.loan_amount as loanAmount,b.raw_add_time \n" +
                    "   from cw_product a \n" +
                    " INNER JOIN ( " +
                    "  select recommend_product_id,loan_amount,raw_add_time from cw_product_recommend where product_id=" + productId + ") b " +
                    "      on a.id = b.recommend_product_id " +
                    " where is_valid=1 and channel!='重庆平讯数据服务有限公司' " +
                    "   and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top) " +
                    " ";
        }

        List<ProductRecommendListDto> productListDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductRecommendListDto.class));
        return productListDtos;
    }

    /***
     * 构造审核数据sql
     * @param productDtoList
     * @param productId
     * @return
     */
    private String createAuditSql(List<ProductDto> productDtoList,Long productId){
        String sql="";
        sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,b.loan_amount as loanAmount,b.raw_add_time \n" +
                "   from cw_product a \n" +
                " INNER JOIN ( " +
                "  select recommend_product_id,loan_amount,raw_add_time from cw_product_recommend where product_id=" + productId + ") b " +
                "      on a.id = b.recommend_product_id " +
                " where is_valid=1 and channel!='重庆平讯数据服务有限公司' " +
                "   and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top) " +
                " union all ";
        sql += " (select a.recommend_img as recommendImg,a.name,a.img,a.id,a.loan_amount as loanAmount,raw_add_time " +
                "  from cw_product a where is_valid=1 and channel!='重庆平讯数据服务有限公司' and id !="+productId;
        boolean flag=false;
        if (productDtoList.get(0).getAndroidFlag()&&productDtoList.get(0).getAppleFlag()) {
            sql += "  and is_android = 1 and is_apple=1 " +
                   "  and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                    " order by show_order asc ";
            flag=true;
        }
        if (productDtoList.get(0).getAppleFlag()&&!productDtoList.get(0).getAndroidFlag()&&!flag) {
            sql += " and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                   " and is_apple = 1 and is_android=0 order by show_order_jqw asc ";
            flag=true;
        }
        if (!productDtoList.get(0).getAppleFlag()&&productDtoList.get(0).getAndroidFlag()&&!flag) {
            sql += " and ((limit_user_top <= today_apply_user  and (is_hidden=2 or is_hidden=0)) or today_apply_user<=limit_user_top)" +
                    " and is_apple = 0 and is_android=1 order by show_order asc ";
        }

        sql += "  limit 6 ) " ;
        return sql;
    }
    /**
     * 构造审核数据
     * @param productVersionDtoList
     * @param productDtoList
     * @param productId
     * @return
     */
    private String createSql(List<ProductAuditVersionDto> productVersionDtoList,List<ProductDto> productDtoList,Long productId){
        String sql="";
        if (productVersionDtoList.get(0).getIsAudit()) {
            sql += createAuditSql(productDtoList,productId);
        } else {
            sql += " select a.recommend_img as recommendImg,a.name,a.img,a.id,a.loan_amount as loanAmount " +
                    " from cw_product a where is_valid=1 and channel='重庆平讯数据服务有限公司'";
            sql += "  and is_android = 0 ";
            sql += "  and is_apple = 0 and belong_app like '%" + productVersionDtoList.get(0).getAppName() + "%'";
            sql += "  order by show_order asc  limit 6 ";
        }
        return sql;
    }
    /**
     * 查询未被推荐的产品
     * @param productId
     * @param productName
     * @return
     */
    public List<ProductRecommendListDto> findNoRecommendByProductId(Long productId,String productName){
        String sql = "select a.* \n" +
                "       from cw_product a \n" +
                "      where id not in ( " +
                "             select recommend_product_id" +
                "               from cw_product_recommend " +
                "              where product_id="+productId+"" +
                "           ) " +
                "     and is_valid=1 and channel!='重庆平讯数据服务有限公司' ";
        if(!StringUtils.isEmpty(productName)){
            sql += " and name like '%"+productName+"%'";
        }

        List<ProductRecommendListDto> productListDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductRecommendListDto.class));
        return productListDtos;
    }

    /**
     * 产品排序列表
     * @param searchDto
     * @return
     */
    public List<ProductListDto> findByProductSortList(ProductSearchDto searchDto){
        StringBuffer sql = new StringBuffer();
        if(searchDto.getSortDesc()==null){
            searchDto.setSortDesc("asc");
        }
        sql.append("select a.id,a.name,a.is_valid as isValid,show_order as showOrder,show_order_jqw as showOrderJqw," +
                "   '"+searchDto.getAppSysType()+"' as appSysType,'"+searchDto.getSortDesc()+"' sortDesc \n" +
                "       from cw_product a \n" +
                "  where channel!='重庆平讯数据服务有限公司'");
        if(!StringUtils.isEmpty(searchDto.getName())){
            sql.append(" and name like '%"+searchDto.getName()+"%'");
        }
        if(!StringUtils.isEmpty(searchDto.getAppSysType())&&"android".equals(searchDto.getAppSysType())){
            sql.append(" and is_android = 1 ");
        }
        if(!StringUtils.isEmpty(searchDto.getAppSysType())&&"ios".equals(searchDto.getAppSysType())){
            sql.append(" and is_apple = 1 ");
        }
        //是否上下架
        if(!StringUtils.isEmpty(searchDto.getIsValid())){
            sql.append(" and is_valid = "+searchDto.getIsValid());
        }

        if(!StringUtils.isEmpty(searchDto.getAppSysType())&&"android".equals(searchDto.getAppSysType())){
            sql.append(" order by show_order  "+searchDto.getSortDesc());
        }
        if(!StringUtils.isEmpty(searchDto.getAppSysType())&&"ios".equals(searchDto.getAppSysType())){
            sql.append(" order by show_order_jqw "+searchDto.getSortDesc());
        }

        List<ProductListDto> productListDtos = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ProductListDto.class));
        return productListDtos;
    }

    /**
     * 设置产品推荐
     */
    public void productSetRecommend(ProductRecommendListDto recommendListDto){
        List<ProductRecommendListDto> list=findProductRecommendByProductId(recommendListDto.getProductId(),"","");
        if("1".equals(recommendListDto.getRecommendType())){//取消推荐
            if((list.size()-recommendListDto.getRecommendProductId().split(",").length)>=6) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("delete from cw_product_recommend where recommend_product_id in (" + recommendListDto.getRecommendProductId() + ")" +
                        "  and product_id= " + recommendListDto.getProductId());
                jdbcTemplate.update(stringBuffer.toString());
            }else{
                CwException.throwIt("推荐产品数量不少于6个");
            }
        }else{//增加推荐
            if((list.size()+recommendListDto.getRecommendProductId().split(",").length)<=20) {
                String recommendProductId=recommendListDto.getRecommendProductId();
                String[] recommendProductIdAry = recommendProductId.split(",");
                for(String productId : recommendProductIdAry) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("insert into cw_product_recommend(merchant_id,raw_creator,product_id,recommend_product_id) " +
                            " values(1,1," + recommendListDto.getProductId() + "," + productId+")");
                    jdbcTemplate.update(stringBuffer.toString());
                }
            }else{
                CwException.throwIt("推荐产品数量不超过20个");
            }
        }

    }

    /**
     * 设置产品推荐功能开关
     */
    public void updateProductRecommendFlag(ProductDto productDto){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("update cw_product set recommend_flag='"+productDto.getRecommendFlag()+"'" +
                    " where id = "+productDto.getId());
            jdbcTemplate.update(stringBuffer.toString());
    }


    /**
     * 修改产品结算标识
     * @param productDto
     * @return
     */
    public Boolean updateProductSettleFlag(ProductDto productDto){
        String sql = " update cw_product set " +
                "         limit_user_top = " +productDto.getLimitUserTop()+","+
                "         is_hidden = " +productDto.getIsHidden()+","+
                "         jump_url = '" +productDto.getJumpUrl()+"'"+
                "  where id = '"+productDto.getId()+"'";
        jdbcTemplate.update(sql);

        return Boolean.TRUE;
    }
}
