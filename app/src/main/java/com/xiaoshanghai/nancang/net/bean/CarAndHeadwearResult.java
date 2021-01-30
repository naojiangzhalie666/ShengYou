package com.xiaoshanghai.nancang.net.bean;

public class CarAndHeadwearResult extends BaseResult {


    /**
     * id : 4
     * deckType : 1
     * deckName : 大表哥
     * deckStaticUrl : http://192.168.0.12:8080/imgs/gift/1.png
     * deckUrl : http://192.168.0.12:8080/imgs/gift/1.png
     * isLimit : 0
     * costType : 0
     * costNumber : 666
     * useDay : 7
     * createTime : 2020-07-15 14:10:15
     * active : 1
     */

    private String id;                      //	头饰id
    private int deckType;                   //	装扮类型 1头饰 2座驾，此处无意义
    private String deckName;                //	头饰名称
    private String deckStaticUrl;           //	头饰地址
    private String deckUrl;                 //	头饰动态地址，此处用此地址
//    private int isLimit;                    //	次处无意义
//    private int costType;                   //	次处无意义
//    private int costNumber;                 //	次处无意义
//    private int useDay;                     //	次处无意义
//    private String createTime;              //	次处无意义
//    private int active;                     //	次处无意义


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeckType() {
        return deckType;
    }

    public void setDeckType(int deckType) {
        this.deckType = deckType;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckStaticUrl() {
        return deckStaticUrl;
    }

    public void setDeckStaticUrl(String deckStaticUrl) {
        this.deckStaticUrl = deckStaticUrl;
    }

    public String getDeckUrl() {
        return deckUrl;
    }

    public void setDeckUrl(String deckUrl) {
        this.deckUrl = deckUrl;
    }
}
