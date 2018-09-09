package com.renqi.market.common;

/**
 * @author luhonggang
 * @date  2018-08-14
 */
@SuppressWarnings("all")
public enum MarketMark {
    MARKET_PROVINCE("3","省呗"),
    MARKET_BORROW("4","借了吗"),
    MARKET_CERADIT_CARD("5","51信用卡");
    private String marketType;
    private String marketName;

    MarketMark(String marketType, String marketName) {
        this.marketType = marketType;
        this.marketName = marketName;
    }

    public String getMarketType() {
        return marketType;
    }

    public String getMarketName() {
        return marketName;
    }

}
