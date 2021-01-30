package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class DeckResult extends BaseResult {


    /**
     * deckTotal : 2
     * decks : [{"id":"1","deckType":2,"deckName":"法拉利","deckStaticUrl":"http://192.168.0.12:8080/imgs/gift/1.png","deckUrl":"http://192.168.0.12:8080/imgs/gift/1.png","isLimit":0,"costType":1,"costNumber":666,"useDay":7,"createTime":"2020-07-13 12:08:12","active":1,"validity":1,"used":1}]
     */

    private int deckTotal;
    private List<Decks> decks;

    public int getDeckTotal() {
        return deckTotal;
    }

    public void setDeckTotal(int deckTotal) {
        this.deckTotal = deckTotal;
    }

    public List<Decks> getDecks() {
        return decks;
    }

    public void setDecks(List<Decks> decks) {
        this.decks = decks;
    }


}
