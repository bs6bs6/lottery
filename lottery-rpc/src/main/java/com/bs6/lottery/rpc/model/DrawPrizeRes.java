package com.bs6.lottery.rpc.model;

import com.bs6.lottery.common.Result;

import java.io.Serializable;


public class DrawPrizeRes extends Result implements Serializable {

    private PrizeDTO prizeDTO;

    public DrawPrizeRes(String code, String info) {
        super(code, info);
    }

    public PrizeDTO getPrizeDTO() {
        return prizeDTO;
    }

    public void setPrizeDTO(PrizeDTO prizeDTO) {
        this.prizeDTO = prizeDTO;
    }

}
