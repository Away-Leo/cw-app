package com.cw.biz.agency.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AgencyChannelDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 修改渠道名称
     * @param channelId
     * @param name
     */
    public void updateNameByChannleId(Long channelId, String name) {
        String sql = "update cw_agency_channel set name='" + name + "' where channel_id=" + channelId;
        jdbcTemplate.update(sql);
    }
}
