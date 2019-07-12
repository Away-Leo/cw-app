package com.cw.biz.contract.app.service;

import com.cw.biz.contract.app.dto.ContractDto;
import com.cw.biz.contract.domain.service.ContractDomainService;
import com.cw.biz.home.app.dto.ReportSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 合同服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ContractAppService {

    @Autowired
    private ContractDomainService contractDomainService;

    /**
     * 修改合同
     * @param contractDto
     * @return
     */
    public Long update(ContractDto contractDto)
    {
        return contractDomainService.update(contractDto).getId();
    }

    /**
     * 查看合同信息
     * @param searchDto
     * @return
     */
    public List<ContractDto> findAll(ReportSearchDto searchDto){
        return contractDomainService.findAll(searchDto);
    }

}
