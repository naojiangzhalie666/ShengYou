package com.tencent.liteav.trtcvoiceroom.ui.room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;
import com.tencent.liteav.audiosettingkit.AudioEffectPanel;
import com.tencent.liteav.trtcvoiceroom.R;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomCallback;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDelegate;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;
import com.tencent.liteav.trtcvoiceroom.ui.widget.ConfirmDialogFragment;
import com.tencent.liteav.trtcvoiceroom.ui.widget.InputTextMsgDialog;
import com.tencent.liteav.trtcvoiceroom.ui.widget.SelectMemberView;
import com.tencent.liteav.trtcvoiceroom.ui.widget.msg.MsgEntity;
import com.tencent.liteav.trtcvoiceroom.ui.widget.msg.MsgListAdapter;
import com.tencent.trtc.TRTCCloudDef;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.SeatInfo.STATUS_CLOSE;
import static com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.SeatInfo.STATUS_UNUSED;
import static com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.SeatInfo.STATUS_USED;
import static com.tencent.liteav.trtcvoiceroom.model.impl.base.TXSeatInfo.STATUS_OPEN;

public class VoiceRoomBaseActivity extends AppCompatActivity implements VoiceRoomSeatAdapter.OnItemClickListener, TRTCVoiceRoomDelegate, InputTextMsgDialog.OnTextSendListener, MsgListAdapter.OnItemClickListener {
    protected static final String TAG = VoiceRoomBaseActivity.class.getName();

    protected static final int MAX_SEAT_SIZE = 7;      //????????????????????????
    protected static final String VOICEROOM_ROOM_ID = "room_id";    //??????id
    protected static final String VOICEROOM_ROOM_NAME = "room_name";//?????????
    protected static final String VOICEROOM_USER_NAME = "user_name";//?????????
    protected static final String VOICEROOM_USER_ID = "user_id";//??????id
    protected static final String VOICEROOM_USER_SIG = "user_sig";
    protected static final String VOICEROOM_NEED_REQUEST = "need_request";  //??????????????????????????????
    protected static final String VOICEROOM_SEAT_COUNT = "seat_count";
    protected static final String VOICEROOM_AUDIO_QUALITY = "audio_quality";    //??????
    protected static final String VOICEROOM_USER_AVATAR = "user_avatar";    //????????????
    protected static final String VOICEROOM_ROOM_COVER = "room_cover";      //????????????

    /**
     *
     */
    protected String mSelfUserId;     //????????????ID
    protected int mCurrentRole;    //??????????????????
    protected Set<String> mSeatUserSet; //???????????????????????????
    protected TRTCVoiceRoom mTRTCVoiceRoom; //??????????????????

    protected List<VoiceRoomSeatEntity> mVoiceRoomSeatEntityList;   //????????????
    protected VoiceRoomSeatAdapter mVoiceRoomSeatAdapter;//??????Adapter

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected Group mGroupBottomTool;
    protected CircleImageView mImgHead;
    protected TextView mTvName;
    protected RecyclerView mRvSeat;
    protected RecyclerView mRvImMsg;
    protected View mToolBarView;
    protected AppCompatImageButton mBtnMsg;
    protected AppCompatImageButton mBtnMic;
    protected AppCompatImageButton mBtnAudio;
    protected AppCompatImageButton mBtnEffect;
    protected AudioEffectPanel mAnchorAudioPanel;
    protected SelectMemberView mViewSelectMember;
    protected InputTextMsgDialog mInputTextMsgDialog;
    protected String mRoomId;//??????ID
    protected String mRoomName;//?????????
    protected String mUserName;//???????????????
    protected String mUserAvatar;//????????????url
    protected String mRoomCover;    //????????????
    protected String mMainSeatUserId;//1??????id
    protected boolean mNeedRequest;// ????????????????????????
    protected int mAudioQuality;//??????

    protected List<MsgEntity> mMsgEntityList;//????????????
    protected MsgListAdapter mMsgListAdapter;//??????adapter
    protected ConfirmDialogFragment mConfirmDialogFragment;//?????????dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ?????????????????????????????????????????????
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.trtcvoiceroom_activity_main);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnchorAudioPanel != null) {
            mAnchorAudioPanel.unInit();
            mAnchorAudioPanel = null;
        }
    }

    protected void initListener() {
        mBtnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkButtonPermission()) {
                    boolean currentMode = !mBtnMic.isSelected();
                    mBtnMic.setSelected(currentMode);
                    if (currentMode) {
                        mTRTCVoiceRoom.startMicrophone();
                        ToastUtils.showLong("?????????????????????");
                    } else {
                        mTRTCVoiceRoom.stopMicrophone();
                        mAnchorAudioPanel.stopPlay();
                        ToastUtils.showLong("?????????????????????");
                    }
                }
            }
        });
        mBtnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean currentMode = !mBtnAudio.isSelected();
                mBtnAudio.setSelected(currentMode);
                mTRTCVoiceRoom.muteAllRemoteAudio(!currentMode);
                if (currentMode) {
                    ToastUtils.showLong("??????????????????");
                } else {
                    ToastUtils.showLong("????????????");
                }
            }
        });
        mBtnEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkButtonPermission()) {
                    if (mAnchorAudioPanel.isShown()) {
                        mAnchorAudioPanel.setVisibility(View.GONE);
                        mAnchorAudioPanel.hideAudioPanel();
                    } else {
                        mAnchorAudioPanel.setVisibility(View.VISIBLE);
                        mAnchorAudioPanel.showAudioPanel();
                    }
                }
            }
        });
        mBtnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputMsgDialog();
            }
        });
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @return ???????????????
     */
    protected boolean checkButtonPermission() {
        boolean hasPermission = (mCurrentRole == TRTCCloudDef.TRTCRoleAnchor);
        if (!hasPermission) {
            ToastUtils.showLong("?????????????????????");
        }
        return hasPermission;
    }

    protected void initData() {
        Intent intent = getIntent();
        mRoomId = intent.getStringExtra(VOICEROOM_ROOM_ID);
        mRoomName = intent.getStringExtra(VOICEROOM_ROOM_NAME);
        mUserName = intent.getStringExtra(VOICEROOM_USER_NAME);
        mSelfUserId = intent.getStringExtra(VOICEROOM_USER_ID);
        mNeedRequest = intent.getBooleanExtra(VOICEROOM_NEED_REQUEST, false);
        mUserAvatar = intent.getStringExtra(VOICEROOM_USER_AVATAR);
        mRoomCover = intent.getStringExtra(VOICEROOM_ROOM_COVER);
        mAudioQuality = intent.getIntExtra(VOICEROOM_AUDIO_QUALITY, TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC);
        //        mSeatCount = intent.getIntExtra(VOICEROOM_SEAT_COUNT);
        mTRTCVoiceRoom = TRTCVoiceRoom.sharedInstance(this);
        mTRTCVoiceRoom.setDelegate(this);
        mAnchorAudioPanel.setAudioEffectManager(mTRTCVoiceRoom.getAudioEffectManager());

    }

    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mGroupBottomTool = (Group) findViewById(R.id.group_bottom_tool);
        mImgHead = (CircleImageView) findViewById(R.id.img_head);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mRvSeat = (RecyclerView) findViewById(R.id.rv_seat);
        mRvImMsg = (RecyclerView) findViewById(R.id.rv_im_msg);
        mToolBarView = findViewById(R.id.tool_bar_view);
        mBtnMsg = (AppCompatImageButton) findViewById(R.id.btn_msg);
        mBtnMic = (AppCompatImageButton) findViewById(R.id.btn_mic);
        mBtnAudio = (AppCompatImageButton) findViewById(R.id.btn_audio);
        mBtnEffect = (AppCompatImageButton) findViewById(R.id.btn_effect);
        mAnchorAudioPanel = (AudioEffectPanel) findViewById(R.id.anchor_audio_panel);
        mAnchorAudioPanel.setBackground(getResources().getDrawable(R.drawable.trtcvoiceroom_audio_effect_setting_bg_gradient));

        mViewSelectMember = new SelectMemberView(this);
        mConfirmDialogFragment = new ConfirmDialogFragment();
        mAnchorAudioPanel.setOnAudioEffectPanelHideListener(new AudioEffectPanel.OnAudioEffectPanelHideListener() {
            @Override
            public void onClosePanel() {
                mAnchorAudioPanel.setVisibility(View.GONE);
                mAnchorAudioPanel.hideAudioPanel();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        mInputTextMsgDialog = new InputTextMsgDialog(this, R.style.TRTCVoiceRoomInputDialog);
        mInputTextMsgDialog.setmOnTextSendListener(this);
        mMsgEntityList = new ArrayList<>();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mMsgListAdapter = new MsgListAdapter(this, mMsgEntityList, this);
        mRvImMsg.setLayoutManager(new LinearLayoutManager(this));
        mRvImMsg.setAdapter(mMsgListAdapter);

        mVoiceRoomSeatEntityList = new ArrayList<>();
        for (int i = 0; i < MAX_SEAT_SIZE - 1; i++) {
            mVoiceRoomSeatEntityList.add(new VoiceRoomSeatEntity());
        }
        mVoiceRoomSeatAdapter = new VoiceRoomSeatAdapter(this, mVoiceRoomSeatEntityList, this);
        mRvSeat.setLayoutManager(gridLayoutManager);
        mRvSeat.setAdapter(mVoiceRoomSeatAdapter);
    }

    /**
     *     /////////////////////////////////////////////////////////////////////////////////
     *     //
     *     //                      ??????????????????
     *     //
     *     /////////////////////////////////////////////////////////////////////////////////
     */
    /**
     * ??????????????????
     */
    private void showInputMsgDialog() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputTextMsgDialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //????????????
        mInputTextMsgDialog.getWindow().setAttributes(lp);
        mInputTextMsgDialog.setCancelable(true);
        mInputTextMsgDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputTextMsgDialog.show();
    }

    @Override
    public void onTextSend(String msg) {
        if (msg.length() == 0) {
            return;
        }
        byte[] byte_num = msg.getBytes(StandardCharsets.UTF_8);
        if (byte_num.length > 160) {
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        //????????????
        MsgEntity entity = new MsgEntity();
        entity.userName = "???";
        entity.content = msg;
        entity.userId = mSelfUserId;
        entity.type = MsgEntity.TYPE_NORMAL;
        showImMsg(entity);

        mTRTCVoiceRoom.sendRoomTextMsg(msg, new TRTCVoiceRoomCallback.ActionCallback() {
            @Override
            public void onCallback(int code, String msg) {
                if (code == 0) {
                    ToastUtils.showShort("????????????");
                } else {
                    ToastUtils.showShort("??????????????????[" + code + "]" + msg);
                }
            }
        });
    }

    private void showImMsg(final MsgEntity entity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMsgEntityList.size() > 1000) {
                    while (mMsgEntityList.size() > 900) {
                        mMsgEntityList.remove(0);
                    }
                }
                mMsgEntityList.add(entity);
                mMsgListAdapter.notifyDataSetChanged();
                mRvImMsg.smoothScrollToPosition(mMsgListAdapter.getItemCount());
            }
        });
    }

    public void resetSeatView() {
        mSeatUserSet.clear();
        for (VoiceRoomSeatEntity entity : mVoiceRoomSeatEntityList) {
            entity.isUsed = false;
        }
        mVoiceRoomSeatAdapter.notifyDataSetChanged();
    }

    /**
     * ??????????????????????????????
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onError(int code, String message) {

    }

    @Override
    public void onWarning(int code, String message) {

    }

    @Override
    public void onDebugLog(String message) {

    }

    @Override
    public void onRoomDestroy(String roomId) {

    }

    @Override
    public void onRoomInfoChange(TRTCVoiceRoomDef.RoomInfo roomInfo) {
        mNeedRequest = roomInfo.needRequest;
        mRoomName = roomInfo.roomName;
        mToolbarTitle.setText(getString(R.string.trtcvoiceroom_main_title, roomInfo.roomName, roomInfo.roomId));
    }

    @Override
    public void onSeatListChange(final List<TRTCVoiceRoomDef.SeatInfo> seatInfoList) {
        //??????????????????
        //????????????????????????????????????????????????????????????userId
        final List<String> userids = new ArrayList<>();
        //?????????????????????
        for (int i = 0; i < seatInfoList.size(); i++) {
            //?????????????????????????????????????????????????????????
            TRTCVoiceRoomDef.SeatInfo newSeatInfo = seatInfoList.get(i);
            // ?????????????????????????????????????????????????????????????????????0?????????????????????
            if (i == 0) {
                //?????????id????????????????????????ID?????????????????????id?????????
                if (mMainSeatUserId == null || !mMainSeatUserId.equals(newSeatInfo.userId)) {
                    //???????????????
                    mMainSeatUserId = newSeatInfo.userId;
                    userids.add(newSeatInfo.userId);
                    mTvName.setText("?????????????????????");
                }
                continue;
            }
            // ????????????????????????????????? ??????????????????????????????????????????
            VoiceRoomSeatEntity oldSeatEntity = mVoiceRoomSeatEntityList.get(i - 1);
            //??????????????????userid??????????????????????????????userid??????????????????userid?????????
            if (newSeatInfo.userId != null && !newSeatInfo.userId.equals(oldSeatEntity.userId)) {
                //userId??????????????????????????????????????????
                //?????????????????????userId??????????????????????????????????????????????????????
                userids.add(newSeatInfo.userId);
            }
            oldSeatEntity.userId = newSeatInfo.userId;

//            if (newSeatInfo.status )
            // ???????????????????????????
//            switch (newSeatInfo.status) {
//                //??????????????????
//                case STATUS_UNUSED:     //??????????????????
//                    oldSeatEntity.isUsed = false;
////                    oldSeatEntity.isClose = false;
//                    break;
////                case STATUS_CLOSE:      //???????????????
////                    oldSeatEntity.isUsed = false;
////                    oldSeatEntity.isClose = true;
////                    break;
//                case STATUS_USED:       //???????????????
//                    oldSeatEntity.isUsed = true;
////                    oldSeatEntity.isClose = false;
//                    break;
//                default:
//                    break;
//            }

            if (newSeatInfo.status == STATUS_UNUSED){
                oldSeatEntity.isUsed = false;//??????????????????
            } else if (newSeatInfo.status == STATUS_USED) {
                oldSeatEntity.isUsed = true; //???????????????
            }

            if (newSeatInfo.closeStatus == STATUS_OPEN){
                oldSeatEntity.isClose = false;
            } else if (newSeatInfo.closeStatus == STATUS_CLOSE) {
                oldSeatEntity.isClose = true;
            }

            //????????????????????????
            oldSeatEntity.isMute = newSeatInfo.mute;
        }
        mVoiceRoomSeatAdapter.notifyDataSetChanged();

        //?????????userId??????????????????????????????????????????  ???????????????????????????????????????userId?????????
        mTRTCVoiceRoom.getUserInfoList(userids, new TRTCVoiceRoomCallback.UserListCallback() {
            @Override
            public void onCallback(int code, String msg, List<TRTCVoiceRoomDef.UserInfo> list) {
                // ??????????????????userinfo
                Map<String, TRTCVoiceRoomDef.UserInfo> map = new HashMap<>();
                //??????userid ???????????????
                for (TRTCVoiceRoomDef.UserInfo userInfo : list) {
                    map.put(userInfo.userId, userInfo);
                }
                //??????????????????
                for (int i = 0; i < seatInfoList.size(); i++) {
                    //?????????????????????
                    TRTCVoiceRoomDef.SeatInfo newSeatInfo = seatInfoList.get(i);
                    //?????????????????????userid??????userInfo
                    TRTCVoiceRoomDef.UserInfo userInfo = map.get(newSeatInfo.userId);
                    if (userInfo == null) {
                        continue;
                    }
                    // ???????????????????????????????????????????????????????????????
                    if (i == 0) {

                        if (newSeatInfo.status == STATUS_USED) {
                            //???????????????
                            if (!TextUtils.isEmpty(userInfo.userAvatar)) {
                                Picasso.get().load(userInfo.userAvatar).into(mImgHead);
                            } else {
                                mImgHead.setImageResource(R.drawable.trtcvoiceroom_ic_head);
                            }
                            if (TextUtils.isEmpty(userInfo.userName)) {
                                mTvName.setText(userInfo.userId);
                            } else {
                                mTvName.setText(userInfo.userName);
                            }
                        } else {
                            mTvName.setText("???????????????");
                        }
                    } else {
                        // ?????????????????????????????????
                        VoiceRoomSeatEntity seatEntity = mVoiceRoomSeatEntityList.get(i - 1);
                        if (userInfo.userId.equals(seatEntity.userId)) {
                            seatEntity.userName = userInfo.userName;
                            seatEntity.userAvatar = userInfo.userAvatar;
                        }
                    }
                }
                mVoiceRoomSeatAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAnchorEnterSeat(int index, TRTCVoiceRoomDef.UserInfo user) {
        if (index != 0) {
            // ???????????????????????????
            showNotifyMsg(user.userName + "???" + index + "??????");
        }
    }

    @Override
    public void onAnchorLeaveSeat(int index, TRTCVoiceRoomDef.UserInfo user) {
        if (index != 0) {
            // ???????????????????????????
            showNotifyMsg(user.userName + "???" + index + "??????");
        }
    }

    @Override
    public void onSeatMute(int index, boolean isMute) {
        if (isMute) {
            showNotifyMsg(index + "???????????????");
        } else {
            showNotifyMsg(index + "??????????????????");
        }
    }

    @Override
    public void onSeatClose(int index, boolean isClose) {
        showNotifyMsg(isClose ? "????????????" + index + "??????" : "????????????" + index + "??????");
    }

    @Override
    public void onAudienceEnter(TRTCVoiceRoomDef.UserInfo userInfo) {
        showNotifyMsg(userInfo.userName + "??????");
    }

    @Override
    public void onAudienceExit(TRTCVoiceRoomDef.UserInfo userInfo) {
        showNotifyMsg(userInfo.userName + "??????");
    }

    @Override
    public void onUserVolumeUpdate(String userId, int volume) {

    }

    @Override
    public void onRecvRoomTextMsg(String message, TRTCVoiceRoomDef.UserInfo userInfo) {
        MsgEntity msgEntity = new MsgEntity();
        msgEntity.userId = userInfo.userId;
        msgEntity.userName = userInfo.userName;
        msgEntity.content = message;
        msgEntity.type = MsgEntity.TYPE_NORMAL;
        showImMsg(msgEntity);
    }

    @Override
    public void onRecvRoomCustomMsg(String cmd, String message, TRTCVoiceRoomDef.UserInfo userInfo) {

    }

    @Override
    public void onReceiveNewInvitation(String id, String inviter, String cmd, String content) {

    }

    @Override
    public void onInviteeAccepted(String id, String invitee) {

    }

    @Override
    public void onInviteeRejected(String id, String invitee) {

    }

    @Override
    public void onInvitationCancelled(String id, String invitee) {

    }

    @Override
    public void onAgreeClick(int position) {

    }

    protected int changeSeatIndexToModelIndex(int srcSeatIndex) {
        return srcSeatIndex + 1;
    }

    protected void showNotifyMsg(String msg) {
        MsgEntity msgEntity = new MsgEntity();
        msgEntity.type = MsgEntity.TYPE_NORMAL;
        msgEntity.content = msg;
        mMsgEntityList.add(msgEntity);
        mMsgListAdapter.notifyDataSetChanged();
        mRvImMsg.smoothScrollToPosition(mMsgListAdapter.getItemCount());
    }
}