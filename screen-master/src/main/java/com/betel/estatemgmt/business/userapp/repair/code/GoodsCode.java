package com.betel.estatemgmt.business.userapp.repair.code;

/**
 * <p>
 *
 * </p>
 * ClassName: GoodsCode <br/>
 * Author: zhouye  <br/>
 * Date: 2017/8/03 18:16 <br/>
 * Version: 1.0 <br/>
 */
public interface GoodsCode {

    /*
    商品被删除
     */
    String GOODS_DELETE = "G0100";

    /*
    商品被驳回
     */
    String GOODS_RULES = "G0101";

    /*
    物品ID为空
     */
    String GOODSID_NULL = "G0102";

    /*
    物品标题格式错误，不能出现英文的\%<>’”长度不能超过20
     */
    String GOODS_TITLES = "G0103";

    /*
    物品描述不能超过200字
     */
    String GOODS_DES = "G0104";

    /*
    物品价格格式错误，不能为负，格式为0.00
     */
    String GOODS_PRICE_FORMAT = "G0105";

    /*
    联系人昵称格式错误，不能出现英文的\%<>’”长度为2-15字
     */
    String CONCAT_NAME = "G0106";

    /*
    联系人电话格式错误，不能出现非数字，长度为11位。只能以13,14,15,17,18开头
     */
    String CONCAT_PHONE_FORMAT = "G0107";

    /*
    图片最多上传九张
     */
    String IAMGE_OUTOFNUM ="G0108";

    /*
    图片大小过大
     */
    String IMAGE_SIZE_LARGE = "G0109";

    /*
    图片格式错误
     */
    String IMAGE_FORMAT_WRONG = "G0110";

    /*
    图片为空
     */
    String  IMAGE_NULL = "G0111";

    /*
    商品标题为空
     */
    String GOODS_TITLE_NULL = "G0117";

    /*
    商品价格为空
     */
    String GOODS_PRICE_NULL = "G0113";

    /*
    物品联系人姓名为空
     */
    String CONCAT_NAME_NULL = "G0114";

    /*
    物品联系人号码为空
     */
    String CONCAT_PHONE_NULL = "G0115";

    /*
    搜索关键字格式错误[最好说明一下关键字的格式什么样的   ok],不可以含有英文的\<>'"%等字符,长度不可大于20
     */
    String KEYWORD_FORMAT = "G0116";

    /*
    商品留言过长
   */
    String GOODSREPLY_OUTLENGTH = "G0118";
    /*
    商品留言为空
    */
    String GOODSREPLY_NULL = "G0119";

}
