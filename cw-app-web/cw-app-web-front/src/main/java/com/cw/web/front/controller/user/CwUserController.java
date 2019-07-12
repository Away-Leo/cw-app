package com.cw.web.front.controller.user;

import com.cw.biz.CPContext;
import com.cw.biz.user.app.dto.CwUserInfoDto;
import com.cw.biz.user.app.service.CwUserInfoAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户个人信息接口
 * Created by dujy on 2017-05-20.
 */
@RestController
public class CwUserController extends AbstractFrontController {

    @Autowired
    private CwUserInfoAppService cwUserInfoAppService;

    /**
     * 新增或者修改客户信息
     * @return
     */
    @PostMapping("/userInfo/update.json")
    public CPViewResultInfo update(@RequestBody CwUserInfoDto cwUserInfoDto)
    {
        cwUserInfoDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = cwUserInfoAppService.update(cwUserInfoDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("用户信息修改成功");

        return cpViewResultInfo;
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userInfo/findById.json")
    public CPViewResultInfo findById()
    {
        Long id = CPContext.getContext().getSeUserInfo().getId();
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CwUserInfoDto cwUserInfoDto = cwUserInfoAppService.findById(id);
        cpViewResultInfo.setData(cwUserInfoDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
