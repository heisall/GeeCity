package com.geecity.hisenseplus.home.utils;

/**
 * 访问网络参数所用类
 * */
public class WebConstants {

	public static String sServiceUrl;
	public static boolean DEBUG;
	// 微信的appid
	public static final String WXAPP_APPID = "wx706df433748af20c";
	public static final String WXAPP_APPSECRET = "f78e80ec755ab99be5a7b1e3565b5f37";
	//新房详情页分享
	public static final String SHARE_URL_ESTATE_NEW = "http://admin.hisenseplus.com/win/t_cm_roomhuxing.aspx?id=";
	//物业监督详情页分享
	public static final String SHARE_URL_WYJD = "http://admin.hisenseplus.com/win/t_cm_supervisedetail.aspx?id=";
	//发现详情页分享
	public static final String SHARE_URL_DISC = "http://admin.hisenseplus.com/win/t_cm_finddetail.aspx?id=";

	// 我的发现
	public static final String METHOD_MY_DISC_LIST = "user/myFind";
	// 我的建议
	public static final String METHOD_USER_MY_ADVICE = "user/adviseSubmit";
	// 我的租售_出租
	public static final String METHOD_USER_MY_RENT = "user/myRents";
	// 我的租售_出售
	public static final String METHOD_USER_MY_SALE = "user/mySales";
	// 情感状况
	public static final String METHOD_USER_INFOR_MOTION = "user/findEmotions";
	// 兴趣爱好
	public static final String METHOD_USER_INFOR_HOBBY = "user/findHobbies";
	// 用户信息
	public static final String METHOD_USER_INFOR = "user/changeInfo";
	// 注册
	public static final String METHOD_REGISTER = "user/register";
	// 登录
	public static final String METHOD_LOGIN = "user/userLogin";
	// 忘记密码
	public static final String METHOD_RESET_PWD = "user/resetPass";
	// 修改密码
	public static final String METHOD_CHANGEPASS = "user/changePass";
	// 签到
	public static final String METHOD_USER_SIGNIN = "user/signIn";
	// 签到的广告
	public static final String METHOD_SIGN_INDEX = "index/signIndex";
	// 绑定房源-获取城市列表
	public static final String METHOD_GET_CITY = "build/getCitys";
	// 绑定房源-获取小区列表
	public static final String METHOD_GET_AREA = "build/getSubdistrict";
	// 绑定房源-获取楼号列表
	public static final String METHOD_GET_BUILDING = "build/getBuildings";
	// 绑定房源-获取单元和房间列表
	public static final String METHOD_GET_ROMES = "build/getRomes";
	// 绑定房源-获取户主列表
	public static final String METHOD_GET_OWNERS = "build/infoValidate";
	// 绑定房源
	public static final String METHOD_LOCK_HOME = "build/lockRooms";
	// 获取首页数据
	public static final String METHOD_GET_HOMEPAGE = "index/ads";
	// 获取管家首页数据
	public static final String METHOD_GET_MANAGER_HOMEPAGE = "keep/keepIndex";
	// 发现
	public static final String METHOD_DISC_LIST = "find/findIndex";
	// 发现详情
	public static final String METHOD_DISC_DETAIL = "find/findDetail";
	// 发现详情评论
	public static final String METHOD_DISC_DETAIL_COMMENTS = "user/publishComments";
	// 新增发现
	public static final String METHOD_DISC_ADD = "user/saveFind";
	// 通知公告
	public static final String METHOD_GET_NOTICE = "act/findActPage";
	// 通知公告详情
	public static final String METHOD_GET_NOTICE_DETAIL = "act/actDetail";
	// 活动报名
	public static final String METHOD_ENROLL_ACT = "act/enrollAct";
	// 物业监督首页
	public static final String METHOD_SUP_SUPINDEX = "sup/supIndex";
	// 物业监督详情
	public static final String METHOD_SUP_DETAIL = "sup/supDetail";
	// 新楼盘列表
	public static final String METHOD_ESTATE_NEW_LIST = "house/newHouseIndex";
	// 新楼盘详情
	public static final String METHOD_ESTATE_NEW_DETAIL = "house/houseDetail";
	// 楼盘收藏
	public static final String METHOD_ESTATE_COLLECT = "house/houseCollect";
	// 二手房列表
	public static final String METHOD_ESTATE_SENDHAND_LIST = "house/oldHouseList";
	// 二手房详情
	public static final String METHOD_ESTATE_SENDHAND_DETAIL = "house/oldHouseDetail";
	// 二手房发布
	public static final String METHOD_ESTATE_SAVE_OLD_HOUSE = "house/saveOldHouse";
	// 租赁房屋列表
	public static final String METHOD_ESTATE_RENT_LIST = "house/rentHouseList";
	// 租赁房屋详情
	public static final String METHOD_ESTATE_RENT_DETAIL = "house/rentHouseDetail";
	// 租房发布
	public static final String METHOD_ESTATE_SAVER_ENT_HOUSE = "house/saveRentHouse";
	// 租房房屋信息登记筛选条件
	public static final String METHOD_ESTATE_ADD_HOUSE = "house/addRentHouse";
	// 二手房房屋信息登记筛选条件
	public static final String METHOD_ESTATE_ADD_OLD_HOUSE = "house/addOldHouse";
	// 二手房筛选条件
	public static final String METHOD_ESTATE_HOUSEFILTER = "house/houseFilter";
	// 租房筛选条件
	public static final String METHOD_ESTATE_RENTFILTER = "house/rentFilter";
	// 报修/投诉列表
	public static final String METHOD_BILL_MYCOMPLAINS = "bill/myComplains";
	// 报修/投诉新增
	public static final String METHOD_BILL_SAVECOMPLAIN = "bill/complainSave";
	// 报修/投诉详情
	public static final String METHOD_BILL_DETAIL = "bill/complainDetail";
	// 报修/投诉评价
	public static final String METHOD_BILL_SAVE_EVALUATE = "bill/saveEvaluate";
	// 物业账单
	public static final String METHOD_BILL_FINDBILLS = "bill/findBills";
	// 生活首页
	public static final String METHOD_LIFE_HOMEPAGE = "life/lifeIndex";
	// 生活信息首页
	public static final String METHOD_LIFEINFO_HOMEPAGE = "life/onDoorIndex";
	// 团购列表，仅用于首页进入团购界面
	public static final String METHOD_GROUPBUY_LIST = "life/groupBuying";
	// 积分列表
	public static final String METHOD_SCORE_GOODS_LIST = "order/scoreGoodsList";
	// 商户列表
	public static final String METHOD_LIFE_BUSINESS_LIST = "life/merchantList";
	// 获取四大类（上门、商户、食品、家装）相应的全部分类
	public static final String METHOD_LIFE_MORE_LIST = "life/classifyIndex";
	// 商户详情
	public static final String METHOD_LIFE_STORED_ETAIL = "life/storeDetail";
	// 商品详情
	public static final String METHOD_LIFE_GOODS_ETAIL = "life/goodsDetail";
	// 加入或删除购物车
	public static final String METHOD_ORDER_ADDCART = "order/addCart";
	// 获取购物车
	public static final String METHOD_ORDER_CAETLIST = "order/cartList";
	// 批量删除购物车
	public static final String METHOD_ORDER_BEACHDELETE = "order/beachDelete";
	// 获取收货地址
	public static final String METHOD_ORDER_ADDRESSLIST = "order/addressList";
	// 删除收货地址
	public static final String METHOD_ORDER_DEL_ADDRESS = "order/delAddress";
	// 在线预约
	public static final String METHOD_ORDER_BOOKONLINE = "order/bookOnLine";
	// 获取订单结算的地址信息和配送方式信息
	public static final String METHOD_ORDER_GET_ORDER_INFO = "order/addOrder";
	// 从购物车提交订单
	public static final String METHOD_ORDER_SAVE = "order/saveOrder";
	// 直接购买提交订单
	public static final String METHOD_ORDER_DIRECTBUY = "order/directBuy";
	// 提交订单
	public static final String METHOD_ORDER_ADD_ADDRESS = "order/manegerAddress";
	// 提交订单
	public static final String METHOD_ORDER_MYORDERS = "order/myOrders";
	// 订单详情
	public static final String METHOD_ORDER_DETAILS = "order/orderDetails";
	// 订单评价
	public static final String METHOD_EVALUATE_GOODS = "order/evaluateGoods";
	// 修改订单状态
	public static final String METHOD_ORDER_CHANGE_STATUS = "order/changeOrderStatus";
	// 修改物业缴费订单状态
	public static final String METHOD_BILL_CHANGEORDER = "bill/changeOrder";
	// 删除订单
	public static final String METHOD_ORDER_DEL_DETAILS = "order/delOrder";
	// 生成微信预付订单
	public static final String METHOD_ORDER_PERPAY = "order/perPay";
	// 检测更新
	public static final String METHOD_UPDATE_METHED = "user/findAppVersion";
	// 我的房产
	public static final String METHOD_BUILD_MYROOMS = "build/myRooms";
	// 设置默认房产
	public static final String METHOD_BUILD_CHANGEDEFAULT = "build/changeDefault";
	// 我的消息
	public static final String METHOD_USER_MESAAGE = "user/myMsg";
	// 分享
	public static final String METHOD_SHARE = "user/modifyFind";
    // 商城优惠广告接口
    public static final String METHOD_ORDER_FAVOURABLE = "order/favourable";
    // 设置默认收件地址
    public static final String METHOD_MANEGER_ADDRESS = "order/manegerAddress";
    // 物业缴费生成预订单
    public static final String METHOD_BILL_PERPAY = "bill/perPay";

	public enum RequestType {
		/**
		 * 版本更新
		 * */
		VERSIONCHECK,
		/**
		 * 登录
		 */
		LOGIN
	}
}
