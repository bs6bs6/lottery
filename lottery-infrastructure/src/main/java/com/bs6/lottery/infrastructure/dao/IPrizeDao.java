package com.bs6.lottery.infrastructure.dao;

import com.bs6.lottery.infrastructure.po.Prize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IPrizeDao {

    /**
     * 查询奖品信息
     *
     * @param prizeId 奖品ID
     * @return        奖品信息
     */
    Prize queryPrizeInfo(String prizeId);

    /**
     * 插入奖品配置
     *
     * @param list 奖品配置
     */
    void insertList(List<Prize> list);

}
