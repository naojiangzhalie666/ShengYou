package com.xiaoshanghai.nancang.mvp.ui.fragment.studio;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseFragment;
import com.xiaoshanghai.nancang.callback.OnFaceFragmentClick;
import com.xiaoshanghai.nancang.helper.RoomFaceManager;
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomFaceAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomFaceVPAdapter;
import com.xiaoshanghai.nancang.net.bean.RoomFace;
import com.tencent.qcloud.tim.uikit.component.face.EmojiIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RoomFaceFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    EmojiIndicatorView mIndicator;

    private int rows = 3;       //行
    private int columns = 5;    //列

    private ArrayList<View> ViewPagerItems = new ArrayList<>();

    private OnFaceFragmentClick mClick;

    public static RoomFaceFragment newInstance() {
        return new RoomFaceFragment();
    }

    public void setOnClick(OnFaceFragmentClick onClick) {
        this.mClick = onClick;
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_room_face;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        ArrayList<RoomFace> roomFace = (ArrayList<RoomFace>) RoomFaceManager.Companion.getRoomNewFace();
        initPage(roomFace, 5, 3);
    }


    private void initPage(ArrayList<RoomFace> faces, int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        intiIndicator(faces);

        ViewPagerItems.clear();

        int pageCont = getPagerCount(faces);

        for (int i = 0; i < pageCont; i++) {
            ViewPagerItems.add(getViewPagerItem(i, faces));
        }

        RoomFaceVPAdapter mVpAdapter = new RoomFaceVPAdapter(ViewPagerItems);
        mViewPager.setAdapter(mVpAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.playBy(oldPosition, position);
                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void intiIndicator(ArrayList<RoomFace> list) {
        mIndicator.init(getPagerCount(list));
    }

    private View getViewPagerItem(int position, ArrayList<RoomFace> list) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.layout_room_face_grid, null);//表情布局

        GridView gridview = layout.findViewById(R.id.chart_face_gv);

        final List<RoomFace> subList = new ArrayList<>();
        subList.addAll(list.subList(position * (columns * rows), (columns * rows) * (position + 1) > list.size() ? list.size() : (columns * rows) * (position + 1)));

        RoomFaceAdapter mGvAdapter = new RoomFaceAdapter(subList, getActivity());

        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);


        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoomFace roomFace = subList.get(position);
                if (mClick != null) {
                    mClick.onClickItemResult(roomFace);
                }
            }
        });

        return gridview;
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */
    private int getPagerCount(List<RoomFace> faces) {
        int count = faces.size();
        return count % (columns * rows) == 0 ? count / (columns * rows) : count / (columns * rows) + 1;
    }

}
