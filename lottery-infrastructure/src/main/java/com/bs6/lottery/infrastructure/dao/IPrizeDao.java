package com.bs6.lottery.infrastructure.dao;

import com.bs6.lottery.infrastructure.po.Prize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 奖品信息表DAO
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/4
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
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
