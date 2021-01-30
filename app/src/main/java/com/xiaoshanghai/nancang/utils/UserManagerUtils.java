package com.xiaoshanghai.nancang.utils;

import androidx.annotation.ColorRes;

import com.xiaoshanghai.nancang.R;

public class UserManagerUtils {

    /**
     * 用户爵位名称
     *
     * @param noble
     * @return
     */
    public static String userNoble(int noble) {
        String nobleName = "";
        switch (noble) {
            case 1:
                nobleName = "男爵";
                break;
            case 2:
                nobleName = "子爵";
                break;
            case 3:
                nobleName = "伯爵";
                break;
            case 4:
                nobleName = "侯爵";
                break;
            case 5:
                nobleName = "公爵";
                break;
            case 6:
                nobleName = "国王";
                break;
            case 7:
                nobleName = "皇帝";
                break;
            default:
                break;
        }

        return nobleName;
    }

    /**
     * 用户爵位图标
     *
     * @param noble
     * @return
     */
    public static int nobleImage(int noble) {

        int nobleImage = 0;
        switch (noble) {
            case 1:
                nobleImage = R.mipmap.img_baron;
                break;
            case 2:
                nobleImage = R.mipmap.img_viscount;
                break;
            case 3:
                nobleImage = R.mipmap.img_earl;
                break;
            case 4:
                nobleImage = R.mipmap.img_marquis;
                break;
            case 5:
                nobleImage = R.mipmap.img_duke;
                break;
            case 6:
                nobleImage = R.mipmap.img_king;
                break;
            case 7:
                nobleImage = R.mipmap.img_emperor;
                break;
            default:
                nobleImage = 0;
                break;
        }
        return nobleImage;
    }

    /**
     * 权限判断
     *
     * @param selfNoble 当前权限
     * @param noble     所需权限
     * @return
     */
    public static boolean nobleAuthority(int selfNoble, int noble) {
        return selfNoble >= noble;
    }


    public static @ColorRes
    int getNobleMave(int noble) {
        int nobleImage = 0;
        switch (noble) {
            case 5:
                nobleImage = R.color.color_noble_5;
                break;
            case 6:
                nobleImage = R.color.color_noble_6;
                break;
            case 7:
                nobleImage = R.color.color_noble_7;
                break;
            default:
                nobleImage = R.color.color_noble_0;
                break;
        }
        return nobleImage;
    }

    public static int getNobleBg(String noble) {

        int nobleBg = 0;

        switch (noble) {

            case "0":
            case "1":
            case "2":
            case "3":
                nobleBg = R.drawable.shape_in_room_noble_0;
                break;
            case "4":
                nobleBg = R.drawable.shape_in_room_noble_4;
                break;
            case "5":
                nobleBg = R.drawable.shape_in_room_noble_5;
                break;
            case "6":
                nobleBg = R.drawable.shape_in_room_noble_6;
                break;
            case "7":
                nobleBg = R.drawable.shape_in_room_noble_7;
                break;
            default:
                nobleBg = R.drawable.shape_in_room_noble_0;
                break;

        }
        return nobleBg;
    }

    public static String getNobleStartColor(String noble) {

        String nobleColor = "000000";

        switch (noble) {

            case "0":
                nobleColor = "#BBBBBB";
                break;

            case "1":
                nobleColor = "#C6F1F1";
                break;

            case "2":
                nobleColor = "#FFD3A7";
                break;

            case "3":
                nobleColor = "#ED6C44";
                break;

            case "4":
                nobleColor = "#06B4FD";
                break;

            case "5":
                nobleColor = "#FF57CE";
                break;

            case "6":
                nobleColor = "#FFFF00";
                break;

            case "7":
                nobleColor = "#F00000";
                break;

            default:
                nobleColor = "000000";
                break;

        }
        return nobleColor;
    }

    public static String getNobleEndColor(String noble) {

        String nobleColor;

        switch (noble) {

            case "0":
                nobleColor = "#BBBBBB";
                break;

            case "1":
                nobleColor = "#C6F1F1";
                break;

            case "2":
                nobleColor = "#FFD3A7";
                break;

            case "3":
                nobleColor = "#FFB230";
                break;

            case "4":
                nobleColor = "#6CF3FF";
                break;

            case "5":
                nobleColor = "#B735F3";
                break;

            case "6":
                nobleColor = "#FF6E02";
                break;

            case "7":
                nobleColor = "#FC726D";
                break;

            default:
                nobleColor = "000000";
                break;

        }
        return nobleColor;
    }

    public static int userPage(String noble){
        int userPage;

        switch (noble) {

            case "1":
                userPage = R.mipmap.img_page_noble_1;
                break;

            case "2":
                userPage = R.mipmap.img_page_noble_2;
                break;

            case "3":
                userPage = R.mipmap.img_page_noble_3;
                break;

            case "4":
                userPage = R.mipmap.img_page_noble_4;
                break;

            case "5":
                userPage = R.mipmap.img_page_noble_5;
                break;

            case "6":
                userPage = R.mipmap.img_page_noble_6;
                break;

            case "7":
                userPage = R.mipmap.img_page_noble_7;
                break;

            default:
                userPage = 0;
                break;

        }
        return userPage;
    }

}
