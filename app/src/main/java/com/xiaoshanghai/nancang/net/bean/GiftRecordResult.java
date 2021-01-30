package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class GiftRecordResult extends BaseResult {


    /**
     * pepperGiftTotal : 3
     * pepperGifts : [{"id":"1","giftName":"熊掌","giftStaticUrl":"http://192.168.0.12:8080/imgs/gift/1","giftUrl":"http://192.168.0.12:8080/imgs/gift/1","giftType":1,"giftPriceType":1,"giftPrice":10,"nobleId":"0","createTime":"2020-07-11 12:04:49","active":1,"getTotal":3}]
     * coinGiftTotal : 3
     * coinGifts : [{"id":"2","giftName":"独角兽","giftStaticUrl":"http://192.168.0.12:8080/imgs/gift/1","giftUrl":"http://192.168.0.12:8080/imgs/gift/1","giftType":1,"giftPriceType":2,"giftPrice":66,"nobleId":"0","createTime":"2020-07-11 12:07:04","active":1,"getTotal":2},{"id":"4","giftName":"板砖","giftStaticUrl":"http://192.168.0.12:8080/imgs/gift/1","giftUrl":"http://192.168.0.12:8080/imgs/gift/1","giftType":3,"giftPriceType":2,"giftPrice":555,"nobleId":"1","createTime":"2020-07-11 12:12:42","active":1,"getTotal":1}]
     */

    private int pepperGiftTotal;
    private int coinGiftTotal;
    private List<GiftsResult> pepperGifts;
    private List<GiftsResult> coinGifts;

    public int getPepperGiftTotal() {
        return pepperGiftTotal;
    }

    public void setPepperGiftTotal(int pepperGiftTotal) {
        this.pepperGiftTotal = pepperGiftTotal;
    }

    public int getCoinGiftTotal() {
        return coinGiftTotal;
    }

    public void setCoinGiftTotal(int coinGiftTotal) {
        this.coinGiftTotal = coinGiftTotal;
    }

    public List<GiftsResult> getPepperGifts() {
        return pepperGifts;
    }

    public void setPepperGifts(List<GiftsResult> pepperGifts) {
        this.pepperGifts = pepperGifts;
    }

    public List<GiftsResult> getCoinGifts() {
        return coinGifts;
    }

    public void setCoinGifts(List<GiftsResult> coinGifts) {
        this.coinGifts = coinGifts;
    }
}
