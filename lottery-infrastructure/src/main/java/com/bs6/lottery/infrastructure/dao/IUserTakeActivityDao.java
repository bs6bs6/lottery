package com.bs6.lottery.infrastructure.dao;

import com.bs6.lottery.infrastructure.po.UserTakeActivity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IUserTakeActivityDao {

    /**
     * 插入用户领取活动信息
     *
     * @param userTakeActivity 入参
     */
    void insert(UserTakeActivity userTakeActivity);

}
