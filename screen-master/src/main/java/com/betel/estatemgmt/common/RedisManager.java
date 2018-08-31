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

package com.betel.estatemgmt.common;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * <p>
 * Redis管理器
 * </p>
 * ClassName: RedisManager <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/16 15:07 <br/>
 * Version: 1.0 <br/>
 */
public class RedisManager {

    private static final Logger LOG = LoggerFactory.getLogger(RedisManager.class);

    private static JedisPool pool = null;
    private static final String HOST = ConfigManager.read(ConfigName.REDIS_HOST); // 地址
    private static final String PORT = ConfigManager.read(ConfigName.REDIS_PORT); // 端口号
    private static final String PASSWORD = ConfigManager.read(ConfigName.REDIS_PASSWORD); // 密码
    private static final int TIMEOUT = 5000; // 读取超时等待时间

    //连接Redis数据库
    static {
        if (LOG.isInfoEnabled()) {
            LOG.info("========redis connect star========");
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(300);
            config.setMaxTotal(600);
            config.setMaxWaitMillis(1000);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, HOST, Integer.parseInt(PORT), TIMEOUT, PASSWORD);
        } catch (Exception e) {
            LOG.error("========redis connect error========", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========redis connect success!========");
        }
    }

    /**
     * <p>
     * 追加，当key存在时，将value追加到当前value之后
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 10:57
     *
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间
     */
    public static void append(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.append(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis append error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * <p>
     * 新增，若数据库中存在则覆盖
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 10:59
     *
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间
     */
    public static void add(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //先删除
            delete(key);
            //再追加
            jedis.append(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis add error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * <p>
     * 刷新失效时间
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 10:59
     *
     * @param key        键
     * @param expireTime 失效时间
     */
    public static void expire(String key, int expireTime){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis expire error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * <p>
     * 根据key获取value
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:06
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            LOG.error("========redis get error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * <p>
     * 根据key模糊查找
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:06
     *
     * @param key 键
     * @return 值集合
     */
    public static Set<String> findKey(String key) {
        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            set = jedis.keys("*" + key + "*");
        } catch (Exception e) {
            LOG.error("========redis findKey error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return set;
    }

    /**
     * <p>
     * 删除
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:07
     *
     * @param key 键
     */
    public static void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            LOG.error("========redis delete error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 查询redis帖子浏览次数
     *
     * @return 返回map集合
     */
    public static Map<Long, Integer> findAllTopicView() {
        Map<Long, Integer> map = new HashMap<>();
        Jedis jedis = null;
        Set<String> set;
        Long topicId;
        try {
            jedis = pool.getResource();
            set = jedis.keys("tp*");
            for (String str : set) {
                topicId = Long.valueOf(str.substring(2, str.length()));
                map.put(topicId, Integer.valueOf(jedis.get(str)));
            }
        } catch (Exception e) {
            LOG.error("========redis findAllTopicView error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return map;
    }

    /**
     * <p>
     * 通过pipeline添加
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/7/13 9:51
     *
     * @param key        键
     * @param value      值
     */
    public static void addPipe(String key,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            //先删除
            delete(key);
            //再追加
            Pipeline pp = jedis.pipelined();
            pp.set(key,value);
        } catch (Exception e){
            LOG.error("=======redis add error=======",e);
        } finally {
            //jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if(null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * <p>
     * 获取redis中的敏感词汇
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/7/12 14:05
     *
     * @return 返回List集合
     */
    public static List<String> findAllSensitiveWords() {
        List<String> list = new ArrayList<>();
        Jedis jedis = null;
        String listToString = null;
        try {
            jedis = pool.getResource();
            listToString = jedis.get("ILLEGALWORDS");
            if(listToString != null && listToString.length() > 2){
                String newString = listToString.substring(1,listToString.length()-1);
                String[] array = newString.split(",");
                list =java.util.Arrays.asList(array);
            }
        } catch (Exception e) {
            LOG.error("========redis findAllSensitiveWords error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return list;
    }

}
