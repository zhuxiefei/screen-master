import com.betel.estatemgmt.business.web.task.util.GetDateUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/9/009.
 */
public class test {

    public static void main(String[] args)throws Exception {
        System.out.println(AESUtil.encrypt("1"));
    }
}
