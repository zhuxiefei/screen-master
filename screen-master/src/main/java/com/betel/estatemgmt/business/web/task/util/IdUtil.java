package com.betel.estatemgmt.business.web.task.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: IdUtil <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/26 11:42 <br/>
 * Version: 1.0 <br/>
 */
public class IdUtil {

    private static int sequence = 0;
    private static int length = 6;

    private static final Logger LOG = LoggerFactory.getLogger(IdUtil.class);

    /**
     * <p>
     * 标识+YYYYMMDDHHMMSS+6位自增长码(20位) 
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/11 9:37
     * @return String 自增序列号
     * */
    public static synchronized String getFlowNo(){
        sequence = sequence >= 999999 ? 1 : sequence+1;
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String s = Integer.toString(sequence);
        return "RW" + dateTime + addLeftZero(s,length);
    }

    /**
     * <p>
     * 左填0
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/11 9:35
     *
     * @param s  自增数
     * @param length 总长度
     * @return String 填充后的数
     * */
    public static String addLeftZero(String s,int length){
        int old = s.length();
        if(length > old){
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length){
                LOG.error("==========addLeftZero error=========","length is over");
            }
            int lim = c.length - x.length;
            for (int i=0; i<lim; i++){
                c[i] = '0';
            }
            System.arraycopy(x,0,c,lim,x.length);
            return new String(c);
        }
        return s.substring(0,length);
    }
}
