package smartCouplet.business.model;

/**
 * @Auther: xiayanxin
 * @Date: 2018/6/26/026 09:48
 * @Description:
 */
public class Device {

    private String openId;

    private String deviceId;

    private String devicePasswd;

    private String deviceType;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePasswd() {
        return devicePasswd;
    }

    public void setDevicePasswd(String devicePasswd) {
        this.devicePasswd = devicePasswd;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "Device{" +
                "openId='" + openId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", devicePasswd='" + devicePasswd + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
