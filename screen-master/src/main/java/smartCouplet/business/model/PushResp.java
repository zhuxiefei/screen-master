package smartCouplet.business.model;

/**
 * @Auther: xiayanxin
 * @Date: 2018/7/3/003 10:24
 * @Description:
 */
public class PushResp {

    /**
     * 1为门磁 2为燃气 3为水浸 4为烟雾 5为门禁
     */
    private String devType ;

    /**
     * 1为一级警报  2为二级警报
     */
    private String level;

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "PushResp{" +
                "devType='" + devType + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
