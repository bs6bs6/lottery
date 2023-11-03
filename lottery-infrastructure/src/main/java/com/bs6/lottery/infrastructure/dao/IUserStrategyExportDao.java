package com.bs6.lottery.infrastructure.dao;


import com.bs6.lottery.infrastructure.po.UserStrategyExport;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IUserStrategyExportDao {

    /**
     * 新增数据
     * @param userStrategyExport 用户策略
     */
    void insert(UserStrategyExport userStrategyExport);

    /**
     * 查询数据
     * @param uid 用户ID
     * @return 用户策略
     */
    UserStrategyExport queryUserStrategyExportByUId(String uid);

}
