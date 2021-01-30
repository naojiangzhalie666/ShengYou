package com.xiaoshanghai.nancang.net.bean

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.chad.library.adapter.base.entity.JSectionEntity
import com.chad.library.adapter.base.entity.SectionEntity
import java.io.Serializable

data class FaceMsg(var seatIndex: Int, var phiz: String?) : BaseResult()

data class RoomGiftResult(var id: String,
                          var giftName: String,
                          var giftStaticUrl: String,
                          var giftUrl: String,
                          var giftType: Int,
                          var giftPriceType: Int,
                          var giftShowType: Int,
                          var giftStatus: Int,
                          var giftPrice: Int,
                          var nobleId: String,
                          var createTime: String,
                          var active: String,
                          val giftCount: Int,
                          var isSelect: Boolean = false) : BaseResult()

data class GiftAllResult(var generals: MutableList<RoomGiftResult>,
                         var nobles: MutableList<RoomGiftResult>,
                         var magics: MutableList<RoomGiftResult>,
                         var packages: MutableList<RoomGiftResult>) : BaseResult()

data class GiftViewSeat(var userId: String?,
                        var name: String?,
                        var avatar: String?,
                        var noble: String?,
                        var index: Int?,
                        var isAllSelect: Boolean?,
                        var isRoom: Boolean?,
                        var isSelect: Boolean?,
                        var isSeat: Boolean?
)

data class SendGift(
        var userId: String?,     //赠送人id
        var userName: String?,   //赠送人名字
        var userLevel: Int?,     //赠送人魅力等级
        var userNoble: String?,     //赠送人爵位
        var content: String?,    //空 因为格式固定，没有文本内容
        var handserName: String?,//被赠送人名字
        var handserNoble: String?, //被赠送人爵位
        var giftImage: String?,//被赠送礼物静态图片
        var giftShowImage: String?,  //赠送礼物展示图片 可能是SVGA 可能是png
        var giftNum: Int?,    //赠送数
        var type: Int?,
        var getUserIds: String?,    //接收礼物的userids
        var giftPriceType: Int?,    //礼物类型
        var giftPrice: Int?,        //礼物价值
        var giftShowType: Int?
)

data class HalfRoomRankResult(var thisRanking: RoomRankResult?,
                              var rankings: MutableList<RoomRankResult>?)

data class RoomRankResult(var rankNo: Int?,
                          var roomId: String?,
                          var roomName: String?,
                          var roomPicture: String?,
                          var roomNumber: String?,
                          var countPrice: Int,
                          var upPrice: Int)

data class UserRankResult(var rankNo: Int?,
                          var userId: String?,
                          var userName: String?,
                          var userPicture: String?,
                          var userNumber: String?,
                          var userSex: Int?,
                          var countPrice: Int?)

data class RoomNotiyEntity(var roomNotiyTitle: String?,
                           var roomNotiyComment: String?)

data class RoomSettingEntity(var roomName: String?,
                             var roomNoticeTitle: String?,
                             var roomNoticeComment: String?,
                             var typeName: String?,
                             var roomTypeId: String?,
                             var roomPublic: Boolean?,
                             var roomSpecial: Boolean?,
                             var roomLock: Boolean?,
                             var roomPassword: String?,
                             var roomExclusion: Boolean?,
                             var roomBgPicture: String?,
                             var roomNumber: String?,
                             var roomStatus: Boolean?,
                             var id: String?,
                             var memberCount: Int?,
                             var roomOwnerId: String?,
                             var giftValue: Boolean?,
                             var isBlock: Int?,
                             var personCount: Int?,
                             var createUserId: String?,
                             var userKind: Int?,
                             var roomTypeColor: String?)

data class RoomPsd(var roomLock: Boolean,
                   var roomPsd: String?)

data class RoomTypeEntity(var roomTypeId: String?,
                          var roomTypeName: String?,
                          var roomTypeColor: String?)

data class RoomManagerEntity(var id: String?,
                             var userId: String?,
                             var userPicture: String?,
                             var userName: String?)

data class RoomManagerMsgEntity(var userKind: Int?,
                                var userId: String?)

data class RoomBlackListEntity(var id: String?,
                               var userId: String?,
                               var userName: String?,
                               var userPicture: String?,
                               var sex: Any?)

data class RoomBgEntity(var id: String?,
                        var backgroundUrl: String?,
                        var backgroundName: String?,
                        var nobleId: Int?,
                        var status: Int?,
                        var createTime: String?,
                        var active: Any) : BaseResult()

data class PrizeResult(var id: String?,
                       var drawName: String?,
                       var drawKind: Int?,
                       var drawCost1: Int?,
                       var drawCost2: Int?,
                       var drawCost3: Int?,
                       var items: MutableList<PrizePoolEntity>?)

data class PrizePoolEntity(var id: String?,
                           var drawId: String?,
                           var itemNo: Int?,
                           var rewardPicture: String?,
                           var drawRate: String?,
                           var rewardId: String?,
                           var rate: String?,
                           var gift: GiftsResult?)

data class PositionEntity(var image: ImageView?,
                          var fromAxisX: Float?,
                          var fromAxisY: Float?,
                          var toAxisX: Float?,
                          var toAxisY: Float?,
                          var isSeat: Boolean?)

data class LotteryPirzeResult(var status: Int?,
                              var resultItems: MutableList<AwardsEntity>?)

data class AwardsEntity(var item: AwardsResult?,
                        var times: Int?)

data class AwardsResult(var id: String?,
                        var drawId: String?,
                        var itemNo: Int?,
                        var rewardPicture: String?,
                        var rewardType: Int?,
                        var rewardId: String,
                        var gift: GiftsResult?)

data class FunButtonEntity(@DrawableRes var lordIcon: Int?,
                           @DrawableRes var viceIcon: Int?,
                           var buttonName: String?,
                           var buttonId: Int?,
                           var isSelect: Boolean?)

data class RoomSeat(var isSeat: Boolean?,
                    var index: Int?, //座位序号
                    var isUsed: Boolean? = false,//座位是否使用
                    var isClose: Boolean? = false,//座位是否关闭
                    var isMute: Boolean? = false//座位是否静音
) : BaseResult()

data class OnLineUserResult(
        var userId: String?,
        var userPicture: String?,
        var userName: String?,
        var userSex: Int?,
        var noble: String?,
        var onMike: Int?,
        var userKind: Int?,
        var userLevel: Int?,
        var charmLevel: Int?,
        var userType: Int?,
        var headwear: String?
) : BaseResult()

data class OnLinePick(
        var userId: String?,
        var seatIndex: Int) : BaseResult()

data class RowSeatResult(var id: String?,
                         var roomId: String?,
                         var userId: String?,
                         var createTime: String?,
                         var active: String?,
                         var userName: String?,
                         var userPictrue: String?,
                         var sax: Int?,
                         var index: Int?)

data class GlobalMsgResult(
        var giftPrice: String?,
        var roomId: String?,
        var sendUserAvatar: String?,
        var giftStaticUrl: String?,
        var getUserName: String?,
        var giftNum: String?,
        var sendUserName: String?,
        var getUserAvatar: String?,
        var giftName: String?,
        var type: Int,//抽奖全局消息 1, 送礼物全局消息 2
        var boxType:Int?
)

data class LotteryEntity(
        var giftPrice: Int?,
        var userLevel: Int?,
        var giftPriceType: Int?,
        var userId: String?,
        var userNoble: Int?,
        var userName: String?,
        var boxType: Int?,
        var type: Int?,
        var lotteryResult: String?
)

data class SmallAnimation(var imageView: ImageView?,
                          var isSeat: Boolean?,
                          var index: Int?)

data class ChatGiftSeat(
        var userPicture: String?,
        var userName: String?,
        var userId: String?,
        var isSelect: Boolean?
)

data class ReportType(
        var id: String?,
        var reportKindComment: String?,
        var createTime: String?,
        var active: String?,
        var isSelect: Boolean = false
)

data class ReportFoot(
        var content: String?,
        var photoPath: MutableList<String>?
)

data class TopicMsg(
        var id: String?,
        var topicId: String?,
        var userId: String?,
        var beUserId: String?,
        var commentType: Int?,
        var isRead: Int?,
        var createTime: String?,
        var userName: String?,
        var userPicture: String?,
        var userSex: Int?,
        var topicContent: String?,
        var topicpicture: String?,
        var unReadCount: Int?
)

data class GradeResult(
        var level: Int?,
        var rate: Double?,
        var disparity: Int?
)

data class UserIncome(
        var userName: String?,
        var number: Double?,
        var userPictrue: String?,
        var date: String?,
        var time: String?
)

data class UserIncomeBean(
        var date: String?,
        var userIncomes: MutableList<UserIncome>?
)

data class FamilyIncome(
        override val isHeader: Boolean,
        var date: String?,
        var userIncome: UserIncome?
) : SectionEntity

data class SystemNotic(
        var id: String?,
        var notifyTitle: String?,
        var notifyContent: String?,
        var creatorId: String?,
        var createTime: String?,
        var active: Int?
)

data class SeachRoomEntty(
        var roomId: String?,
        var userId: String?,
        var userName: String?,
        var userPictureUrl: String?,
        var userSex: String?,
        var roomStatus: String?,
        var room: RoomEntity?
)

data class RoomEntity(
        var roomId: String?,
        var roomName: String?,
        var roomTypeName: String?,
        var roomTypeColor: String?,
        var personCount: Int?,
        var userId: String?,
        var userPictureUrl: String?,
        var userName: String?,
        var roomLock: String?,
        var roomPassword: String?
)

data class HomeSeachEntity(
        var id: String?,
        var userNumber: Int?,
        var userName: String?,
        var userPhone: String?,
        var userBirthday: String?,
        var userSex: Int?,
        var userPicture: String?)

data class SignRewardEntity(
        var id: String?,
        var signDate: Int?,
        var signExperience: Int?,
        var signPicture: String?,
        var rewardType: Int?,
        var rewardNumber: Int?,
        var rewardId: String?,
        var deck: Decks?,
        var gift: GiftsResult?,
        var isOpen: Boolean = false
)

data class SignDayEntity(
        var days: Int?,
        var hasSign: Int?
)

data class CustomerServiceEntity(
        var id: String?,
        var servePhone: String?,
        var serveWechat: String?,
        var createTime: String?,
        var active: Int?
)

data class QueryRoomEntity(
        var room: RoomEtity?,
        var status: Int?
)

data class RoomEtity(
        var id: String?,
        var roomTypeId: String?,
        var roomName: String?,
        var createUserId: String?,
        var roomOwnerId: String?,
        var roomLock: Int?,
        var roomPassword: String?,
        var roomStatus: Int?
)

data class ShareRoom(
        var roomId: String?,
        var roomName: String?,
        var roomPicture: String?
) : Serializable

data class CashEntity(
        var userId: String?,
        var cashAccount: Int?,
        var jewelNumber: Int?,
        var cashMode: Int?,
        var accountNo: String?,
        var cashStatus: Int?,
        var createTime: String?,
        var active: Int?,
        var userName: String?,
        var userNumber: String?,
        var date: String?,
        var time: String?,
        val coinNumber: Double,
        val amount: Double,
        val payType: Int,
        val transactionNumber: String,
        var reason:String?,
        var bankType:String?
) : BaseResult()

data class CashBean(
        var date: String?,
        var cashResult: MutableList<CashEntity>?
)

data class CashMuListEntity(
        override val isHeader: Boolean = false,
        var date: String?,
        var result: CashEntity?

) : JSectionEntity()

data class GoldRecordEntity(
        val active: Int,
        val amount: Double,
        val coinNumber: Double,
        val createTime: String,
        val date: String,
        val id: Any,
        val isBeautiful: Any,
        val payType: Int,
        val rechargeCount: Any,
        val time: String,
        val transactionNumber: String,
        val userId: String,
        val userName: String,
        val userNumber: Any,
        val withdrawalCount: Any
)

data class BackstageRoom(
        var roomId: String,
        var roomBg: String
)

data class GuidBean(
        var index: Int,
        var guidaImage: Int
)

data class PayResultEntity(
    val alipay_trade_app_pay_response: AlipayTradeAppPayResponse,
    val sign: String,
    val sign_type: String
)

data class AlipayTradeAppPayResponse(
    val app_id: String,
    val auth_app_id: String,
    val charset: String,
    val code: String,
    val msg: String,
    val out_trade_no: String,
    val seller_id: String,
    val timestamp: String,
    val total_amount: String,
    val trade_no: String
)

data class BindEntity(
    val account: String,
    val bankName: String,
    val bankType: String,
    val realName: String,
    val type: Int
):BaseResult()