package com.cw.biz.goods.app.service;

import com.cw.biz.goods.app.dto.PostAddressDto;
import com.cw.biz.goods.domain.entity.PostAddress;
import com.cw.biz.goods.domain.service.PostAddressDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class PostAddressAppService {

    @Autowired
    private PostAddressDomainService postAddressDomainService;

    /**
     * 新增邮寄地址
     * @param postAddressDto
     * @return
     */
    public Long create(PostAddressDto postAddressDto){
        return postAddressDomainService.create(postAddressDto).getId();
    }

    /**
     * 修改邮件地址
     * @param postAddressDto
     * @return
     */
    public Long update(PostAddressDto postAddressDto){
        return postAddressDomainService.update(postAddressDto).getId();
    }

    /**
     * 查询邮寄地址
     * @param id
     * @return
     */
    public PostAddressDto findById(Long id){
        PostAddressDto postAddressDto = new PostAddressDto();
        PostAddress postAddress = postAddressDomainService.findById(id);
        if(postAddress!=null){
            postAddressDto = postAddress.to(PostAddressDto.class);
        }
        return postAddressDto;
    }

    /**
     * 按条件查询邮寄地址
     * @return
     */
    public List<PostAddressDto> findAll(){
        List<PostAddressDto> postAddressDtos = new ArrayList<>();
        List<PostAddress> postAddressList =  postAddressDomainService.findAll();
        for(PostAddress postAddress:postAddressList){
            postAddressDtos.add(postAddress.to(PostAddressDto.class));
        }
        return postAddressDtos;
    }

}
