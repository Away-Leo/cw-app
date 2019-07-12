package com.cw.web.front.controller.message;

import com.cw.biz.message.app.dto.MessageDto;
import com.cw.biz.message.app.service.MessageAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息服务
 * Created by dujy on 2017-05-20.
 */
@RestController
public class MessageController extends AbstractFrontController {

    @Autowired
    private MessageAppService messageAppService;

    /**
     * 查询消息详情
     * @param id
     * @return
     */
    @GetMapping("/message/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        MessageDto messageDto = messageAppService.findById(id);
        cpViewResultInfo.setData(messageDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询我的消息
     * @param messageDto
     * @return
     */
    @GetMapping("/message/list.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(MessageDto messageDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<MessageDto> messageDtos = messageAppService.findByCondition(messageDto);
        cpViewResultInfo.setData(messageDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


}
