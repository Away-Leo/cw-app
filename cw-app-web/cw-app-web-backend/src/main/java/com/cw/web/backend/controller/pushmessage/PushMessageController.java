package com.cw.web.backend.controller.pushmessage;

import com.cw.biz.push.app.dto.PushMessageDto;
import com.cw.biz.push.app.service.PushMessageAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 消息推送
 * Created by dujy on 2017-11-09.
 */
@RestController
public class PushMessageController extends AbstractBackendController {

    @Autowired
    private PushMessageAppService pushMessageAppService;

    /**
     * 新增推送信息
     * @param pushMessageDto
     * @return
     */
    @PostMapping("/message/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody PushMessageDto pushMessageDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = pushMessageAppService.create(pushMessageDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("新增推送消息成功");
        return cpViewResultInfo;
    }

    /**
     * 查询推送消息详情
     * @param id
     * @return
     */
    @GetMapping("/message/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        PushMessageDto bannerDto = pushMessageAppService.findById(id);
        cpViewResultInfo.setData(bannerDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询推送消息
     * @param pushMessageDto
     * @return
     */
    @PostMapping("/message/findAll.json")
    @ResponseBody
    public CPViewResultInfo findAll(@RequestBody PushMessageDto pushMessageDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<PushMessageDto> creditCardDtos = pushMessageAppService.findByCondition(pushMessageDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 修改推送消息
     * @param bannerDto
     * @return
     */
    @PostMapping("/message/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody PushMessageDto bannerDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = pushMessageAppService.update(bannerDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("推送消息修改成功");
        return cpViewResultInfo;
    }

    /**
     * 推送消息
     * @param pushMessageDto
     * @return
     */
    @PostMapping("/message/sendMessage.json")
    @ResponseBody
    public CPViewResultInfo sendMessage(@RequestBody PushMessageDto pushMessageDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean sendResult = pushMessageAppService.sendPushMessage(pushMessageDto.getId());
        cpViewResultInfo.setData(sendResult);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("推送消息修改成功");
        return cpViewResultInfo;
    }

}
