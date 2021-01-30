package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.studio.StudioAct;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.QueryRoomEntity;
import com.xiaoshanghai.nancang.net.bean.RoomEtity;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.DialogRoomLockView;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.trtc.TRTCCloudDef;

public class EnterRoomHelp {


    /**
     * 点击首页右上角创建或是进入房间
     *
     * @param context
     * @param view
     */
    public static void createRoom(Context context, BaseView view) {
        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(context);

        HttpClient.getApi().queryRoom()
                .execOnThread(view.getActLifeRecycle(), new HttpObserver<QueryRoomEntity>() {

                    @Override
                    protected void success(QueryRoomEntity bean, BaseResponse<QueryRoomEntity> response) {

                        if (bean != null) {
                            Integer status = bean.getStatus();
                            if (status == 0) {
                                if (!TextUtils.isEmpty(MainActivity.mRoomId)) {

                                    trtcVoiceRoom.exitRoom((code, msg) -> {
                                        if (code == 0||code == 10009) {
                                            StudioAct.Companion.createRoom(context, SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, false);
                                        }
                                    });

                                } else {

                                    StudioAct.Companion.createRoom(context, SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, false);
                                }

                            } else if (status == 1) {
                                RoomEtity room = bean.getRoom();
                                if (room != null && room.getRoomStatus() == 0) {

                                    if (!TextUtils.isEmpty(MainActivity.mRoomId)) {
                                        trtcVoiceRoom.exitRoom((code, msg) -> {
                                            if (code == 0||code == 10009) {
                                                StudioAct.Companion.createRoom(context, SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, false);
                                            }
                                        });
                                    } else {
                                        StudioAct.Companion.createRoom(context, SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, false);
                                    }

                                } else if (room.getRoomStatus() == 1) {

                                    if (!TextUtils.isEmpty(MainActivity.mRoomId)) {
                                        if (MainActivity.mRoomId.equals(room.getId())) {
                                            StudioAct.Companion.enterRoom(context, bean.getRoom().getId(), SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, bean.getRoom().getRoomName(), true);
                                        } else {
                                            trtcVoiceRoom.exitRoom((code, msg) -> {
                                                if (code == 0||code == 10009) {
                                                    StudioAct.Companion.enterRoom(context, bean.getRoom().getId(), SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, bean.getRoom().getRoomName(), false);
                                                }
                                            });
                                        }

                                    } else {

                                        StudioAct.Companion.enterRoom(context, bean.getRoom().getId(), SPUtils.getInstance().getUserInfo().getId(), TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, bean.getRoom().getRoomName(), false);
                                    }

                                }
                            }
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
    }


    /**
     * 通过房间ID 查询房间信息
     *
     * @param context context
     * @param view    BaseView
     * @param roomId  房间ID
     */
    public static void enterRoomControl(Context context, BaseView view, String roomId) {
        HttpClient.getApi().queryEnterRoom(roomId)
                .execOnThread(view.getActLifeRecycle(), new HttpObserver<RoomEtity>() {

                    @Override
                    protected void success(RoomEtity bean, BaseResponse<RoomEtity> response) {
                        if (bean != null) {
                            enterRoom(context, bean.getId(), SPUtils.getInstance().getUserInfo().getId(),
                                    TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC, bean.getRoomName(),
                                    bean.getRoomLock() == 1, bean.getCreateUserId(), bean.getRoomPassword());
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
    }

    /**
     * 进入房间
     *
     * @param context      context
     * @param roomId       房间id
     * @param enterUserId  进房用户ID
     * @param audioQuality 音质
     * @param roomName     房间名
     * @param isLock       房间是否上锁
     * @param landlordId   房东ID
     * @param password     房间密码
     */
    public static void enterRoom(Context context, String roomId, String enterUserId, int audioQuality, String roomName, boolean isLock, String landlordId, String password) {
        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(context);
        //是房东 直接进入房间
        if (enterUserId.equals(landlordId)) {

            if (!TextUtils.isEmpty(MainActivity.mRoomId)) {
                if (MainActivity.mRoomId.equals(roomId)) {
                    StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, true);
                } else {
                    trtcVoiceRoom.exitRoom((code, msg) -> {
                        if (code == 0||code == 10009) {
                            StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);
                        }
                    });
                }

            } else {
                StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);
            }

        } else {//不是房东
            //房间是否有锁
            if (isLock && !TextUtils.isEmpty(password)) { //有锁


                if (!TextUtils.isEmpty(MainActivity.mRoomId)) {
                    if (MainActivity.mRoomId.equals(roomId)) {
                        StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, true);
                    } else {

                        final DialogRoomLockView dialogRoomLockView = new DialogRoomLockView(context);
                        TipsDialog.createDialog(context, dialogRoomLockView)

                                .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                    dialog.dismiss();
                                })
                                .bindClick(R.id.tv_confirm, (v, dialog) -> {

                                    EditText etPsw = dialogRoomLockView.getView(R.id.et_psw);
                                    String psw = etPsw.getText().toString().trim();
                                    if (psw.equals(password)) {

                                        trtcVoiceRoom.exitRoom((code, msg) -> {
                                            if (code == 0||code == 10009) {
                                                StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);
                                            }
                                        });

                                        dialog.dismiss();
                                    }

                                })
                                .show();
                    }

                } else {

                    final DialogRoomLockView dialogRoomLockView = new DialogRoomLockView(context);
                    TipsDialog.createDialog(context, dialogRoomLockView)

                            .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                dialog.dismiss();
                            })
                            .bindClick(R.id.tv_confirm, (v, dialog) -> {

                                EditText etPsw = dialogRoomLockView.getView(R.id.et_psw);
                                String psw = etPsw.getText().toString().trim();
                                if (psw.equals(password)) {

                                    StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);

                                    dialog.dismiss();
                                }

                            })
                            .show();

                }


            } else {      //没有锁
                if (!TextUtils.isEmpty(MainActivity.mRoomId)) {
                    if (MainActivity.mRoomId.equals(roomId)) {
                        StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, true);
                    } else {
                        trtcVoiceRoom.exitRoom((code, msg) -> {
                            if (code == 0||code == 10009) {
                                StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);
                            }
                        });
                    }

                } else {
                    StudioAct.Companion.enterRoom(context, roomId, enterUserId, audioQuality, roomName, false);
                }
            }

        }
    }


}
