package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.CacheConstant;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.MaterialContract;
import com.xiaoshanghai.nancang.mvp.presenter.MaterialPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyPhotosAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.FamilyMemberAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HorizontalPhotoAdapter;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class MaterialFragment extends BaseMvpFragment<MaterialPresenter> implements MaterialContract.View {


    @BindView(R.id.rcy_view)
    RecyclerView rcyView;
    @BindView(R.id.tv_photo_not)
    TextView tvPhotoNot;
    @BindView(R.id.tv_introduction)
    TextView mTvIntroduction;
    @BindView(R.id.iv_family_logo)
    CircleImageView ivFamilyLogo;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.tv_family_id)
    TextView tvFamilyId;
    @BindView(R.id.tv_family_member)
    TextView tvFamilyMember;
    @BindView(R.id.ll_family)
    LinearLayout llFamily;

    private HorizontalPhotoAdapter mAdapter;

    private List<UserPicturesResult> myPictures = new ArrayList<>();

    private MineReslut mResult;

    private MyFamilyResult mFamilyResult;

    private boolean isSelf = false;

    public static MaterialFragment newInstance() {
        return new MaterialFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_material;
    }

    @Override
    protected MaterialPresenter createPresenter() {
        return new MaterialPresenter();
    }

    private void initData() {
        mAdapter = new HorizontalPhotoAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rcyView.setLayoutManager(linearLayoutManager);

        rcyView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserPicturesResult item = (UserPicturesResult) adapter.getItem(position);
                if (!item.getId().equals("0")) {
                    ArrayList<String> photo = new ArrayList<>();

                    for (UserPicturesResult userPicturesResult : myPictures) {
                        String id = userPicturesResult.getId();
                        if (!id.equals("0")) {
                            photo.add(userPicturesResult.getUserPicture());
                        }
                    }

                    if (isSelf) {
                        photoPreviewWrapper((position - 1), photo);
                    } else {
                        photoPreviewWrapper((position), photo);
                    }
                } else {
                    Bundle photoBundle = new Bundle();
                    if (isSelf) {
                        photoBundle.putSerializable(Constant.MINE_RESLUT, mResult);
                    } else {
                        photoBundle.putSerializable(Constant.MINE_RESLUT, CacheConstant.result);
                    }
                    ActStartUtils.startForAct(MaterialFragment.this.getActivity(), MyPhotosAct.class, photoBundle, Constant.MY_PHOTOS);
                }
            }
        });
    }

    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getActivity())
                .saveImgDir(null); // 保存图片的目录，如果传 null，则没有保存图片功能

        if (imageInfo.size() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // 当前预览图片的索引
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        initData();

        Bundle arguments = getArguments();

        mResult = (MineReslut) arguments.getSerializable(Constant.MINE_RESLUT);

        String id = mResult.getId();
        if (id.equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
        } else {
            isSelf = false;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isSelf) {
            setMineResuylt(mResult);
        } else {
            setMineResuylt(CacheConstant.result);
        }
    }

    private void setMineResuylt(MineReslut result) {

        List<UserPicturesResult> userPictures = result.getUserPictures();
        photoAdd(userPictures);
        if (myPictures.size() <= 0) {
            rcyView.setVisibility(View.GONE);
            tvPhotoNot.setVisibility(View.VISIBLE);
        } else {
            tvPhotoNot.setVisibility(View.GONE);
            rcyView.setVisibility(View.VISIBLE);
        }



        mAdapter.setList(myPictures);

        String userIntroduce = result.getUserIntroduce();
        if (TextUtils.isEmpty(userIntroduce)) {
            mTvIntroduction.setText(getResources().getString(R.string.not_introduction));
        } else {
            mTvIntroduction.setText(userIntroduce);
        }

        mPresenter.getMyFamily(result.getId());

    }

    private void photoAdd(List<UserPicturesResult> userPictures) {
        myPictures.clear();

        if (isSelf) {
            UserPicturesResult userPicturesResult = initList();
            myPictures.add(userPicturesResult);
        }

        myPictures.addAll(userPictures);
    }

    private UserPicturesResult initList() {
        UserPicturesResult bean = new UserPicturesResult();
        bean.setId("0");
        return bean;

    }


    @Override
    public void onSuccess(MyFamilyResult familyResult) {
        if (familyResult == null) {
            llFamily.setVisibility(View.GONE);
        } else {
            mFamilyResult = familyResult;
            GlideAppUtil.loadImage(getActivity(), familyResult.getClanPicture(), ivFamilyLogo);
            tvFamilyName.setText(familyResult.getClanName());
            tvFamilyId.setText(String.valueOf(familyResult.getClanNumber()));
            tvFamilyMember.setText(String.valueOf(familyResult.getMember()));
        }
    }

    @Override
    public void onError(String msg) {
        llFamily.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_family})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_family:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.FAMILY_ID, mFamilyResult.getClanId());
                bundle.putSerializable(Constant.MY_FAMILY, mFamilyResult);
                ActStartUtils.startAct(getActivity(), FamilyMemberAct.class, bundle);
                break;
        }
    }
}
