package smartCouplet.business.model;

/**
 * @Auther: xiayanxin
 * @Date: 2018/6/25/025 16:16
 * @Description:
 */
public class GatewayTop {

    private String cmd;

    private String gwID;

    private String time;

    private Integer sn;

    private String messageCode;

    private String name;

    private String type;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getGwID() {
        return gwID;
    }

    public void setGwID(String gwID) {
        this.gwID = gwID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GatewayTop{" +
                "cmd='" + cmd + '\'' +
                ", gwID='" + gwID + '\'' +
                ", time='" + time + '\'' +
                ", sn=" + sn +
                ", messageCode='" + messageCode + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
