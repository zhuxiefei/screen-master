package com.betel.estatemgmt.utils;

import com.betel.estatemgmt.common.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 敏感词过滤工具
 * </p>
 * ClassName: IllegalwordUtil <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/8/4 17:18 <br/>
 * Version: 1.0 <br/>
 */
public class IllegalwordUtil {

    private static final Logger LOG = LoggerFactory.getLogger(IllegalwordUtil.class);

    /**
     * <p>
     * list转map
     * </p>
     * Author: xiayanxin <br/>
     * Date: ` 17:20
     *
     * @param illegalwordList list
     * @return Map<Character,List<String>> map
     */
    private static Map<Character, List<String>> wordListToMap(List<String> illegalwordList) {
        Map<Character, List<String>> result = new HashMap<Character, List<String>>();
        for (String s : illegalwordList) {
            s = s.trim();
            char c = s.charAt(0);
            List<String> strs = result.get(c);
            if (strs == null) {
                strs = new ArrayList<String>();
                result.put(c, strs);
            }
            strs.add(s);
        }

        return result;
    }


    /**
     * <p>
     * 敏感词过滤工具
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/4 17:25
     *
     * @param src 过滤内容
     * @return 过滤后的内容
     */
    public static String wordFilter(String src) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("======IllegalwordUtil wordFilter start======src=" + src);
        }
        //从redis中获取敏感词列表
        List<String> illegalwordList = RedisManager.findAllSensitiveWords();
        if (LOG.isDebugEnabled()) {
            LOG.debug("======wordFilter listFromRedis======illegalwordList=" + illegalwordList);
        }
        if (illegalwordList != null && illegalwordList.size() > 0) {
            //将list转map
            Map<Character, List<String>> wordMap = null;
            try {
                wordMap = wordListToMap(illegalwordList);
            } catch (Exception e) {
                LOG.error("======wordListToMap error======", e);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("======wordListToMap======wordMap=" + wordMap);
            }
            StringBuilder strb = new StringBuilder();
            try {
                for (int i = 0; i < src.length(); i++) {
                    char c = src.charAt(i);
                    String find = null;
                    if (wordMap.containsKey(c)) {
                        List<String> words = wordMap.get(c);
                        for (String s : words) {
                            String temp = src.substring(i, (s.length() <= (src.length() - i)) ? i + s.length() : i);
                            if (s.equals(temp)) {
                                find = s;
                                break;
                            }
                        }
                    }
                    if (find != null) {
                        strb.append("**");
                        i += (find.length() - 1);
                    } else {
                        strb.append(c);
                    }
                }
            } catch (Exception e) {
                LOG.error("======wordFilter error======", e);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("======IllegalwordUtil wordFilter end======src=" + strb.toString());
            }
            return strb.toString();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("======IllegalwordUtil wordFilter end======src=" + src);
        }
        return src;
    }
}
