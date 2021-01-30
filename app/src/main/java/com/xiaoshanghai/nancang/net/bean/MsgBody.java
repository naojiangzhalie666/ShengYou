package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

public class MsgBody extends BaseResult implements MultiItemEntity {

    public static final int MSG_IN_ROOM = 0;//进出房间消息

    public static final int MSG_TALK = 1;//聊天

    public static final int MSG_SEND_GIFT = 3;//送礼物

    public static final int MSG_PUBLIC_SETTING = 4; //房间公屏设置

    public static final int MSG_PICE_SEAT = 5; //排麦模式弹幕

    public static final int MSG_LOTTERY = 6; //抽奖弹幕

    public int type;

    public String message;

    public VoiceRoomSeatEntity.UserInfo userInfo;

    public SendGift sendGift;

    public LotteryEntity lottery;

    public boolean roomPublic = false;

    public boolean roomPick = false;

    @Override
    public int getItemType() {
        return type;
    }


}
