package com.cw.biz.discount.domain.service;

import com.cw.biz.discount.app.dto.ChannelQualityDto;
import com.cw.biz.discount.domain.entity.ChannelQuality;
import com.cw.biz.discount.domain.repository.ChannelQualityRepository;
import com.cw.core.common.base.BaseDomainService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChannelQualityDomainService extends BaseDomainService<ChannelQualityRepository,ChannelQuality, ChannelQualityDto> {


}
