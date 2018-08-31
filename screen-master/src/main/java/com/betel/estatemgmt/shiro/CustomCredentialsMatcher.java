package com.betel.estatemgmt.shiro;

import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * <p>
 * 自定义密码凭证匹配器
 * </p>
 * ClassName: CustomCredentialsMatcher <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/12 9:44 <br/>
 * Version: 1.0 <br/>
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    private static Logger LOG = Logger.getLogger(CustomCredentialsMatcher.class);
    /**
     * <p>
     * 密码匹配
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 15:54
     *
     * @param authcToken
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //获取数据库中密码
        Object accountCredentials = getCredentials(info);
        //比较结果
        boolean isMatch = false;
        //将用户输入的密码AES加密
        try{
            String password = AESUtil.encrypt(String.valueOf(token.getPassword()));
            //返回匹配结果
            isMatch =  equals(password, accountCredentials.toString());
        }catch (Exception e){
            LOG.error("========doCredentialsMatch error========",e);
        }
        return isMatch;
    }
}
