package com.cw.web.front.controller.goods;

import com.cw.biz.goods.app.dto.PeriodDto;
import com.cw.biz.goods.app.service.PeriodAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品服务
 * Created by dujy on 2017-06-26.
 */
@RestController
public class PeriodController extends AbstractFrontController {

    @Autowired
    private PeriodAppService periodAppService;

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/period/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        PeriodDto periodDto = periodAppService.findById(id);
        cpViewResultInfo.setData(periodDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询商品分期列表
     * @param periodDto
     * @return
     */
    @PostMapping("/period/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody PeriodDto periodDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<PeriodDto> periodDtos = periodAppService.findByCondition(periodDto);
        cpViewResultInfo.setData(periodDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

}
