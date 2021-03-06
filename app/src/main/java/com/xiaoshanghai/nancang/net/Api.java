package com.xiaoshanghai.nancang.net;

import com.xiaoshanghai.nancang.bean.GetAppMinetBaseDataBean;
import com.xiaoshanghai.nancang.bean.GetfaceModleBean;
import com.xiaoshanghai.nancang.bean.getBaiDUTokenBean;
import com.xiaoshanghai.nancang.net.bean.AvatarResult;
import com.xiaoshanghai.nancang.net.bean.AwardsEntity;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.net.bean.BlackListMineBean;
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.CheckVersionBean;
import com.xiaoshanghai.nancang.net.bean.ChiliIncomeResult;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.CommentResult;
import com.xiaoshanghai.nancang.net.bean.CreateRoomResult;
import com.xiaoshanghai.nancang.net.bean.CustomerServiceEntity;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.GetAppPayBuyTicketBean;
import com.xiaoshanghai.nancang.net.bean.GiftAllResult;
import com.xiaoshanghai.nancang.net.bean.GiftRecordResult;
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult;
import com.xiaoshanghai.nancang.net.bean.GoldResult;
import com.xiaoshanghai.nancang.net.bean.GradeResult;
import com.xiaoshanghai.nancang.net.bean.HalfRoomRankResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;
import com.xiaoshanghai.nancang.net.bean.IncomeResult;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.xiaoshanghai.nancang.net.bean.LotteryPirzeResult;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;
import com.xiaoshanghai.nancang.net.bean.NobleResult;
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult;
import com.xiaoshanghai.nancang.net.bean.PrizeResult;
import com.xiaoshanghai.nancang.net.bean.QueryRoomEntity;
import com.xiaoshanghai.nancang.net.bean.RecordResult;
import com.xiaoshanghai.nancang.net.bean.ReportType;
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity;
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity;
import com.xiaoshanghai.nancang.net.bean.RoomEtity;
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult;
import com.xiaoshanghai.nancang.net.bean.RoomManagerEntity;
import com.xiaoshanghai.nancang.net.bean.RoomResult;
import com.xiaoshanghai.nancang.net.bean.RowSeatResult;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;
import com.xiaoshanghai.nancang.net.bean.SignDayEntity;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;
import com.xiaoshanghai.nancang.net.bean.SystemNotic;
import com.xiaoshanghai.nancang.net.bean.TopicMsg;
import com.xiaoshanghai.nancang.net.bean.UserIncome;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;
import com.xiaoshanghai.nancang.net.bean.UserRankResult;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api {

    /**
     * ???????????? - ???????????????
     *
     * @param phone
     * @return
     */
    @POST("app_auth/login_phone")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> oneKeyLogin(@Field("phone") String phone);

    /**
     * ???????????? - ???????????????
     *
     * @param userId
     * @return
     */
    @POST("app_messaging/usersig")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getUserSig(@Field("userId") String userId);

    /**
     * ?????? - ??????????????????????????????
     *
     * @param phone
     * @return
     */
    @POST("app_auth/get_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getStatusCode(@Field("phone") String phone);

    /**
     * ?????? - ?????????????????????????????????
     *
     * @param phone
     * @param code
     * @return
     */
    @POST("app_auth/login_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> loginCode(@Field("phone") String phone, @Field("code") String code, @Field("city") String city, @Field("latitude") String latitude, @Field("longitude") String longitude);

    /**
     * ?????? - ??????????????????????????????
     *
     * @param phone
     * @param password
     * @return
     */
    @POST("app_auth/login_pw")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> loginPsd(@Field("phone") String phone, @Field("password") String password, @Field("city") String city, @Field("latitude") String latitude, @Field("longitude") String longitude);

    /**
     * ?????? - ???????????????????????????
     *
     * @param phone
     * @param code
     * @return
     */
    @POST("app_auth/check_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> checkCode(@Field("phone") String phone, @Field("code") String code);

    /**
     * ??????
     *
     * @param file         ??????
     * @param userName     ????????????
     * @param userPhone    ???????????????
     * @param userBirthday ????????????
     * @param userSex      ????????????
     * @param wechatOpenid ???????????????
     * @return
     */
    @POST("app_auth/register")
    @Multipart
    HttpObservable<BaseResponse<LogonResult>> register(@Part MultipartBody.Part file,
                                                       @Part("userName") String userName,
                                                       @Part("userPhone") String userPhone,
                                                       @Part("userBirthday") String userBirthday,
                                                       @Part("userSex") String userSex,
                                                       @Part("wechatOpenid") String wechatOpenid
    , @Part("city") String city, @Part("latitude") String latitude, @Part("longitude") String longitude)
    ;

    /**
     * ??????banner???
     *
     * @return
     */
    @POST("app_banner/query")
    HttpObservable<BaseResponse<List<BannerResult>>> getBanner();

    /**
     * ????????????
     *
     * @return
     */
    @POST("app_room/type")
    HttpObservable<BaseResponse<List<HomeSortResult>>> getHomeSort();

    /**
     * ????????????????????????????????????
     *
     * @param current
     * @param size
     * @param typeId
     * @return
     */
    @POST("app_room/query_by_type")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<RoomResult>>>> getRooms(@Field("current") String current,
                                                                            @Field("size") String size,
                                                                            @Field("typeId") String typeId);

    /**
     * ???????????????
     *
     * @param topicContent
     * @param params
     * @return
     */
    @POST("app_topic/publish")
    @Multipart
    HttpObservable<BaseResponse<String>> releaseFriends(@Part("topicContent") String topicContent,
                                                        @PartMap Map<String, RequestBody> params);

    /**
     * ?????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_topic/query_all")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircleCity(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("userId") String userId,
                                                                                                 @Field("city") String city,
                                                                                                 @Field("userSex") String userSex);

    /**
     * ???????????????????????????records???????????????>0????????????????????????
     * ??????????????????????????????????????????????????????
     */
    @POST("app_topic/query_all")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getAppTopic(@Field("current") String current,
                                                                                                 @Field("size") String size,
                                                                                                 @Field("userId") String userId);
    /**
     * ?????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_topic/query_all")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("userId") String userId,
                                                                                             @Field("userSex") String userSex);
    /**
     * ?????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_topic/query_all")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("userId") String userId);
    /**
     * ?????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_topic/query_follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getAttention(@Field("current") String current,
                                                                                         @Field("size") String size);

    /**
     * ?????????????????????
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/query_one")
    @FormUrlEncoded
    HttpObservable<BaseResponse<FriendsCircleResult>> getFriendOne(@Field("topicId") String topicId);

    /**
     * ??????
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/do_like")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> fabulous(@Field("topicId") String topicId);

    /**
     * ???????????????????????????
     *
     * @param current
     * @param size
     * @param topicId
     * @return
     */
    @POST("app_topic/query_comment")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<CommentResult>>>> getComment(@Field("current") String current,
                                                                                 @Field("size") String size,
                                                                                 @Field("topicId") String topicId);

    /**
     * ??????????????? ???????????????????????????
     *
     * @param topicId        ?????????id
     * @param commentContent ??????????????????
     * @param commentType    ??????????????? 1. ???????????? 2.???????????? ??????????????????????????????????????????id
     * @param fatherId       ????????????id ???????????????????????????????????????
     * @return
     */
    @POST("app_topic/add_comment")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> comment(@Field("topicId") String topicId,
                                                 @Field("commentContent") String commentContent,
                                                 @Field("commentType") String commentType,
                                                 @Field("fatherId") String fatherId);


    /**
     * ??????????????????
     *
     * @param commentId
     * @return
     */
    @POST("app_topic/query_comment_id")
    @FormUrlEncoded
    HttpObservable<BaseResponse<CommentResult>> getSecondComment(@Field("commentId") String commentId);

    /**
     * ??????userid??????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_clan/query_myclan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(@Field("userId") String userId);

    /**
     * ?????????????????????
     *
     * @return
     */
    @POST("app_record/recommend")
    HttpObservable<BaseResponse<List<StartRecommendResult>>> getStartRecommend();

    /**
     * ???????????????????????????
     *
     * @return
     */
    @POST("app_record/leaderboard")
    HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank();

    /**
     * ???????????????????????????id??????????????????
     *
     * @param clanId
     * @return
     */
    @POST("app_clan/query_claninfo")
    @FormUrlEncoded
    HttpObservable<BaseResponse<ClanResult>> getClanInfo(@Field("clanId") String clanId);

    /**
     * ????????????ID ??????????????????
     *
     * @param clanId
     * @return
     */
    @POST("app_clanmember/clanmembers")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<FamilyMemberResult>>> familyMembers(@Field("clanId") String clanId);


    /**
     * ??????????????????????????????????????????
     *
     * @param userId ??????ID
     * @return
     */
    @POST("app_clanapply/status")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getFamilyApplyStatu(@Field("userId") String userId);


    /**
     * ????????????
     *
     * @param clanName     ????????????
     * @param applicantId  ?????????id
     * @param applyComment ??????
     * @param file         ??????????????????
     * @return
     */
    @POST("app_clanapply/createclan")
    @Multipart
    HttpObservable<BaseResponse<String>> createFamily(@Part("clanName") String clanName,
                                                      @Part("applicantId") String applicantId,
                                                      @Part("applyComment") String applyComment,
                                                      @Part MultipartBody.Part file);

    /**
     * ????????????????????????
     *
     * @param userId
     * @param begin
     * @param size
     * @return
     */
    @POST("app_clanapply/applylist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<RecordResult>>>> getapplyList(@Field("userId") String userId,
                                                                                  @Field("begin") String begin,
                                                                                  @Field("size") String size);

    /**
     * ????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_mine/base_data")
    @FormUrlEncoded
    HttpObservable<BaseResponse<MineReslut>> getMine(@Field("userId") String userId);

    /**
     * ????????????
     *
     * @param userId
     * @param clanId
     * @return
     */
    @POST("app_clan/add_clan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> joinFamily(@Field("userId") String userId, @Field("clanId") String clanId);

    /**
     * ????????????
     *
     * @param userId
     * @param clanId
     * @return
     */
    @POST("app_clan/quit_clan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> outFamily(@Field("userId") String userId, @Field("clanId") String clanId);

    /**
     * ????????????
     *
     * @param file
     * @return
     */
    @POST("app_mine/update_portrait")
    @Multipart
    HttpObservable<BaseResponse<AvatarResult>> upAvatar(@Part MultipartBody.Part file);

    /**
     * ????????????
     *
     * @param birthday
     * @return
     */
    @POST("app_mine/update_birthday")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editBirthday(@Field("birthday") String birthday);

    /**
     * ????????????
     *
     * @param name
     * @return
     */
    @POST("app_mine/update_name")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editNickName(@Field("name") String name);

    /**
     * ????????????
     *
     * @param introduce
     * @return
     */
    @POST("app_mine/update_introduce")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editIntroduce(@Field("introduce") String introduce);

    /**
     * ????????????????????????????????
     *
     * @param file
     * @return
     */
    @POST("app_mine/add_picture")
    @Multipart
    HttpObservable<BaseResponse<String>> addPicture(@Part MultipartBody.Part file);

    /**
     * ????????????????????????????????
     *
     * @param id
     * @return
     */
    @POST("app_mine/del_picture")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> deletePhoto(@Field("id") String id);

    /**
     * ????????????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_mine/query_picture")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<UserPicturesResult>>> getMyPhotos(@Field("userId") String userId);


    /**
     * ???????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_fan/if_follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> queryFollow(@Field("userId") String userId);

    /**
     * ??????&????????????
     * ?????????????????????
     *
     * @param userId
     * @param status
     * @return
     */
    @POST("app_fan/follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> follow(@Field("userId") String userId, @Field("status") String status);

    /**
     * ????????????
     *
     * @param userId
     * @return
     */
    @POST("app_fan/unfollow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> unfollow(@Field("userId") String userId);

    /**
     * ????????????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_gift/get_history")
    @FormUrlEncoded
    HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(@Field("userId") String userId);

    /**
     * ??????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_deck/query_car")
    @FormUrlEncoded
    HttpObservable<BaseResponse<DeckResult>> carRecord(@Field("userId") String userId);

    /**
     * ?????????????????????????????/?????????
     *
     * @param deckType
     * @return
     */
    @POST("app_deck/query_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<Decks>>> store(@Field("deckType") Integer deckType);

    /**
     * ????????????/??????
     *
     * @param deckId
     * @param userId
     * @return
     */
    @POST("app_deck/buy_deck")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> buyDeck(@Field("deckId") String deckId,
                                                  @Field("userId") String userId);


    /**
     * ????????????????????????
     *
     * @return
     */
    @POST("app_deck/query_headwear")
    HttpObservable<BaseResponse<DeckResult>> getMyHeadwear();

    /**
     * ????????????
     *
     * @param id
     * @return
     */
    @POST("app_deck/use_car")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> useCar(@Field("id") String id);

    /**
     * ???????????????
     *
     * @param id
     * @return
     */
    @POST("app_deck/use_headwear")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> useHeadwear(@Field("id") String id);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST("app_deck/query_car")
    HttpObservable<BaseResponse<DeckResult>> getMyCar();

    /**
     * ??????????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_fan/query_follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFollow(@Field("current") String current,
                                                                                   @Field("size") String size);

    /**
     * ??????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_fan/query_fan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFans(@Field("current") String current,
                                                                                 @Field("size") String size);

    /**
     * ??????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_fan/query_friend")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFriends(@Field("current") String current,
                                                                                    @Field("size") String size);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_coinconfig/rechargeList")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldResult>>>> getGoldList(@Field("current") String current,
                                                                               @Field("size") String size,
                                                                               @Field("userId") String userId);

    /**
     * ????????????
     *
     * @param payType
     * @param payTargetId
     * @return
     */
    @POST("app_pay/buy_coin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<BuyGoldResult>> buyGold(@Field("payType") int payType,
                                                        @Field("payTargetId") String payTargetId);

    /**
     * ??????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_cashconfig/cashlist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldResult>>>> getCashList(@Field("current") String current,
                                                                               @Field("size") String size);

    @POST("app_cashconfig/get_all")
    HttpObservable<BaseResponse<List<NewGoldResult>>> getCashList();


    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_jewel/income_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(@Field("current") String current,
                                                                                   @Field("size") String size,
                                                                                   @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_jewel/outcome_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getOutComeList(@Field("current") String current,
                                                                                    @Field("size") String size,
                                                                                    @Field("date") String date);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_coin/count")
    HttpObservable<BaseResponse<Double>> getMyGoldNum();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_jewel/count")
    HttpObservable<BaseResponse<Double>> getMyDiamondNum();

    /**
     * ????????????
     *
     * @return
     */
    @POST("app_radish/count")
    HttpObservable<BaseResponse<Double>> getMyChili();

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_radish/income_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getMyChiliIncomeList(@Field("current") String current,
                                                                                               @Field("size") String size,
                                                                                               @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_radish/outcome_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getMyChiliOutlayList(@Field("current") String current,
                                                                                               @Field("size") String size,
                                                                                               @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_coin/income_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getMyGoldIncomeList(@Field("current") String current,
                                                                                              @Field("size") String size,
                                                                                              @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_gift/income_coin_gift")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getGoldGiftList(@Field("current") String current,
                                                                                       @Field("size") String size,
                                                                                       @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_gift/income_radish_gift")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getChiliGiftList(@Field("current") String current,
                                                                                        @Field("size") String size,
                                                                                        @Field("date") String date);

    /**
     * ??????????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_gift/outcome_coin_gift")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getGoldGiftOutlayList(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("date") String date);

    /**
     * ??????????????????????????????
     *
     * @param current
     * @param size
     * @param date
     * @return
     */
    @POST("app_gift/outcome_radish_gift")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getChiliGiftOutlayList(@Field("current") String current,
                                                                                              @Field("size") String size,
                                                                                              @Field("date") String date);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_nobleconfig/noble_list")
    HttpObservable<BaseResponse<List<NobleResult>>> getNobleList();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_mine/query_noble")
    HttpObservable<BaseResponse<Integer>> getNoble();

    /**
     * ??????????????????
     *
     * @param nobleId
     * @return
     */
    @POST("app_coin/buy_noble")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> goldBuyNoble(@Field("nobleId") String nobleId);

    /**
     * RMB????????????
     *
     * @param payType
     * @param payTargetId
     * @return
     */
    @POST("app_pay/buy_noble")
    @FormUrlEncoded
    HttpObservable<BaseResponse<BuyGoldResult>> buyNoble(@Field("payType") String payType,
                                                         @Field("payTargetId") String payTargetId);

    /**
     * ????????????????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_teenager/selectStatus")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getTeenStatus(@Field("userId") String userId);


    /**
     * ??????/?????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_teenager/addTeenager")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> startTeens(@Field("userId") String userId,
                                                    @Field("password") String password,
                                                    @Field("status") String status);

    /**
     * ??????????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_userreal/queryreal")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> queryVerified(@Field("userId") String userId);

    /**
     * ????????????
     *
     * @param userId
     * @return
     */
    @POST("app_userreal/userreal")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> verified(@Field("userId") String userId,
                                                  @Field("userRealName") String userRealName,
                                                  @Field("userIdentifica") String userIdentifica);

    /**
     * ????????????
     *
     * @return
     */
    @POST("app_room/create")
    HttpObservable<BaseResponse<CreateRoomResult>> createRoom();

    /**
     * ????????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/enter_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<CreateRoomResult>> enterRoom(@Field("roomId") String roomId);

    /**
     * ??????userid??????????????????userInfo
     *
     * @param userId
     * @return
     */
    @POST("app_room/user_detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getRoomUser(@Field("userId") String userId,
                                                                           @Field("roomId") String roomId);

    /**
     * ??????
     *
     * @param roomId
     * @param mikeOrder
     * @return
     */
    @POST("app_room/into_mike")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> takeSeat(@Field("roomId") String roomId,
                                                   @Field("mikeOrder") String mikeOrder);


    /**
     * ??????
     */
    @POST("app_room/out_mike")
    HttpObservable<BaseResponse<Integer>> outMike();

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    @POST("app_gift/query_room_gift")
    HttpObservable<BaseResponse<GiftAllResult>> getRoomGift();

    /**
     * ?????????
     *
     * @param roomId
     * @param giftId
     * @param userIds
     * @param type
     * @param number
     * @return
     */
    @POST("app_gift/give_gift")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> giveGift(@Field("roomId") String roomId,
                                                   @Field("giftId") String giftId,
                                                   @Field("userIds") List<String> userIds,
                                                   @Field("type") int type,
                                                   @Field("number") int number);

    /**
     * ???????????????????????????
     *
     * @return
     */
    @POST("app_gift/query_package")
    HttpObservable<BaseResponse<List<RoomGiftResult>>> getPackageGift();

    /**
     * ??????????????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/ranking")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HalfRoomRankResult>> getHalfRoomRank(@Field("roomId") String roomId);

    /**
     * ?????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/user_ranking")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<UserRankResult>>> getUserRank(@Field("roomId") String roomId,
                                                                   @Field("type") Integer type);

    /**
     * ??????????????????
     *
     * @param roomId
     * @param noticeTitle
     * @param noticeComment
     * @return
     */
    @POST("app_room/modify_room_notice")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> editRoomNotify(@Field("roomId") String roomId,
                                                         @Field("noticeTitle") String noticeTitle,
                                                         @Field("noticeComment") String noticeComment);

    /**
     * ????????????
     *
     * @param roomId
     * @param type
     * @return
     */
    @POST("app_room/modify_room_public")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> setRoomPublic(@Field("roomId") String roomId,
                                                        @Field("type") int type);

    /**
     * ????????????
     *
     * @param roomId
     * @param giftValue
     * @return
     */
    @POST("app_room/open_gift_value")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> setRoomGiftValue(@Field("roomId") String roomId,
                                                          @Field("giftValue") String giftValue);


    /**
     * ???????????????
     *
     * @param roomId
     * @param userId
     * @return
     */
    @POST("app_room/reset_gift_count")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> clearGiftValue(@Field("roomId") String roomId,
                                                         @Field("userId") String userId);


    /**
     * ???????????????
     *
     * @param roomId
     * @param roomName
     * @return
     */
    @POST("app_room/modify_room_name")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> modifyName(@Field("roomId") String roomId,
                                                     @Field("roomName") String roomName);

    /**
     * ??????????????????
     *
     * @param roomId
     * @param type
     * @param password
     * @return
     */
    @POST("app_room/modify_room_password")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> modifyPsw(@Field("roomId") String roomId,
                                                    @Field("type") String type,
                                                    @Field("password") String password);

    /**
     * ??????????????????
     *
     * @param roomId
     * @param typeId
     * @return
     */
    @POST("app_room/modify_room_type")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> modifyRoomType(@Field("roomId") String roomId,
                                                         @Field("typeId") String typeId);

    /**
     * ?????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/query_admin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<RoomManagerEntity>>> getManager(@Field("roomId") String roomId);

    /**
     * ???????????????
     *
     * @param roomId
     * @param userId
     * @return
     */
    @POST("app_room/remove_admin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> removeManager(@Field("roomId") String roomId,
                                                        @Field("userId") String userId);

    /**
     * ???????????????????????????
     *
     * @param current
     * @param size
     * @param blacklistType
     * @param current_id
     * @return
     */
    @POST("app_blacklist/black_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<RoomBlackListEntity>>>> getRoomBlackList(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("blacklistType") String blacklistType,
                                                                                             @Field("current_id") String current_id);

    /**
     * ???????????????
     *
     * @param blacklistType
     * @param currentId
     * @param blacklistUserId
     * @return
     */
    @POST("app_blacklist/delete_blacklist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> removeBlackList(@Field("blacklistType") String blacklistType,
                                                          @Field("currentId") String currentId,
                                                          @Field("blacklistUserId") String blacklistUserId);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST("app_backgroundImg/query_imglist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<RoomBgEntity>>>> getRoomBg(@Field("current") String current,
                                                                               @Field("size") String size);

    /**
     * ??????????????????
     *
     * @param roomId
     * @param userId
     * @param imgId
     * @return
     */
    @POST("room_background_img/set_background")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> setRoomBg(@Field("roomId") String roomId,
                                                    @Field("userId") String userId,
                                                    @Field("imgId") String imgId);

    /**
     * ??????????????????
     *
     * @param kind ???????????? 1 ???????????? 2 ????????????
     * @return
     */
    @POST("app_draw/detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<PrizeResult>> getPrize(@Field("kind") Integer kind);

    /**
     * ??????
     *
     * @param type ????????????
     * @param kind ????????????
     * @return
     */
    @POST("app_draw/draw")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LotteryPirzeResult>> lottery(@Field("type") int type,
                                                             @Field("kind") int kind);

    /**
     * ????????????????????????
     *
     * @param kind
     * @return
     */
    @POST("app_draw/last_history")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<AwardsEntity>>> getLotterRecord(@Field("kind") int kind);

    @POST("app_room/add_admin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> addManager(@Field("roomId") String roomId,
                                                     @Field("userId") String userId);

    /**
     * ????????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/exit_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> exitRoom(@Field("roomId") String roomId);

    /**
     * ???????????????
     *
     * @param blacklistType
     * @param currentId
     * @param blacklistUserId
     * @return
     */
    @POST("app_blacklist/add_blacklist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> addBlackList(@Field("blacklistType") String blacklistType,
                                                       @Field("currentId") String currentId,
                                                       @Field("blacklistUserId") String blacklistUserId);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param roomId
     * @return
     */
    @POST("app_room/online_person")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<OnLineUserResult>>>> getOnLineUser(@Field("current") String current,
                                                                                       @Field("size") String size,
                                                                                       @Field("roomId") String roomId);

    /**
     * ????????????
     *
     * @param roomId
     * @return
     */
    @POST("wheatlist/query_wheat_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<RowSeatResult>>> getRowSeatUser(@Field("roomId") String roomId);

    /**
     * ????????????
     *
     * @param userId
     * @param roomId
     * @return
     */
    @POST("wheatlist/add_wheat")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> singUp(@Field("userId") String userId,
                                                 @Field("roomId") String roomId);

    /**
     * ??????????????????
     *
     * @param id
     * @param userId
     * @param roomId
     * @return
     */
    @POST("wheatlist/delete_wheat")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> removeSing(@Field("id") String id,
                                                    @Field("userId") String userId,
                                                    @Field("roomId") String roomId);

    @POST("app_room/modify_room_exclusion")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> pickSwitch(@Field("roomId") String roomId,
                                                     @Field("type") String type);

    /**
     * ?????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_blacklist/is_black")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> isBlack(@Field("userId") String userId);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_reportkind/reportkind")
    HttpObservable<BaseResponse<List<ReportType>>> getReportType();

    /**
     * ?????????????????????
     *
     * @param userId        ??????id
     * @param reportType    ????????????????????????1???????????? 2????????????
     * @param reportKindId  ????????????id??????report_kind???id
     * @param targetId      ????????????id??????report_type=1???????????????????????????id?????????report_type=2???????????????????????????id
     * @param reportComment ????????????
     * @return
     */
    @POST("app_reportrecord/filingreport")
    @Multipart
    HttpObservable<BaseResponse<String>> report(@PartMap Map<String, RequestBody> files,
                                                @Part("userId") String userId,
                                                @Part("reportType") String reportType,
                                                @Part("reportKindId") String reportKindId,
                                                @Part("targetId") String targetId,
                                                @Part("reportComment") String reportComment);

    /**
     * ????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_topic/message_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<TopicMsg>>>> getTopicMsg(@Field("current") String current,
                                                                             @Field("size") String size,
                                                                             @Field("userId") String userId);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST("app_mine/level_disparity")
    HttpObservable<BaseResponse<GradeResult>> getUserGrade();

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST("app_mine/charm_disparity")
    HttpObservable<BaseResponse<GradeResult>> getCharmGrade();

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_record/clan_commission")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FamilyMemberResult>>>> getFamilyUser(@Field("current") String current,
                                                                                         @Field("size") String size,
                                                                                         @Field("userId") String userId);

    /**
     * ????????????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_record/query_clan_info")
    @FormUrlEncoded
    HttpObservable<BaseResponse<StartRecommendResult>> getFamilyDiamondInfo(@Field("userId") String userId);


    @POST("app_auth/reset_password")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> changePswMine(@Field("type") String type, @Field("password") String password, @Field("code") String code, @Field("oldPassword") String oldPassword);

    /**
     * ??????????????????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_record/user_income_detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<UserIncome>>>> getUserIncome(@Field("current") String current,
                                                                                 @Field("size") String size,
                                                                                 @Field("userId") String userId,
                                                                                 @Field("clanId") String clanId,
                                                                                 @Field("startTime") String startTime);

    /**
     * ??????????????????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("/app_record/clan_income_detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<UserIncome>>>> getFamilyIncome(@Field("current") String current,
                                                                                   @Field("size") String size,
                                                                                   @Field("userId") String userId,
                                                                                   @Field("clanId") String clanId,
                                                                                   @Field("startTime") String startTime);

    /**
     * ??????userid??????????????????
     *
     * @param userId
     * @return
     */
    @POST("app_room/user_info")
    @FormUrlEncoded
    HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getUserInfo(@Field("userId") String userId);


    /**
     * ???????????????????????????
     *
     * @param current
     * @param size
     * @param blacklistType
     * @param current_id
     * @return
     */
    @POST("app_blacklist/black_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<BlackListMineBean>>>> getBlackListMine(@Field("current") String current,
                                                                                           @Field("size") String size,
                                                                                           @Field("blacklistType") String blacklistType,
                                                                                           @Field("current_id") String current_id);

    /**
     * ???????????????
     *
     * @param id
     * @return
     */
    @POST("app_blacklist/delete_blacklist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> removeBlackMine(@Field("id") String id);


    /**
     * ??????     *
     *
     * @param feedbackComment ??????
     * @param contactType     ????????????1qq 2??????
     * @param contactNumber   ?????????qq???
     * @param userId          ??????id
     * @return
     */
    @POST("app_feedback/add_feedback")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> feedBack(@Field("feedbackComment") String feedbackComment,
                                                   @Field("contactType") String contactType,
                                                   @Field("contactNumber") String contactNumber,
                                                   @Field("userId") String userId);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_message/sysmessag_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<SystemNotic>>>> getSystemNotic(@Field("current") String current,
                                                                                   @Field("size") String size);


    /**
     * ????????????
     *
     * @return
     */
    @POST("app_download/download")
    HttpObservable<BaseResponse<CheckVersionBean>> checkVersion();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_room/history")
    HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_room/delete_room_record")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> removeEnterRoomRecord(@Field("userId") String userId);

    /**
     * ????????????
     *
     * @param searchString
     * @return
     */
    @POST("app_user/search")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(@Field("searchString") String searchString);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_sign/query_config")
    HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_sign/sign_days")
    HttpObservable<BaseResponse<SignDayEntity>> getSignDay();

    /**
     * ??????
     *
     * @return
     */
    @POST("app_sign/sign")
    HttpObservable<BaseResponse<Integer>> sign();

    /**
     * ???????????????
     *
     * @param phone
     * @param userId
     * @param code
     * @return
     */
    @POST("app_auth/modifie_phone")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> changePhone(@Field("phone") String phone,
                                                     @Field("userId") String userId,
                                                     @Field("code") String code);

    /**
     * ??????????????????
     *
     * @param userId
     * @param status
     * @return
     */
    @POST("app_mine/update_invisible_status")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> enterRoomStatus(@Field("userId") String userId,
                                                         @Field("status") String status);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_customerserve/customerserve")
    HttpObservable<BaseResponse<CustomerServiceEntity>> getService();

    /**
     * ?????????????????????????????????????????????
     *
     * @return
     */
    @POST("app_room/query_created")
    HttpObservable<BaseResponse<QueryRoomEntity>> queryRoom();

    /**
     * ????????????ID ??????????????????????????????????????????ID????????????????????????????????????
     *
     * @param roomId
     * @return
     */
    @POST("app_room/query_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<RoomEtity>> queryEnterRoom(@Field("roomId") String roomId);

    /**
     * ?????????????????????
     *
     * @return
     */
    @POST("app_room/random_room_id")
    HttpObservable<BaseResponse<String>> getHiChat();

    /**
     * ??????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_jewel_cash_record/query_jewel_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getCashList(@Field("current") String current,
                                                                               @Field("size") String size,
                                                                               @Field("userId") String userId,
                                                                               @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param current
     * @param size
     * @param userId
     * @return
     */
    @POST("app_coin_recharge_record/query_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getGoldRecharge(@Field("current") String current,
                                                                                   @Field("size") String size,
                                                                                   @Field("userId") String userId,
                                                                                   @Field("date") String date);

    /**
     * ???????????????
     *
     * @param jewelNumber
     * @param coinNumber
     * @param userId
     * @return
     */
    @POST("jewel_coin_record/Jewel_change_coin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> goldChange(@Field("jewelNumber") String jewelNumber,
                                                    @Field("coinNumber") String coinNumber,
                                                    @Field("userId") String userId);

    /**
     * ??????????????????
     * @param coinNumber
     * @return
     */
    @POST("app_jewel_coin_record/jewel_change_coin2")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> goldChange(@Field("coinNumber") String coinNumber);


    /**
     * ????????????
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/delete")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> deleteTopic(@Field("topicId") String topicId);


    /**
     * ???????????????
     *
     * @param userId
     * @return
     */
    @POST("app_teenager/selectStatus")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getTeensStatus(@Field("userId") String userId);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("app_user/get_set_account")
    HttpObservable<BaseResponse<BindEntity>> getBoundPay();

    /**
     * ??????????????????ali??????
     *
     * @param type
     * @param account
     * @return
     */
    @POST("app_user/bing_account")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> bindWeChat(@Field("type") String type, @Field("account") String account,
                                                     @Field("realName") String realName, @Field("code") String code);

    /**
     * ??????????????????
     * @param type
     * @param account
     * @param bankType
     * @param bankName
     * @param realName
     * @param code
     * @return
     */
    @POST("app_user/bing_account")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> bindBlackNum(@Field("type") String type, @Field("account") String account,
                                                       @Field("bankType") String bankType, @Field("bankName") String bankName,
                                                       @Field("realName") String realName, @Field("code") String code);

    /**
     * ??????
     * @param cashConfigId
     * @return
     */
    @POST("app_jewel_cash_record/apply_jewel_cash")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> withdraw(@Field("cashConfigId") String cashConfigId);

    /**
     * ???????????????????????????
     * @return
     */
    @POST("app_gift/user_all_gift_price")
    HttpObservable<BaseResponse<Integer>> getTotalValue();

    /**
     * ????????????????????????
     * @return
     */
    @POST("/app_draw/open_draw")
    HttpObservable<BaseResponse<Map<String,String>>> getRoomConfig();

    /**
     * ????????????token
     */
    @POST("https://aip.baidubce.com/oauth/2.0/token")
    @FormUrlEncoded
    HttpObservable<getBaiDUTokenBean> getBaiDUToken(@Field("grant_type") String grant_type, @Field("client_id") String client_id, @Field("client_secret") String client_secret);
    /**
     * ?????????????????????????????????
     */
    @POST("app_auth/update_authentication")
    @FormUrlEncoded
    HttpObservable<BaseResponse> getUpdateAuthentication(@Field("phone") String phone);
    /**
     * ????????????
     */
    @POST("app_pay/buy_ticket")
    @FormUrlEncoded
    HttpObservable<GetAppPayBuyTicketBean> getAppPayBuyTicket(@Field("payType") String payType, @Field("payTargetId") String payTargetId);
    /**
     * APP???????????????????????????????????????????????????
     */
    @POST("app_mine/base_data")
    @FormUrlEncoded
    HttpObservable<GetAppMinetBaseDataBean> getAppMinetBaseData(@Field("userId") String userId);
}

