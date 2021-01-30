package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.OnItemDeleteCallback;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;



public class MyPhotosAdapter extends BaseQuickAdapter<UserPicturesResult, MyPhotosAdapter.Holder> {

    private boolean isEdit = false;

    private OnItemDeleteCallback mCallback;

    public MyPhotosAdapter() {
        super(R.layout.adapter_my_photos);


    }

    public void setOnItemDeleteCallback(OnItemDeleteCallback callback) {
        this.mCallback = callback;
    }

    @Override
    protected void convert(@NotNull Holder holder, UserPicturesResult result) {
        String id = result.getId();
        if (id.equals("0")) {
            holder.mIvPhoto.setImageResource(R.mipmap.square_release_increase);
            holder.mIvDelete.setVisibility(View.GONE);
        } else {
            GlideAppUtil.loadImage(getContext(), result.getUserPicture(), holder.mIvPhoto);
            holder.mIvDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        }

        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null) {
                    mCallback.onItemDelete(getItemPosition(result));
                }
            }
        });

    }



    public void setEdit() {
        isEdit = !isEdit;
        notifyDataSetChanged();
    }


    static class Holder extends BaseViewHolder {

        private ImageView mIvPhoto;
        private ImageView mIvDelete;

        public Holder(@NotNull View view) {
            super(view);
            mIvPhoto = view.findViewById(R.id.iv_photo);
            mIvDelete = view.findViewById(R.id.iv_delete);
        }
    }
}
