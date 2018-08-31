package smartCouplet.common;

/**
 * <p>
 * 状态码
 * </p>
 * ClassName: ActiveUser <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 10:06 <br/>
 * Version: 1.0 <br/>
 */
public interface  StatusCode {

	/**
	 * 全局系统异常错误代码
	 */
	String FAILURE = "99999";

	/**
	 * 全局系统成功返回码
	 */
	String SUCCESS = "00000";

	/**
	 * 全局系统无权限返回码
	 */
	String UNAUTHORIZED = "11111";

	/**
	 * 账号被删除，用户再操作时，错误返回码
	 */
	String ACCOUNT_DELETE = "22222";

	/**
	 * 用户被禁言
	 */
	String ACCOUNT_BANTAIL = "44444";

	/**
	 * token失效，登录超时，重新登录
	 */
	String TOKEN_EXPIRE = "33333";

	/**
	 * 用户被禁止登陆
	 */
	String ACCOUNT_BANLOGIN = "55555";

}
