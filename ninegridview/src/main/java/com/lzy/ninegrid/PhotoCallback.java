package com.lzy.ninegrid;

import com.lzy.ninegrid.ImageInfo;

import java.util.List;

public interface PhotoCallback {
    void onItemPhotoClick(int index, List<ImageInfo> imageInfo);
}
