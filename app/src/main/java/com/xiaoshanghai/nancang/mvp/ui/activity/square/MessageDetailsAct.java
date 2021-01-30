package com.xiaoshanghai.nancang.mvp.ui.activity.square;


import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.OnItemClickCallback;
import com.xiaoshanghai.nancang.callback.OnSecondCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.MessageDetailsContract;
import com.xiaoshanghai.nancang.mvp.presenter.MessageDetailsPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MessageDetailsAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.MessageDetailsHeaderView;
import com.xiaoshanghai.nancang.net.bean.CommentResult;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.KeyboardUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.InputTextMsgDialog;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class MessageDetailsAct extends BaseMvpActivity<MessageDetailsPresenter> implements MessageDetailsContract.View, OnRefreshLoadMoreListener, OnSecondCallback, OnItemClickCallback, InputTextMsgDialog.OnTextSendListener {

    private static final String TAG = "MessageDetailsAct";
    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rc_comment)
    RecyclerView rcComment;
    @BindView(R.id.rl_fabulous)
    RelativeLayout rlFabulous;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLLComment;
    @BindView(R.id.ll_comment_2)
    LinearLayout mLLComment2;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.tv_fabulous)
    TextView mTvFabulous;
    @BindView(R.id.tv_send)
    TextView mTvSend;
//    @BindView(R.id.view_emoji)
//    View mEmoji;

    private String mTopicId;

    private FriendsCircleResult mResult;

    private MessageDetailsAdapter mAdapter;

    private MessageDetailsHeaderView headerView;

    private boolean mHasFocus;

    private String commentType;

    private String fatherId = "";

    private int position = -1;

    private InputTextMsgDialog mInputTextMsgDialog;

    @Override
    public int setLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected MessageDetailsPresenter createPresenter() {
        return new MessageDetailsPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mInputTextMsgDialog = new InputTextMsgDialog(this, R.style.TRTCVoiceRoomInputDialog);
        mInputTextMsgDialog.setmOnTextSendListener(this);
        refresh.setOnRefreshLoadMoreListener(this);
        initData();
        initFocus();

    }

    @OnTextChanged(R.id.et_comment)
    void onTextChang(Editable editable) {
        mTvSend.setEnabled(mEtComment.length() > 0);
    }

    private void initFocus() {

//        View decorView = getWindow().getDecorView();
//        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));

        mEtComment.setOnFocusChangeListener((v, hasFocus) -> {
            mHasFocus = hasFocus;
            if (!hasFocus) {
                // 失去焦点时的处理内容
                mLLComment2.setVisibility(View.GONE);
                mLLComment.setVisibility(View.VISIBLE);
                KeyboardUtils.hideKeyboard(mEtComment);

            } else {
                // 得到焦点时的处理内容
                mLLComment2.setVisibility(View.VISIBLE);
                mLLComment.setVisibility(View.GONE);
                KeyboardUtils.showKeyboard(mEtComment);
            }
        });

    }

    private void initAdapter() {

        mAdapter = new MessageDetailsAdapter(this);
        rcComment.setLayoutManager(new LinearLayoutManager(this));
        rcComment.setAdapter(mAdapter);

        headerView = new MessageDetailsHeaderView(this);
        headerView.setCallback(this);

        mAdapter.setHeaderView(headerView);

        headerView.upData(mResult);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();

        FriendsCircleResult result = (FriendsCircleResult) extras.getSerializable(Constant.FRIEND_CIRCLE_RESULT);


        if (result == null) {
            mTopicId = extras.getString(Constant.TOPIC_ID);
        } else {
            mTopicId = result.getId();
        }

        mPresenter.getFriend(mTopicId);


    }

//    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
//        return () -> {
//            Rect r = new Rect();
//            decorView.getWindowVisibleDisplayFrame(r);
//
//            int naivHeight = NavigationBarUtils.getNavigationBarHeightIfRoom(MessageDetailsAct.this);
//
//            int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels + ScreenUtils.px2dp(MessageDetailsAct.this, naivHeight);
//            int diff = height - r.bottom;
//
//            if (diff != 0) {
//                if (contentView.getPaddingBottom() != diff) {
//                    contentView.setPadding(0, 0, 0, diff);
////                    if (diff > 0)
////                        mEmoji.setVisibility(View.GONE);
//                }
//
//            } else {
//                if (contentView.getPaddingBottom() != 0) {
//                    contentView.setPadding(0, 0, 0, 0);
//                }
//            }
//        };
//    }

    @OnClick({R.id.iv_black, R.id.rl_fabulous, R.id.rl_comment, R.id.rl_share, R.id.tv_send, R.id.iv_emoji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.rl_fabulous:
                if (mResult == null) return;
                int hasLike = mResult.getHasLike();//是否点赞
                int likeNumber = mResult.getLikeNumber();//点赞数
                if (hasLike == 1) {
                    mResult.setHasLike(0);
                    mResult.setLikeNumber(likeNumber - 1);
                } else if (hasLike == 0) {
                    mResult.setHasLike(1);
                    mResult.setLikeNumber(likeNumber + 1);
                }

                setFabulous(mTvFabulous, mResult.getHasLike(), mResult.getLikeNumber());

                mPresenter.fabulous(mResult.getId());

                break;
            case R.id.rl_comment:
                commentType = "1";
                position = -1;
//                mEtComment.setFocusable(true);
//                mEtComment.setFocusableInTouchMode(true);
//                mEtComment.requestFocus();

                showInputMsgDialog("");

                break;
            case R.id.rl_share:

                if (mResult == null) return;

                TipsDialog.createDialog(this, R.layout.dialog_share)
                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_share_buddy, (v, dialog) -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.BUDDY_KEY, Constant.TOPIC);
                            bundle.putSerializable(Constant.TOPIC, mResult);
                            ActStartUtils.startAct(this, MyBuddyAct.class, bundle);
                        })
                        .show();
                break;

            case R.id.tv_send:
                if (mResult != null) {
                    mPresenter.comment(mResult.getId(), mEtComment.getText().toString().trim(), commentType, fatherId, position);
                    mEtComment.clearFocus();
                }
                break;
            case R.id.iv_emoji:
//                mEmoji.setVisibility(View.VISIBLE);
//                KeyboardUtils.hideKeyboard(mEmoji);
                break;
        }
    }

    /**
     * 显示输入Dialog
     */
    private void showInputMsgDialog(String userName) {

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputTextMsgDialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        mInputTextMsgDialog.getWindow().setAttributes(lp);
        mInputTextMsgDialog.setCancelable(true);
        mInputTextMsgDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mInputTextMsgDialog.setAtFlat(userName);
        mInputTextMsgDialog.show();
    }

    /**
     * 点赞设置
     *
     * @param fabulous   点赞控件
     * @param hasLike    是否点赞 1 是 0 否
     * @param likeNumber 点赞数
     */
    public void setFabulous(TextView fabulous, int hasLike, int likeNumber) {
        fabulous.setEnabled(hasLike == 0 ? false : true);
        fabulous.setText(String.valueOf(likeNumber));
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mResult != null) {
            mPresenter.mPage = mPresenter.initPage;
            mPresenter.getComment(refreshLayout, mResult != null ? mResult.getId() : "");
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mResult != null) {
            mPresenter.getComment(refreshLayout, mResult != null ? mResult.getId() : "");
        }
    }


    @Override
    public void onFriendSuccess(FriendsCircleResult result) {
        mResult = result;

        if (mResult == null) {
            return;
        }

        setFabulous(mTvFabulous, mResult.getHasLike(), mResult.getLikeNumber());

        initAdapter();

        if (mResult != null) {
            refresh.autoRefresh();
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, List<CommentResult> records, String num) {
        if (refreshLayout != null)
            refreshLayout.finishRefresh();
        headerView.setCommentNum(num);
        mAdapter.setList(records);
        if (records.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.resetNoMoreData();
        }
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, List<CommentResult> records) {
        mAdapter.addData(records);

        if (records.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void fabulousSuccess(String msg) {

    }

    @Override
    public void commentSuccess(String msg) {
        ToastUtils.showShort(msg);
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getComment(null, mResult != null ? mResult.getId() : "");
        mEtComment.setText("");

    }

    @Override
    public void secondCommentSuccess(String commentId, int position) {
        mPresenter.getSecondComment(commentId, position);
    }

    @Override
    public void secondSuccess(CommentResult result, int position) {
        mAdapter.setData(position, result);
        mAdapter.notifyDataSetChanged();

        mEtComment.setHint("");
        mEtComment.setText("");
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mHasFocus && event.getKeyCode() == 4) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEtComment.clearFocus();
                }
            }, 200);

        }
        return super.dispatchKeyEvent(event);

    }

    @Override
    public void onSecondClick(CommentResult result, int position) {

        commentType = "2";
//        mEtComment.setFocusable(true);
//        mEtComment.setFocusableInTouchMode(true);
//        mEtComment.requestFocus();
//
//        mEtComment.setHint("回复 " + result.getUserName());

        fatherId = result.getId();

        this.position = position;

        showInputMsgDialog(result.getUserName());


    }

    private void more(FriendsCircleResult result) {

        TipsDialog.createDialog(this, R.layout.dialog_select_camera)
                .setTextColor(R.id.tv_camera, R.color.color_black)
                .setText(R.id.tv_camera, "举报")
                .setTextColor(R.id.tv_photo, R.color.color_aaaaaa)
                .setText(R.id.tv_photo, "取消")
                .bindClick(R.id.tv_camera, (v, dialog) -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_USER);
                    bundle.putString(Constant.REPORT_ID, result.getUserId());
                    ActStartUtils.startAct(this, ReportAct.class, bundle);
                    dialog.dismiss();
                })
                .bindClick(R.id.tv_photo, (v, dialog) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public <T> void onClickItem(T result) {
        more((FriendsCircleResult) result);
    }

    @Override
    public void onTextSend(String msg) {
        if (mResult != null) {
            mPresenter.comment(mResult.getId(), msg, commentType, fatherId, position);
            mEtComment.clearFocus();
        }
    }
}
