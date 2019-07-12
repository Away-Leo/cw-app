package com.cw.biz.goods.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.goods.app.dto.PostAddressDto;
import com.cw.biz.goods.domain.entity.PostAddress;
import com.cw.biz.goods.domain.repository.PostAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮寄地址服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class PostAddressDomainService {

    @Autowired
    private PostAddressRepository repository;
    /**
     * 新增邮寄地址
     * @param postAddressDto
     * @return
     */
    public PostAddress create(PostAddressDto postAddressDto)
    {
        PostAddress postAddress = new PostAddress();
        postAddress.from(postAddressDto);
        postAddress.setUserId(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(postAddress);
    }

    /**
     * 修改邮寄地址
     * @param postAddressDto
     * @return
     */
    public PostAddress update(PostAddressDto postAddressDto){
        PostAddress postAddress = repository.findOne(postAddressDto.getId());
        if(postAddress == null){
            create(postAddressDto);
        }
        postAddress.from(postAddressDto);
        repository.save(postAddress);
        return postAddress;
    }

    /**
     * 查询PostAddress
     * @param id
     * @return
     */
    public PostAddress findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询产品列表
     * @return
     */
    public List<PostAddress> findAll(){
        return repository.findByUserId(CPContext.getContext().getSeUserInfo().getId());
    }

}
