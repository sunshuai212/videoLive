package com.aihuishou.httplib.api;


/**
 * 类名称：ApiConstants
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/9
 * 描述：api相关常量
 */
public class ApiConstants {

    /**
     * baseUrl
     */
    public static String BASE_URL = "http://116.211.167.106/";
    /**
     * 其他baseUrl
     */
    public static String XUSER_URL = "http://192.168.1.45:8080/user-api";

    /**
     * 根据手机号获取图片验证码
     */
    public static final String GET_IMG_VERIFY_CODE = "genCaptchaCode";
    // SMS Code
    public static final String GET_SMS_CODE = "sendMesCode";
    public static final String GET_INDEX_CODE = "home";

    /*商品详情*/
    public static final String GET_PRODUCT_DETAIL = "product/detail";
    /*获取属性信息*/
    public static final String GET_PROPERTY_INFO = "product/sku/selector";

    //登录
    public static final String API_FOR_LOGIN = "apiForLogin";
    /*根据user_id获取用户登录流程*/
    public static final String API_FOR_LOGIN_STEP = "appLoginStep";
    /*根据user_id获取代扣跳转url*/
    public static final String API_FOR_WITHHOLD = "withhold/buildUrl";
    /*根据user_id获取信用套餐跳转url*/
    public static final String API_FOR_CREDIT = "ali/buildZhimaPackageUrl";
    /*根据user_token获取用户登录信息*/
    public static final String API_FOR_CHECK_LOGIN_STATUS = "checkUserLoginStatus";
    public static final String API_FOR_SAVE_ZHIMA_INFO = "saveZhimaInfo";
    public static final String API_FOR_BIND_ALI_FORM_APP = "bindAliFormAPP";
    /*订单保存接口*/
    public static final String API_FOR_TRADE_SAVE = "trade/save";
    /*订单展示接口*/
    public static final String API_FOR_TRADE_INDEX = "trade/index";
    /*每期金额动态接口*/
    public static final String API_FOR_TRADE_GETINSTALLMENTPRICE = "trade/getInstallmentPrice";

    public static final String GET_PROPERTY_INFO_API_URL = "activity/get-property-info";

    //商品列表
    public static final String GET_PRODUCT_LIST_API_URL = "product/list";

    //获取地址
    public static final String CITY_LIST = "trade/getRegions";
    //获取地址
    public static final String SAVE_DELIVERY_ADDRESS = "trade/saveDeliveryAddress";
    //保存花呗支付接口
    public static final String SAVE_HUABEI_PAY = "trade/saveHuabeiPay";
    //保存信用卡支付接口
    public static final String SAVE_LBF_PAY = "trade/saveLbfPay";

    //个人中心
    public static final String USER_INFO = "center/userInfo";//个人中心

    public static final String CHECK_UNBINDINFO = "center/checkUnbindInfo";   //支付宝代扣解绑 是否可以解绑


    public static final String UNBIND_PAY_WITH_HOLD = "center/unbindPayWithHold";   //支付宝代扣解绑

    public static final String GET_CANCEL_REASON = "center/cancelReason";   //获取订单取消原因列表
    public static final String CANCEL_ORDER = "center/cancelTrade";   //订单取消
    public static final String ORDER_DETAIL = "center/detail";   //订单详情
    public static final String ORDER_LIST = "center/list";   //订单列表
    public static final String CARD_LIST = "center/cardList";   //卡券包列表
    public static final String REPAIR_LIST = "center/repairlist";   //维修列表
    public static final String REFUND_LIST = "center/refundlist";   //退货列表
    public static final String RECOMMENT_LIST = "center/recommendList";   //推荐列表

    //首页
    public static final String HOME_PAGE = "home/page";   //首页
    public static final String HOME_CATEGORY_TYPE = "home/categoryType";   //分类信息接口
    public static final String HOME_MANUFACTURER = "home/manufacturerList";   //品牌信息接口
    public static final String HOME_CATEGORY = "home/category";   //分类列表接口
    public static final String HOME_STATIC_URL = "home/staticUrl";   //分类列表接口

    public static final String UPDATE_VERSION="version" ; //更新接口


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.AI_RENT_URL:
                host = BASE_URL;
                break;
            case HostType.XUSER_URL:
                host = XUSER_URL;
                break;
            default:
                host = BASE_URL;
                break;
        }
        return host;
    }
}
