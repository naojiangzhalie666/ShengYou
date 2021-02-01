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
     * 一键登录 - 手机号登录
     *
     * @param phone
     * @return
     */
    @POST("app_auth/login_phone")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> oneKeyLogin(@Field("phone") String phone);

    /**
     * 一键登录 - 手机号登录
     *
     * @param userId
     * @return
     */
    @POST("app_messaging/usersig")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getUserSig(@Field("userId") String userId);

    /**
     * 登录 - 通过手机号获取验证码
     *
     * @param phone
     * @return
     */
    @POST("app_auth/get_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getStatusCode(@Field("phone") String phone);

    /**
     * 登录 - 通过手机号和验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    @POST("app_auth/login_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> loginCode(@Field("phone") String phone, @Field("code") String code, @Field("city") String city, @Field("latitude") String latitude, @Field("longitude") String longitude);

    /**
     * 登录 - 通过手机号和密码登录
     *
     * @param phone
     * @param password
     * @return
     */
    @POST("app_auth/login_pw")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LogonResult>> loginPsd(@Field("phone") String phone, @Field("password") String password, @Field("city") String city, @Field("latitude") String latitude, @Field("longitude") String longitude);

    /**
     * 注册 - 校验手机号与验证码
     *
     * @param phone
     * @param code
     * @return
     */
    @POST("app_auth/check_code")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> checkCode(@Field("phone") String phone, @Field("code") String code);

    /**
     * 注册
     *
     * @param file         头像
     * @param userName     用户昵称
     * @param userPhone    用户手机号
     * @param userBirthday 用户生日
     * @param userSex      用户性别
     * @param wechatOpenid 用户微信号
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
     * 首页banner图
     *
     * @return
     */
    @POST("app_banner/query")
    HttpObservable<BaseResponse<List<BannerResult>>> getBanner();

    /**
     * 首页分类
     *
     * @return
     */
    @POST("app_room/type")
    HttpObservable<BaseResponse<List<HomeSortResult>>> getHomeSort();

    /**
     * 获取首页分类中开放的房间
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
     * 朋友圈发布
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
     * 获取附近朋友圈
     *
     * @param current
     * @param size
     * @return
     */
    @POST("app_topic/query_all")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(@Field("current") String current,
                                                                                             @Field("size") String size,
                                                                                             @Field("userId") String userId
    , @Field("city") String city);
    /**
     * 获取推荐朋友圈
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
     * 获取关注朋友圈
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
     * 获取单个朋友圈
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/query_one")
    @FormUrlEncoded
    HttpObservable<BaseResponse<FriendsCircleResult>> getFriendOne(@Field("topicId") String topicId);

    /**
     * 点赞
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/do_like")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> fabulous(@Field("topicId") String topicId);

    /**
     * 获取某条朋友圈评论
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
     * 朋友圈评论 一级评论和二级品论
     *
     * @param topicId        朋友圈id
     * @param commentContent 评论文本内容
     * @param commentType    评论类型： 1. 一级品论 2.二级评论 二级评论时需要提供评论目标的id
     * @param fatherId       评论目标id 当为二级评论时必须传此参数
     * @return
     */
    @POST("app_topic/add_comment")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> comment(@Field("topicId") String topicId,
                                                 @Field("commentContent") String commentContent,
                                                 @Field("commentType") String commentType,
                                                 @Field("fatherId") String fatherId);


    /**
     * 查询单个评论
     *
     * @param commentId
     * @return
     */
    @POST("app_topic/query_comment_id")
    @FormUrlEncoded
    HttpObservable<BaseResponse<CommentResult>> getSecondComment(@Field("commentId") String commentId);

    /**
     * 根据userid查询家族信息
     *
     * @param userId
     * @return
     */
    @POST("app_clan/query_myclan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(@Field("userId") String userId);

    /**
     * 家族星推荐接口
     *
     * @return
     */
    @POST("app_record/recommend")
    HttpObservable<BaseResponse<List<StartRecommendResult>>> getStartRecommend();

    /**
     * 获取家族魅力排行榜
     *
     * @return
     */
    @POST("app_record/leaderboard")
    HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank();

    /**
     * 家族详情，根据家族id查询家族信息
     *
     * @param clanId
     * @return
     */
    @POST("app_clan/query_claninfo")
    @FormUrlEncoded
    HttpObservable<BaseResponse<ClanResult>> getClanInfo(@Field("clanId") String clanId);

    /**
     * 根据家族ID 获取家族成员
     *
     * @param clanId
     * @return
     */
    @POST("app_clanmember/clanmembers")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<FamilyMemberResult>>> familyMembers(@Field("clanId") String clanId);


    /**
     * 查询用户是否有待审核家族申请
     *
     * @param userId 用户ID
     * @return
     */
    @POST("app_clanapply/status")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getFamilyApplyStatu(@Field("userId") String userId);


    /**
     * 创建家族
     *
     * @param clanName     家族名称
     * @param applicantId  申请者id
     * @param applyComment 备注
     * @param file         家族图片文件
     * @return
     */
    @POST("app_clanapply/createclan")
    @Multipart
    HttpObservable<BaseResponse<String>> createFamily(@Part("clanName") String clanName,
                                                      @Part("applicantId") String applicantId,
                                                      @Part("applyComment") String applyComment,
                                                      @Part MultipartBody.Part file);

    /**
     * 获取家族申请记录
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
     * 获取个人基本资料
     *
     * @param userId
     * @return
     */
    @POST("app_mine/base_data")
    @FormUrlEncoded
    HttpObservable<BaseResponse<MineReslut>> getMine(@Field("userId") String userId);

    /**
     * 加入家族
     *
     * @param userId
     * @param clanId
     * @return
     */
    @POST("app_clan/add_clan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> joinFamily(@Field("userId") String userId, @Field("clanId") String clanId);

    /**
     * 退出家族
     *
     * @param userId
     * @param clanId
     * @return
     */
    @POST("app_clan/quit_clan")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> outFamily(@Field("userId") String userId, @Field("clanId") String clanId);

    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    @POST("app_mine/update_portrait")
    @Multipart
    HttpObservable<BaseResponse<AvatarResult>> upAvatar(@Part MultipartBody.Part file);

    /**
     * 修改生日
     *
     * @param birthday
     * @return
     */
    @POST("app_mine/update_birthday")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editBirthday(@Field("birthday") String birthday);

    /**
     * 修改昵称
     *
     * @param name
     * @return
     */
    @POST("app_mine/update_name")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editNickName(@Field("name") String name);

    /**
     * 修改昵称
     *
     * @param introduce
     * @return
     */
    @POST("app_mine/update_introduce")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> editIntroduce(@Field("introduce") String introduce);

    /**
     * 个人资料·增加相册图片
     *
     * @param file
     * @return
     */
    @POST("app_mine/add_picture")
    @Multipart
    HttpObservable<BaseResponse<String>> addPicture(@Part MultipartBody.Part file);

    /**
     * 个人资料·删除相册图片
     *
     * @param id
     * @return
     */
    @POST("app_mine/del_picture")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> deletePhoto(@Field("id") String id);

    /**
     * 个人资料·查询用户相册
     *
     * @param userId
     * @return
     */
    @POST("app_mine/query_picture")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<UserPicturesResult>>> getMyPhotos(@Field("userId") String userId);


    /**
     * 查询是否关注某用户
     *
     * @param userId
     * @return
     */
    @POST("app_fan/if_follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> queryFollow(@Field("userId") String userId);

    /**
     * 关注&取消关注
     * 这里当关注使用
     *
     * @param userId
     * @param status
     * @return
     */
    @POST("app_fan/follow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> follow(@Field("userId") String userId, @Field("status") String status);

    /**
     * 取消关注
     *
     * @param userId
     * @return
     */
    @POST("app_fan/unfollow")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> unfollow(@Field("userId") String userId);

    /**
     * 个人资料·获得礼物历史
     *
     * @param userId
     * @return
     */
    @POST("app_gift/get_history")
    @FormUrlEncoded
    HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(@Field("userId") String userId);

    /**
     * 个人资料·座驾列表
     *
     * @param userId
     * @return
     */
    @POST("app_deck/query_car")
    @FormUrlEncoded
    HttpObservable<BaseResponse<DeckResult>> carRecord(@Field("userId") String userId);

    /**
     * 商城·查询装扮（头饰/座驾）
     *
     * @param deckType
     * @return
     */
    @POST("app_deck/query_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<Decks>>> store(@Field("deckType") Integer deckType);

    /**
     * 购买头饰/座驾
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
     * 获取我的头饰列表
     *
     * @return
     */
    @POST("app_deck/query_headwear")
    HttpObservable<BaseResponse<DeckResult>> getMyHeadwear();

    /**
     * 使用座驾
     *
     * @param id
     * @return
     */
    @POST("app_deck/use_car")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> useCar(@Field("id") String id);

    /**
     * 使用头像框
     *
     * @param id
     * @return
     */
    @POST("app_deck/use_headwear")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> useHeadwear(@Field("id") String id);

    /**
     * 获取我的座驾列表
     *
     * @return
     */
    @POST("app_deck/query_car")
    HttpObservable<BaseResponse<DeckResult>> getMyCar();

    /**
     * 查询·关注用户列表
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
     * 获取粉丝列表
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
     * 获取粉丝列表
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
     * 获取充值金币列表
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
     * 购买金币
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
     * 提现金额列表
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
     * 钻石进账分页列表
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
     * 钻石进账分页列表
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
     * 获取金币数量
     *
     * @return
     */
    @POST("app_coin/count")
    HttpObservable<BaseResponse<Double>> getMyGoldNum();

    /**
     * 获取钻石数量
     *
     * @return
     */
    @POST("app_jewel/count")
    HttpObservable<BaseResponse<Double>> getMyDiamondNum();

    /**
     * 辣椒总数
     *
     * @return
     */
    @POST("app_radish/count")
    HttpObservable<BaseResponse<Double>> getMyChili();

    /**
     * 获取辣椒进账记录
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
     * 获取辣椒出账列表
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
     * 获取辣椒出账列表
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
     * 获取金币收礼记录
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
     * 获取辣椒收礼列表
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
     * 金币礼物赠送分页列表
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
     * 辣椒礼物赠送分页列表
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
     * 贵族购买列表
     *
     * @return
     */
    @POST("app_nobleconfig/noble_list")
    HttpObservable<BaseResponse<List<NobleResult>>> getNobleList();

    /**
     * 获取贵族身份
     *
     * @return
     */
    @POST("app_mine/query_noble")
    HttpObservable<BaseResponse<Integer>> getNoble();

    /**
     * 金币购买贵族
     *
     * @param nobleId
     * @return
     */
    @POST("app_coin/buy_noble")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> goldBuyNoble(@Field("nobleId") String nobleId);

    /**
     * RMB购买贵族
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
     * 查询是否开启了青少年模式
     *
     * @param userId
     * @return
     */
    @POST("app_teenager/selectStatus")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getTeenStatus(@Field("userId") String userId);


    /**
     * 开启/关闭青少年模式
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
     * 查询用户是否实名认证
     *
     * @param userId
     * @return
     */
    @POST("app_userreal/queryreal")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> queryVerified(@Field("userId") String userId);

    /**
     * 实名认证
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
     * 创建房间
     *
     * @return
     */
    @POST("app_room/create")
    HttpObservable<BaseResponse<CreateRoomResult>> createRoom();

    /**
     * 进入房间
     *
     * @param roomId
     * @return
     */
    @POST("app_room/enter_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<CreateRoomResult>> enterRoom(@Field("roomId") String roomId);

    /**
     * 根据userid获取直播间的userInfo
     *
     * @param userId
     * @return
     */
    @POST("app_room/user_detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getRoomUser(@Field("userId") String userId,
                                                                           @Field("roomId") String roomId);

    /**
     * 上麦
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
     * 下麦
     */
    @POST("app_room/out_mike")
    HttpObservable<BaseResponse<Integer>> outMike();

    /**
     * 房间礼物列表集合查询（整合普通、贵族、魔法、背包礼物）
     *
     * @return
     */
    @POST("app_gift/query_room_gift")
    HttpObservable<BaseResponse<GiftAllResult>> getRoomGift();

    /**
     * 送礼物
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
     * 获取背包里面的礼物
     *
     * @return
     */
    @POST("app_gift/query_package")
    HttpObservable<BaseResponse<List<RoomGiftResult>>> getPackageGift();

    /**
     * 房间半小时榜
     *
     * @param roomId
     * @return
     */
    @POST("app_room/ranking")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HalfRoomRankResult>> getHalfRoomRank(@Field("roomId") String roomId);

    /**
     * 房内榜
     *
     * @param roomId
     * @return
     */
    @POST("app_room/user_ranking")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<UserRankResult>>> getUserRank(@Field("roomId") String roomId,
                                                                   @Field("type") Integer type);

    /**
     * 设置房间公告
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
     * 公屏开关
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
     * 公屏开关
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
     * 清空礼物值
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
     * 修改房间名
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
     * 修改房间密码
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
     * 修改房间属性
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
     * 房内榜
     *
     * @param roomId
     * @return
     */
    @POST("app_room/query_admin")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<RoomManagerEntity>>> getManager(@Field("roomId") String roomId);

    /**
     * 移除管理员
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
     * 获取房间黑名单列表
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
     * 删除黑名单
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
     * 获取房间背景列表
     *
     * @return
     */
    @POST("app_backgroundImg/query_imglist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<HomeRoomResult<List<RoomBgEntity>>>> getRoomBg(@Field("current") String current,
                                                                               @Field("size") String size);

    /**
     * 设置房间背景
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
     * 查看奖池礼物
     *
     * @param kind 奖池类型 1 白银宝箱 2 黄金宝箱
     * @return
     */
    @POST("app_draw/detail")
    @FormUrlEncoded
    HttpObservable<BaseResponse<PrizeResult>> getPrize(@Field("kind") Integer kind);

    /**
     * 抽奖
     *
     * @param type 连抽类型
     * @param kind 宝箱类型
     * @return
     */
    @POST("app_draw/draw")
    @FormUrlEncoded
    HttpObservable<BaseResponse<LotteryPirzeResult>> lottery(@Field("type") int type,
                                                             @Field("kind") int kind);

    /**
     * 查询上传抽奖记录
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
     * 退出房间
     *
     * @param roomId
     * @return
     */
    @POST("app_room/exit_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> exitRoom(@Field("roomId") String roomId);

    /**
     * 删除黑名单
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
     * 分页获取在线用户
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
     * 排麦列表
     *
     * @param roomId
     * @return
     */
    @POST("wheatlist/query_wheat_list")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<RowSeatResult>>> getRowSeatUser(@Field("roomId") String roomId);

    /**
     * 排序报名
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
     * 取消排麦报名
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
     * 是否拉黑该用户
     *
     * @param userId
     * @return
     */
    @POST("app_blacklist/is_black")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> isBlack(@Field("userId") String userId);

    /**
     * 获取举报类型
     *
     * @return
     */
    @POST("app_reportkind/reportkind")
    HttpObservable<BaseResponse<List<ReportType>>> getReportType();

    /**
     * 举报房间或用户
     *
     * @param userId        用户id
     * @param reportType    举报类型举报类型1举报房间 2举报个人
     * @param reportKindId  举报种类id关联report_kind表id
     * @param targetId      举报对象id如果report_type=1，这里填入的是房间id，如果report_type=2，这里填入的是用户id
     * @param reportComment 举报类容
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
     * 话题列表
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
     * 获取用户等级信息
     *
     * @return
     */
    @POST("app_mine/level_disparity")
    HttpObservable<BaseResponse<GradeResult>> getUserGrade();

    /**
     * 获取用户魅力等级信息
     *
     * @return
     */
    @POST("app_mine/charm_disparity")
    HttpObservable<BaseResponse<GradeResult>> getCharmGrade();

    /**
     * 族长获取族员列表
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
     * 族长获取家族信息
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
     * 获取家族个人收入详情
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
     * 获取家族个人收入详情
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
     * 根据userid获取用户详情
     *
     * @param userId
     * @return
     */
    @POST("app_room/user_info")
    @FormUrlEncoded
    HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getUserInfo(@Field("userId") String userId);


    /**
     * 获取房间黑名单列表
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
     * 移除黑名单
     *
     * @param id
     * @return
     */
    @POST("app_blacklist/delete_blacklist")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> removeBlackMine(@Field("id") String id);


    /**
     * 反馈     *
     *
     * @param feedbackComment 内容
     * @param contactType     反馈类型1qq 2微信
     * @param contactNumber   微信或qq号
     * @param userId          用户id
     * @return
     */
    @POST("app_feedback/add_feedback")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> feedBack(@Field("feedbackComment") String feedbackComment,
                                                   @Field("contactType") String contactType,
                                                   @Field("contactNumber") String contactNumber,
                                                   @Field("userId") String userId);

    /**
     * 获取系统通知列表
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
     * 检查更新
     *
     * @return
     */
    @POST("app_download/download")
    HttpObservable<BaseResponse<CheckVersionBean>> checkVersion();

    /**
     * 获取进房记录
     *
     * @return
     */
    @POST("app_room/history")
    HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord();

    /**
     * 删除进房记录
     *
     * @return
     */
    @POST("app_room/delete_room_record")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> removeEnterRoomRecord(@Field("userId") String userId);

    /**
     * 首页搜索
     *
     * @param searchString
     * @return
     */
    @POST("app_user/search")
    @FormUrlEncoded
    HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(@Field("searchString") String searchString);

    /**
     * 签到奖励列表
     *
     * @return
     */
    @POST("app_sign/query_config")
    HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward();

    /**
     * 获取签到天数
     *
     * @return
     */
    @POST("app_sign/sign_days")
    HttpObservable<BaseResponse<SignDayEntity>> getSignDay();

    /**
     * 签到
     *
     * @return
     */
    @POST("app_sign/sign")
    HttpObservable<BaseResponse<Integer>> sign();

    /**
     * 修改手机号
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
     * 进房隐身设置
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
     * 获取客服详情
     *
     * @return
     */
    @POST("app_customerserve/customerserve")
    HttpObservable<BaseResponse<CustomerServiceEntity>> getService();

    /**
     * 创建房间前查询个人创建房间详情
     *
     * @return
     */
    @POST("app_room/query_created")
    HttpObservable<BaseResponse<QueryRoomEntity>> queryRoom();

    /**
     * 通过房间ID 查询房间信息，用户只知道房间ID但是需要进入房间时的需求
     *
     * @param roomId
     * @return
     */
    @POST("app_room/query_room")
    @FormUrlEncoded
    HttpObservable<BaseResponse<RoomEtity>> queryEnterRoom(@Field("roomId") String roomId);

    /**
     * 随进进入嗨聊房
     *
     * @return
     */
    @POST("app_room/random_room_id")
    HttpObservable<BaseResponse<String>> getHiChat();

    /**
     * 用户提现记录
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
     * 用户金币充值记录
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
     * 钻石转金币
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
     * 新钻石转金币
     * @param coinNumber
     * @return
     */
    @POST("app_jewel_coin_record/jewel_change_coin2")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> goldChange(@Field("coinNumber") String coinNumber);


    /**
     * 删除话题
     *
     * @param topicId
     * @return
     */
    @POST("app_topic/delete")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> deleteTopic(@Field("topicId") String topicId);


    /**
     * 青少年模式
     *
     * @param userId
     * @return
     */
    @POST("app_teenager/selectStatus")
    @FormUrlEncoded
    HttpObservable<BaseResponse<String>> getTeensStatus(@Field("userId") String userId);

    /**
     * 获取提现账号
     *
     * @return
     */
    @POST("app_user/get_set_account")
    HttpObservable<BaseResponse<BindEntity>> getBoundPay();

    /**
     * 绑定微信号和ali账号
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
     * 银行卡号绑定
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
     * 提现
     * @param cashConfigId
     * @return
     */
    @POST("app_jewel_cash_record/apply_jewel_cash")
    @FormUrlEncoded
    HttpObservable<BaseResponse<Integer>> withdraw(@Field("cashConfigId") String cashConfigId);

    /**
     * 获取背包礼物总价值
     * @return
     */
    @POST("app_gift/user_all_gift_price")
    HttpObservable<BaseResponse<Integer>> getTotalValue();

    /**
     * 获取房间抽奖配置
     * @return
     */
    @POST("/app_draw/open_draw")
    HttpObservable<BaseResponse<Map<String,String>>> getRoomConfig();

    /**
     * 获取百度token
     */
    @POST("https://aip.baidubce.com/oauth/2.0/token")
    @FormUrlEncoded
    HttpObservable<getBaiDUTokenBean> getBaiDUToken(@Field("grant_type") String grant_type, @Field("client_id") String client_id, @Field("client_secret") String client_secret);
    /**
     * 告诉服务器是女的登录了
     */
    @POST("app_auth/update_authentication")
    @FormUrlEncoded
    HttpObservable<BaseResponse> getUpdateAuthentication(@Field("phone") String phone);
    /**
     * 登录支付
     */
    @POST("app_pay/buy_ticket")
    @FormUrlEncoded
    HttpObservable<GetAppPayBuyTicketBean> getAppPayBuyTicket(@Field("payType") String payType, @Field("payTargetId") String payTargetId);
    /**
     * APP启动检查用户是否认证，是否购买门票
     */
    @POST("app_mine/base_data")
    @FormUrlEncoded
    HttpObservable<GetAppMinetBaseDataBean> getAppMinetBaseData(@Field("userId") String userId);
}

