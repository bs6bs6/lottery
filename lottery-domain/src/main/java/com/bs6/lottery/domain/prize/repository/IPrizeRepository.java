package com.bs6.lottery.domain.prize.repository;

public interface IPrizeRepository {

    void updateUserPrizeStatus(String uid, Long orderId, String prizeId, Integer distributeStatus);


}
