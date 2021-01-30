package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

public class MyCarTypeResult extends JSectionEntity implements Serializable {

    public MyCarTypeResult(boolean isHeader, String typeName, int deckTotal, Decks decks) {
        this.isHeader = isHeader;
        this.typeName = typeName;
        this.deckTotal = deckTotal;
        this.decks = decks;
    }

    public boolean isHeader;

    public String typeName;

    public int deckTotal;

    public Decks decks;


    @Override
    public boolean isHeader() {
        return isHeader;
    }

}
