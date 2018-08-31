/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package smartCouplet.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: BeanFactory <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 14:28 <br/>
 * Version: 1.0 <br/>
 */
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> className) {
        return context.getBean(className);
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> className) {
        return context.getBean(beanName, className);
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        setApplicationCx(appContext);
    }

    public static void setApplicationCx(ApplicationContext appContext) {
        context = appContext;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
