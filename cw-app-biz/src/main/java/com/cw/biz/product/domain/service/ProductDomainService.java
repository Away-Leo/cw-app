package com.cw.biz.product.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.apply.app.dto.ApplyDto;
import com.cw.biz.apply.app.service.ApplyAppService;
import com.cw.biz.banner.app.dto.BannerDto;
import com.cw.biz.banner.app.service.BannerAppService;
import com.cw.biz.discount.domain.service.DiscountSettingDomainService;
import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.app.service.LogAppService;
import com.cw.biz.message.app.dto.MessageDto;
import com.cw.biz.message.app.service.MessageAppService;
import com.cw.biz.parameter.app.dto.SpinnerParameterDto;
import com.cw.biz.parameter.app.service.ParameterAppService;
import com.cw.biz.parameter.app.service.SpinnerParameterAppService;
import com.cw.biz.product.app.ChannelEnum;
import com.cw.biz.product.app.DateCycleEnum;
import com.cw.biz.product.app.dto.*;
import com.cw.biz.product.domain.dao.ProductDao;
import com.cw.biz.product.domain.dao.ProductQuartzConfigDao;
import com.cw.biz.product.domain.entity.Product;
import com.cw.biz.product.domain.entity.ProductAuditVersion;
import com.cw.biz.product.domain.entity.ProductStore;
import com.cw.biz.product.domain.entity.ProductVersion;
import com.cw.biz.product.domain.repository.ProductAuditRepository;
import com.cw.biz.product.domain.repository.ProductRepository;
import com.cw.biz.product.domain.repository.ProductStoreRepository;
import com.cw.biz.product.domain.repository.ProductVersionRepository;
import com.cw.biz.push.app.dto.PushMessageDto;
import com.cw.biz.push.app.service.PushMessageAppService;
import com.cw.core.common.util.AppUtils;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.Utils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ProductDomainService {
    public static Logger logger = LoggerFactory.getLogger(ProductDomainService.class);

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductVersionRepository productVersionRepository;
    @Autowired
    private ProductAuditRepository productAuditRepository;
    @Autowired
    private LogAppService logAppService;
    @Autowired
    private BannerAppService bannerAppService;

    @Autowired
    private ProductStoreRepository storeRepository;

    @Autowired
    private SpinnerParameterAppService spinnerParameterAppService;

    @Autowired
    private ApplyAppService applyAppService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductQuartzConfigDao productQuartzConfigDao;
    @Autowired
    private PushMessageAppService pushMessageAppService;

    @Autowired
    private DiscountSettingDomainService discountSettingDomainService;

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public Product create(ProductDto productDto){
        Product product = new Product();
        product.from(productDto);
        product.setSaveDate(new Date());
        product.setOperateNo(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(product);
    }

    /**
     * 修改产品
     * @param productDto
     * @return
     */
    public Product update(ProductDto productDto){
        Product product = repository.findOne(productDto.getId());
        if(product == null){
            CwException.throwIt("产品不存在");
        }
        Boolean isValid = product.getIsValid();
        product.from(productDto);
        if(isValid){
            product.setIsValid(Boolean.TRUE);
        }
        repository.save(product);
        //更新产品数据
        updateDataVersion();

        return product;
    }

    /**
     * 更新产品版本数据
     */
    private void updateDataVersion(){
        List<ProductVersion>  productVersionList = productVersionRepository.findAll();
        if(productVersionList.size() == 0 ){
            ProductVersion productVersion1 = new ProductVersion();
            productVersion1.setDataVersion(1);
            productVersionRepository.save(productVersion1);
        }else {
            ProductVersion productVersion = productVersionRepository.findOne(productVersionList.get(0).getId());
            productVersion.setDataVersion(productVersion.getDataVersion() + 1);
        }
    }
    /**
     * 产品停用、启用
     * @param productDto
     * @return
     */
    public Product enable(ProductVersionDto productDto){
        Product product = repository.findOne(productDto.getId());
        if(product == null){
            CwException.throwIt("产品不存在");
        }
        if(product.getIsValid()) {
            product.setIsValid(Boolean.FALSE);
            product.setOnlineDate(new Date());//下架时间
        }else{
            product.setIsValid(Boolean.TRUE);
            if(product.getOnlineDate()==null) {
                product.setOnlineDate(new Date());//上架时间
            }
        }
        return product;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Product findById(Long id){
        Product product = repository.findOne(id);
        if(product==null) {
            CwException.throwIt("id不能为空");
        }
        return product;
    }

    /**
     * 查询所有产品列表
     * @return
     */
    public Page<Product> findAll(ProductSearchDto searchDto) {
        searchDto.setSizePerPage(1000);
        String[] fields = {"showOrder"};
        searchDto.setSortFields(fields);
        searchDto.setSortDirection(Sort.Direction.ASC);
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                // list.add(cb.equal(root.get("isValid"),Boolean.TRUE));
                list.add(cb.notEqual(root.get("channel"),"重庆平讯数据服务有限公司"));
                if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                        !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
//                    list.add(cb.equal(root.get("operateNo"), CPContext.getContext().getSeUserInfo().getId()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return repository.findAll(spec,searchDto.toPage());
    }

    /**
     * 按条件查询产品列表V1.0秒必贷款
     * @param searchDto
     * @return
     */
    public Page<Product> findByCondition(ProductSearchDto searchDto){
        //设置排序
        setProductSort(searchDto);
        Specification<Product> supplierSpecification = (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //后台查询所有产品，app查询生效的产品
            if(searchDto.getSysType()==0) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            }
            //查询上架状态
            if(searchDto.getIsValid()!=null){
                predicates.add(cb.equal(root.get("isValid"), searchDto.getIsValid()));
            }
            //产品类型：新口子，全部
            if(searchDto.getProductType()!=null&&!"all".equals(searchDto.getProductType())){
                predicates.add(cb.equal(root.get("productType"), searchDto.getProductType()));
            }

            if(searchDto.getProductSubType()!=null&&!"all".equals(searchDto.getProductSubType())){
                predicates.add(cb.equal(root.get("productSubType"), searchDto.getProductSubType()));
            }

            //指数类型 根据指数类型排序
            if(searchDto.getZsType()!=null&&!"all".equals(searchDto.getZsType())){
                // predicates.add(cb.equal(root.get("type"), searchDto.getZsType()));
            }

            //根据不同的应用展示不同的产品
            if(searchDto.getAppName()!=null&&!"".equals(searchDto.getAppName())&&!"all".equals(searchDto.getAppName()))
            {
                predicates.add(cb.like(root.get("belongApp"), "%"+searchDto.getAppName()+"%"));
            }
            if(searchDto.getSysType()==1){
                predicates.add(cb.notEqual(root.get("channel"), "重庆平讯数据服务有限公司"));
//                if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
//                        !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
//                        !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
//                   // predicates.add(cb.equal(root.get("operateNo"), CPContext.getContext().getSeUserInfo().getId()));
//                }
            }
            //产品名称
            if(!Objects.isNull(searchDto.getName())){
                predicates.add(cb.like(root.get("name"),"%"+searchDto.getName()+"%"));
            }
            //合作模式
            if(!Objects.isNull(searchDto.getCooperationModel())&&!"".equals(searchDto.getCooperationModel())){
                predicates.add(cb.equal(root.get("cooperationModel"),searchDto.getCooperationModel()));
            }
            //贷款分类
            if(!Objects.isNull(searchDto.getLoanType())&&!"".equals(searchDto.getLoanType())){
                predicates.add(cb.equal(root.get("sourceType"),searchDto.getLoanType()));
            }
            //流量控制项目
            if(searchDto.getSysType()==0) {
                predicates.add(cb.or(cb.and(cb.lessThanOrEqualTo(root.get("limitUserTop"), root.get("todayApplyUser"))
                        , cb.or(cb.equal(root.get("isHidden"), 2),cb.equal(root.get("isHidden"), 0))),
                       cb.lessThanOrEqualTo(root.get("todayApplyUser"), root.get("limitUserTop"))
                                ));
            }

            //APP构造查询条件 贷款金额 贷款期限，贷款类型
            if(!Objects.isNull(searchDto.getLoanAmount())){
                SpinnerParameterDto spinnerParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanAmount());
                if(spinnerParameterDto!=null){
                    predicates.add(cb.and(cb.greaterThanOrEqualTo(root.get("startAmount"),spinnerParameterDto.getStartValue()),
                            cb.lessThanOrEqualTo(root.get("startAmount"),spinnerParameterDto.getEndValue())));
                }
            }
            if(!Objects.isNull(searchDto.getLoanPeriod())){
                SpinnerParameterDto spinnerParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanPeriod());
                if(spinnerParameterDto!=null){
                    predicates.add(cb.and(cb.greaterThanOrEqualTo(root.get("startPeriod"),spinnerParameterDto.getStartValue()),
                            cb.lessThanOrEqualTo(root.get("startPeriod"),spinnerParameterDto.getEndValue())));
                }
            }

            /**终端判断*/
            checkTerminal(root,cb,predicates,searchDto);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, searchDto.toPage());
    }

    /**
     * 根据app设置排序规则
     * @param searchDto
     */
    private void setProductSort(ProductSearchDto searchDto){
        if(AppUtils.appNameXSD.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderXsd"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameJDS.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderJds"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameJQW.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderJqw"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameDKQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderDkqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameDSQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderDsqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameLSQD.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderLsqd"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNamePXQB.equals(searchDto.getAppName().toUpperCase()))
        {
            String[] fields = {"showOrderPxqb"};
            searchDto.setSortFields(fields);
        }else  if(AppUtils.appNameLYB.equals(searchDto.getAppName().toUpperCase())){
            String[] fields = {"showOrderLyb"};
            searchDto.setSortFields(fields);
        }else{
            String[] fields = {"showOrder"};
            searchDto.setSortFields(fields);
        }
        //综合指数排序
        if(searchDto.getZsType() != null && !"".equalsIgnoreCase(searchDto.getZsType())){
            if("zhzs".equals(searchDto.getZsType())){
                String[] fields = {"showOrderXed"};
                searchDto.setSortFields(fields);
            }else if("cgl".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJqs"};
                searchDto.setSortFields(fields);
            }else if("fksd".equals(searchDto.getZsType())){
                String[] fields = {"showOrderDkh"};
                searchDto.setSortFields(fields);
            }else if("dkll".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJqhh"};
                searchDto.setSortFields(fields);
            }else if("zged".equals(searchDto.getZsType())){
                String[] fields = {"showOrderJdjq"};
                searchDto.setSortFields(fields);
            }else if("rq".equals(searchDto.getZsType())){
                String[] fields = {"showOrderDkt"};
                searchDto.setSortFields(fields);
            }
        }else{
            //IOS单独排序处理
            if(!Objects.isNull(searchDto.getChannelNo())){
                if(searchDto.getChannelNo().toUpperCase().contains("IOS")||
                        searchDto.getChannelNo().toUpperCase().contains("backend".toUpperCase())){
                    String[] fields = {"showOrderJqw"};
                    searchDto.setSortFields(fields);
                }else if(searchDto.getChannelNo().toUpperCase().contains("WECHAT")||
                        searchDto.getChannelNo().toUpperCase().contains("CTGZ93OYC")){//短信按照转化率排序
                    String[] fields = {"showOrderYjx"};
                    searchDto.setSortFields(fields);
                }else{
                    String[] fields = {"showOrder"};
                    searchDto.setSortFields(fields);
                }
            }else{
                String[] fields = {"showOrder"};
                searchDto.setSortFields(fields);
            }
        }


        if(searchDto.getSortColumn()!=null)
        {
            String[] fields = {searchDto.getSortColumn()};
            searchDto.setSortFields(fields);
            if(Sort.Direction.ASC.toString().equals(searchDto.getSortDesc())) {
                searchDto.setSortDirection(Sort.Direction.DESC);
            }else{
                searchDto.setSortDirection(Sort.Direction.ASC);
            }
        }else{
            if(Sort.Direction.ASC.toString().equals(searchDto.getSortDesc())) {
                searchDto.setSortDirection(Sort.Direction.DESC);
            }else{
                searchDto.setSortDirection(Sort.Direction.ASC);
            }
        }
    }

    /**
     * 查询版本号显示数据
     * @param versionNo
     * @return
     */
    public Boolean checkVersionShowData(String versionNo){
        ProductAuditVersion productAuditVersion = productAuditRepository.findByDataVersion(versionNo);
        if(Objects.isNull(productAuditVersion)){
            return Boolean.FALSE;
        }
        return productAuditVersion.getIsAudit();
    }

    /**
     * 按条件查询产品V1.1和借多少接口
     */
    public Page<Product> findByConditionVersion(ProductSearchDto searchDto){
        //查询职业信息
        SpinnerParameterDto spinnerParameterDto = spinnerParameterAppService.findByCode(searchDto.getJob());
        //查询借款金额信息
        SpinnerParameterDto loanParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanAmount()+"");
        //查询借款期限问题
        SpinnerParameterDto loanPeriodParameterDto = spinnerParameterAppService.findByCode(searchDto.getLoanPeriod()+"");

        setProductSort(searchDto);
        Specification<Product> supplierSpecification = (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(!Objects.isNull(searchDto.getName()))
            {
                predicates.add(cb.like(root.get("name"),"%"+searchDto.getName()+"%"));
            }

            /**
             * V1.0查询条件
             */
            /**申请金额*/
            if(!Objects.isNull(searchDto.getAmount())){
                predicates.add(cb.ge(root.get("startAmount"),searchDto.getAmount()));
                predicates.add(cb.ge(root.get("endAmount"),searchDto.getAmount()));
                predicates.add(cb.or(cb.ge(root.get("startAmount"),searchDto.getAmount())));
            }
            /**申请期限*/
            if(DateCycleEnum.DAY.getKey().equals(searchDto.getDateCycle())) {
                if (!Objects.isNull(searchDto.getPeriod())) {
                    predicates.add(cb.ge(root.get("startPeriod"), searchDto.getPeriod()));
                    predicates.add(cb.ge(root.get("endPeriod"), searchDto.getPeriod()));
                    predicates.add(cb.equal(root.get("periodType"),searchDto.getDateCycle()));
                }
            }
            if(DateCycleEnum.MONTH.getKey().equals(searchDto.getDateCycle())){
                if (!Objects.isNull(searchDto.getPeriod())) {
                    predicates.add(cb.ge(root.get("startPeriod"), searchDto.getPeriod()));
                    predicates.add(cb.ge(root.get("endPeriod"), searchDto.getPeriod()));
                    predicates.add(cb.equal(root.get("periodType"),searchDto.getDateCycle()));
                }
            }

            /**
             * V1.1查询条件
             */
            /**贷款期限日*/
            if(DateCycleEnum.DAY.getKey().equals(searchDto.getDateCycle()))
            {
                if(!Objects.isNull(loanPeriodParameterDto)) {
                    predicates.add(cb.ge(root.get("startPeriod"), loanPeriodParameterDto.getStartValue()));
                    predicates.add(cb.ge(root.get("endPeriod"), loanPeriodParameterDto.getEndValue()));
                    predicates.add(cb.equal(root.get("periodType"), loanPeriodParameterDto.getCycle()));
                }
            }
            /**贷款期限月*/
            if(DateCycleEnum.MONTH.getKey().equals(searchDto.getDateCycle())){
                if(!Objects.isNull(loanPeriodParameterDto)) {
                    predicates.add(cb.ge(root.get("startPeriod"), loanPeriodParameterDto.getStartValue()));
                    predicates.add(cb.ge(root.get("endPeriod"), loanPeriodParameterDto.getEndValue()));
                    predicates.add(cb.equal(root.get("periodType"), loanPeriodParameterDto.getCycle()));
                }
            }
            /**贷款金额*/
            if(!Objects.isNull(searchDto.getLoanAmount()))
            {
                if(!Objects.isNull(loanParameterDto)) {
                    predicates.add(cb.ge(root.get("startAmount"), loanParameterDto.getStartValue()));
                    predicates.add(cb.ge(root.get("endAmount"), loanParameterDto.getEndValue()));
                }else{
                    predicates.add(cb.ge(root.get("endAmount"), new BigDecimal(searchDto.getLoanAmount())));
                }
            }
            /**职业*/
            if(!Objects.isNull(searchDto.getJob()))
            {
                if(!Objects.isNull(spinnerParameterDto))
                {
                    predicates.add(cb.like(root.get("applyMaterial"),"%"+spinnerParameterDto.getName()+"%"));
                }
            }
            /**贷款类型*/
            if(!Objects.isNull(searchDto.getLoanType()))
            {
                predicates.add(cb.equal(root.get("productType"),searchDto.getLoanType()));
            }

            /**终端判断*/
            checkTerminal(root,cb,predicates,searchDto);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, searchDto.toPage());
    }


    /**
     * @Author: Away
     * @Title: findByUrl
     * @Description: 按照链接查找产品
     * @Param url
     * @Return: com.cw.biz.product.domain.entity.Product
     * @Date: 2019/7/27 2:52
     * @Version: 1
     */
    public Product findByUrl(String url){
        if(ObjectHelper.isNotEmpty(url)){
            return repository.findByUrl(url);
        }else {
            return null;
        }
    }

    /**
     * 判断来源终端
     * @param root
     * @param cb
     * @param predicates
     * @param searchDto
     */
    private void checkTerminal(Root<Product> root,CriteriaBuilder cb,List<Predicate> predicates,ProductSearchDto searchDto){
        Boolean versionAuditFlag = checkVersionShowData(searchDto.getVersionNo());
        if(!Objects.isNull(searchDto.getChannelNo())) {
            if (searchDto.getChannelNo().toUpperCase().contains(ChannelEnum.IOS.getKey().toUpperCase())) {
                if (!versionAuditFlag) {
                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                } else {
                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.TRUE));
                }
            } else if (searchDto.getChannelNo().toUpperCase().contains(ChannelEnum.WECHAT.getKey().toUpperCase())) {
                predicates.add(cb.equal(root.get("wechatFlag"), Boolean.TRUE));
            } else {
                if (!"backend".toUpperCase().equals(searchDto.getChannelNo().toUpperCase())) {
                    if(!Objects.isNull(searchDto.getChannelNo())) {
                        Boolean isAudit = productDao.findAuditChannel(searchDto.getChannelNo(),searchDto.getVersionNo());
                        if(isAudit) {
                            //读取ios配置
                            if(!Objects.isNull(searchDto.getVersionNo())){
                                isAudit = checkVersionShowData(searchDto.getVersionNo());
                                //vivo审核产品特殊处理
                                if (!isAudit) {
                                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                                    predicates.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                                }else{
                                    predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                                }
                            }else{
                                predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                            }
                        }else{
                            predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                        }
                    }
                }
            }
        }else{
            predicates.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
        }
    }

    /**
     * 借款申请
     * @param logDto
     */
    public void applyLoan(LogDto logDto) throws Exception {
        //Banner链接申请记录
        if(LogEnum.BANNER_LINK.getKey().toString().equals(logDto.getDeviceNumber().toUpperCase())){
            //首页banner的url查询产品ID
            BannerDto bannerDto =bannerAppService.findById(logDto.getProductId());
            logger.info("appName:{},productId:{},userId:{}",logDto.getAppName(),logDto.getProductId(),CPContext.getContext().getSeUserInfo().getUsername());
            if(bannerDto!=null){
                Product productes = repository.findByUrl(bannerDto.getJumpUrl());
                if(productes!=null) {
                    logDto.setProductId(productes.getId());
                    logDto.setType(LogEnum.BANNER_LINK);
                }else{
                    Product product = repository.findOne(logDto.getProductId());
                    if(product!=null){
                        logDto.setProductId(product.getId());
                    }else {
                        logDto.setProductId(null);
                    }
                }
            }
        } //推送申请记录
        else if(LogEnum.PUSH_LINK.getKey().toString().equals(logDto.getDeviceNumber().toUpperCase())){
            //推送打开连接
            PushMessageDto pushMessageDto =pushMessageAppService.findById(logDto.getProductId());
            logger.info("appName:{},productId:{},userId:{}",logDto.getAppName(),logDto.getProductId(),CPContext.getContext().getSeUserInfo().getUsername());
            if(pushMessageDto!=null){
                Product productes = repository.findByUrl(pushMessageDto.getUrl());
                if(productes!=null) {
                    logDto.setProductId(productes.getId());
                    logDto.setType(LogEnum.PUSH_LINK);
                }
            }
        }else{//产品申请
            logger.info("logType:{},productId:{},userId:{}",logDto.getType(),logDto.getProductId(),CPContext.getContext().getSeUserInfo().getUsername());
            if(logDto.getProductId()==null){
                CwException.throwIt("产品不存在！");
            }
            Product product= repository.findOne(logDto.getProductId());
            if(product!=null) {
                product.setClickNum(product.getClickNum()+11);
                product.setTodayApplyUser(productDao.findProductUserById(product.getId()).getApplyNum());
                logDto.setType(LogEnum.APPLY_LOAN);
            }
        }
        //记录引流渠道统计数
        discountSettingDomainService.incrementUvAndPv(logDto.getProductId());

        //记录数据日志
        logDto.setApplyDate(new Date());
        logDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        logAppService.create(logDto);
        //增加借款申请
        if(logDto.getProductId()!=null) {
            ApplyDto applyDto = new ApplyDto();
            applyDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            applyDto.setProductId(logDto.getProductId());
            applyDto.setApplyDate(new Date());
            applyDto.setChannelNo(logDto.getChannelNo());
            applyDto.setAppName(logDto.getAppName());
            applyDto.setApplyArea(logDto.getApplyArea());
            applyAppService.create(applyDto);
            //记录发送消息日志
            sendRegisterMessage(logDto);
        }
    }

    @Autowired
    private MessageAppService messageAppService;

    /**
     * 记录申请日志
     * @param logDto
     */
    private void sendRegisterMessage(LogDto logDto){
        Product product= repository.findOne(logDto.getProductId());
        if(product!=null) {
            MessageDto messageDto = new MessageDto();
            messageDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
            messageDto.setAppName(logDto.getAppName());
            messageDto.setTitle("借款申请已提交，待审核");
            messageDto.setContent("尊敬的用户，您好，你的" + product.getName() + "的借款申请已提交，请等待审核\n" +
                    "我们会及时为你审核，审核通过后及时为您发放贷款\n");
            messageAppService.create(messageDto);
        }
    }
    /**
     * 查询数据更新版本
     * @return
     */
    public ProductVersion findProductVersion(String channelNo){
        ProductVersion productVersion = productVersionRepository.findByChannelNo(channelNo);
        if(productVersion==null)
        {
            return null;
        }
        return productVersion;
    }

    /**
     * 根据地址定位查询门店服务
     * @param name
     * @return
     */
    public List<ProductStore> findStoreByName(String name,String appName){
        name = "%"+name+"%";
        return storeRepository.findByStoreName(name,appName);
    }

    /**
     * 查询推荐产品
     * @return
     */
    public Page<Product> findRecommendProduct(ProductSearchDto productSearchDto) {
        setProductSort(productSearchDto);
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("isRecommend"),productSearchDto.getIsRecommend()==null?"1":productSearchDto.getIsRecommend()));//1:推荐，2：热门，3:新口子
                list.add(cb.like(root.get("belongApp"),"%"+productSearchDto.getAppName()+"%"));
                list.add(cb.equal(root.get("isValid"),Boolean.TRUE));

                list.add(cb.or(cb.and(cb.lessThanOrEqualTo(root.get("limitUserTop"), root.get("todayApplyUser"))
                        , cb.or(cb.equal(root.get("isHidden"), 2),cb.equal(root.get("isHidden"), 0))),
                        cb.lessThanOrEqualTo(root.get("todayApplyUser"), root.get("limitUserTop"))
                ));
                if(productSearchDto.getChannelNo()!=null)
                {
                    if(!productSearchDto.getChannelNo().toUpperCase().contains("IOS".toUpperCase())) {
                        Boolean isAudit = productDao.findAuditChannel(productSearchDto.getChannelNo(),productSearchDto.getVersionNo());
                        if(isAudit) {
                            //读取配置Vivo配置
                            if(!Objects.isNull(productSearchDto.getVersionNo())) {
                                //查询审核数据状态
                                isAudit = checkVersionShowData(productSearchDto.getVersionNo());
                                if (isAudit) {
                                    list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                                } else {
                                    list.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                                    list.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                                }
                            }else{//之前的版本处理
                                list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                            }
                        }else{
                            list.add(cb.equal(root.get("androidFlag"), Boolean.TRUE));
                        }
                    }else{
                        Boolean versionAuditFlag = checkVersionShowData(productSearchDto.getChannelNo());
                        if (versionAuditFlag) {
                            list.add(cb.equal(root.get("appleFlag"), Boolean.TRUE));
                        } else {
                            list.add(cb.equal(root.get("appleFlag"), Boolean.FALSE));
                            list.add(cb.equal(root.get("androidFlag"), Boolean.FALSE));
                        }
                    }
                }

                Predicate[] predicates = new Predicate[list.size()];
                return cb.and(list.toArray(predicates));
            }
        };
        return repository.findAll(spec,productSearchDto.toPage());
    }

    /**
     * 修改产品排序
     * @return
     */
    public Boolean saveProductSort(ProductSortDto sortDto){
        String[] productIdAry = sortDto.getProductId().split(",");
        String[] showOrderAry = sortDto.getShowOrder().split(",");
//        String[] showOrderXsdAry = sortDto.getShowOrderXsd().split(",");
//        String[] showOrderJdsAry = sortDto.getShowOrderJds().split(",");
//        String[] showOrderJqwAry = sortDto.getShowOrderJqw().split(",");
//        String[] showOrderDsqbAry = sortDto.getShowOrderDsqb().split(",");
//        String[] showOrderDkqbAry = sortDto.getShowOrderDkqb().split(",");
//        String[] showOrderPxqbAry = sortDto.getShowOrderPxqb().split(",");
//        String[] showOrderLsqdAry = sortDto.getShowOrderLsqd().split(",");
//        String[] showOrderLybAry = sortDto.getShowOrderLyb().split(",");
//
//        String[] showOrderSydAry = sortDto.getShowOrderSyd().split(",");
//        String[] showOrderSrdAry = sortDto.getShowOrderSrd().split(",");
//        String[] showOrderJdbAry = sortDto.getShowOrderJdb().split(",");
//        String[] showOrderDqlAry = sortDto.getShowOrderDql().split(",");
//        String[] showOrderDkzgAry = sortDto.getShowOrderDkzg().split(",");
//        String[] showOrderJjkAry = sortDto.getShowOrderJjk().split(",");
//        String[] showOrderXedAry = sortDto.getShowOrderXed().split(",");
//        String[] showOrderJqsAry = sortDto.getShowOrderJqs().split(",");
//        String[] showOrderDkhAry = sortDto.getShowOrderDkh().split(",");
//        String[] showOrderJqhhAry = sortDto.getShowOrderJqhh().split(",");
//        String[] showOrderJdjqAry = sortDto.getShowOrderJdjq().split(",");
//        String[] showOrderDktAry = sortDto.getShowOrderDkt().split(",");
//
//        String[] showOrderKjwAry = sortDto.getShowOrderKjw().split(",");
//        String[] showOrderJqfAry = sortDto.getShowOrderJqf().split(",");
//        String[] showOrderMdbAry = sortDto.getShowOrderMdb().split(",");
//        String[] showOrderHdbAry = sortDto.getShowOrderHdb().split(",");
//        String[] showOrderDkssAry = sortDto.getShowOrderDkss().split(",");
//        String[] showOrderYjxAry = sortDto.getShowOrderYjx().split(",");

        if(productIdAry.length==showOrderAry.length){
            for(int i=0;i<productIdAry.length;i++){
                ProductListDto productListDto = new ProductListDto();
                productListDto.setId(Long.parseLong(productIdAry[i]));
                productListDto.setShowOrder(Integer.parseInt(showOrderAry[i]));
                //借钱帝
                productListDto.setShowOrderJqw(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDkqb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDsqb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJds(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderLsqd(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderLyb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderPxqb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderXsd(Integer.parseInt(showOrderAry[i]));

                productListDto.setShowOrderSyd(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderSrd(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJdb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDql(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDkzg(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJjk(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderXed(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJqs(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDkh(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJqhh(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJdjq(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDkt(Integer.parseInt(showOrderAry[i]));

                productListDto.setShowOrderKjw(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderMdb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderHdb(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderJqf(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderDkss(Integer.parseInt(showOrderAry[i]));
                productListDto.setShowOrderYjx(Integer.parseInt(showOrderAry[i]));

                productDao.saveProductSort(productListDto,sortDto.getAppSysType());
            }
        }

        return Boolean.FALSE;
    }

    /**
     * 保存流量控制
     * @param sortDto
     * @return
     */
    public Boolean saveProductConfig(ProductSortDto sortDto){
        String[] limitUserTopAry = sortDto.getLimitUserTop().split(",");
        String[] isHiddenAry = sortDto.getIsHidden().split(",");
        String[] jumpUrlAry = sortDto.getJumpUrl().split(",");
        String[] ids = sortDto.getId().split(",");

        if(limitUserTopAry.length==isHiddenAry.length){
            for(int i=0;i<limitUserTopAry.length;i++){
                ProductDto productListDto = new ProductDto();
                productListDto.setId(Long.parseLong(ids[i]));
                productListDto.setIsHidden(Integer.parseInt(isHiddenAry[i]));
                productListDto.setJumpUrl(jumpUrlAry[i]);
                productListDto.setLimitUserTop(Integer.parseInt(limitUserTopAry[i]));
                productDao.updateProductConfig(productListDto);
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 查询任务配置并执行
     */
    public void executeJob(){
        List<ProductQuartzConfigDto> productQuartzConfigDtoList = productQuartzConfigDao.findAll();

        for(ProductQuartzConfigDto productQuartzConfigDto: productQuartzConfigDtoList){
            String onlineDate = Utils.convertDate(productQuartzConfigDto.getOnlineDate());
            String offlineDate = Utils.convertDate(productQuartzConfigDto.getOfflineDate());
            String todayDate = Utils.convertDate(new Date());
            //判断是否今天上架
            if(todayDate.equals(onlineDate)){
                productQuartzConfigDao.updateProductConfig(1,productQuartzConfigDto.getProductId());
            }
            //判断是否今天下架
            if(todayDate.equals(offlineDate)){
                productQuartzConfigDao.updateProductConfig(0,productQuartzConfigDto.getProductId());
            }
        }

    }

    /**
     * 设置产品推荐产品ID
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findRecommendProductByProductId(Long productId,String versionNo,String channelNo){
        return productDao.findProductRecommendByProductId(productId,versionNo,channelNo);
    }

    /**
     * 查询未被推荐的产品
     * @param productId
     * @param productName
     * @return
     */
    public List<ProductRecommendListDto> findNoRecommendByProductId(Long productId,String productName){
        List<ProductRecommendListDto> productList = productDao.findNoRecommendByProductId(productId,productName);
        return productList;
    }

    /**
     * 设置产品推荐
     * @param recommendListDto
     * @return
     */
    public Boolean productSetRecommend(ProductRecommendListDto recommendListDto){
        productDao.productSetRecommend(recommendListDto);
        return Boolean.TRUE;
    }

    /**
     * 产品推荐功能开关
     * @param recommendListDto
     * @return
     */
    public Boolean updateProductRecommendFlag(ProductDto recommendListDto){
        productDao.updateProductRecommendFlag(recommendListDto);
        return Boolean.TRUE;
    }
    /**
     * 产品排序列表
     * @param dto
     * @return
     */
    public List<ProductListDto> findByProductSortList(ProductSearchDto dto){
        return productDao.findByProductSortList(dto);
    }

    /**
     * 查询审核版本
     * @param versionNo
     * @return
     */
    public ProductAuditVersion getAuditVersion(String versionNo){
        ProductAuditVersion productAuditVersion = productAuditRepository.findByDataVersion(versionNo);
        return productAuditVersion;
    }

}
