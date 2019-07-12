package com.cw.web.front.controller.goods;

import com.cw.biz.goods.app.dto.PostAddressDto;
import com.cw.biz.goods.app.service.PostAddressAppService;
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
public class PostAddressController extends AbstractFrontController {

    @Autowired
    private PostAddressAppService postAddressAppService;

    /**
     * 邮寄地址
     * @param postAddressDto
     * @return
     */
    @PostMapping("/address/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody PostAddressDto postAddressDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long postId = postAddressAppService.update(postAddressDto);
        cpViewResultInfo.setData(postId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存邮寄地址成功");

        return cpViewResultInfo;
    }

    /**
     * 查询邮寄地址详情
     * @param id
     * @return
     */
    @GetMapping("/address/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        PostAddressDto postAddressDto = postAddressAppService.findById(id);
        cpViewResultInfo.setData(postAddressDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询地址列表
     * @return
     */
    @GetMapping("/address/findAll.json")
    @ResponseBody
    public CPViewResultInfo findAll(){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<PostAddressDto> postAddressDtoList = postAddressAppService.findAll();
        cpViewResultInfo.setData(postAddressDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

}
