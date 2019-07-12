package com.cw.biz.contract.domain.service;

import com.cw.biz.contract.app.dto.ContractDto;
import com.cw.biz.contract.domain.dao.ContractDao;
import com.cw.biz.contract.domain.entity.Contract;
import com.cw.biz.contract.domain.repository.ContractRepository;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.core.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ContractDomainService {

    @Autowired
    private ContractRepository repository;
    @Autowired
    private ContractDao dao;
    /**
     * 新增合同
     * @param contractDto
     * @return
     */
    private Contract create(ContractDto contractDto)
    {
        Contract contract = new Contract();
        contract.from(contractDto);
        return repository.save(contract);
    }

    /**
     * 修改合同
     * @param contractDto
     * @return
     */
    public Contract update(ContractDto contractDto)
    {
        contractDto.setPostDate(Utils.strConvertDate(contractDto.getPostDateStr()));
        contractDto.setProductId(contractDto.getId());
        Contract contract = repository.findByProductId(contractDto.getId());
        if(contract == null)
        {
            contract = create(contractDto);
        }else {
            contract.from(contractDto);
            repository.save(contract);
        }
        return contract;
    }

    /**
     * 查询合同列表
     * @param searchDto
     * @return
     */
    public List<ContractDto> findAll(ReportSearchDto searchDto){
        return dao.findAll(searchDto);
    }

}
