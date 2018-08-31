package com.betel.estatemgmt.common.model.config;

public class Config {
    private String confName;

    private String confValue;

    private String confType;

    private String confDesc;

    /**
     * <p>
     * 关键属性构造方法
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/15 13:47
     *
     * @param confName  配置名称
     * @param confValue 配置信息
     */
    public Config(String confName, String confValue) {
        this.confValue = confValue;
        this.confName = confName;
    }

    /**
     * <p>
     * 全参数构造方法
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/15 16:13
     *
     * @param confName  配置名称
     * @param confValue 配置信息
     * @param confType  配置类型
     * @param confDesc  配置描述
     */
    public Config(String confName, String confValue, String confType, String confDesc) {
        this.confName = confName;
        this.confValue = confValue;
        this.confType = confType;
        this.confDesc = confDesc;
    }

    /**
     * <p>
     * 无参数构造方法
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/15 16:14
     */
    public Config() {

    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName == null ? null : confName.trim();
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue == null ? null : confValue.trim();
    }

    public String getConfType() {
        return confType;
    }

    public void setConfType(String confType) {
        this.confType = confType == null ? null : confType.trim();
    }

    public String getConfDesc() {
        return confDesc;
    }

    public void setConfDesc(String confDesc) {
        this.confDesc = confDesc == null ? null : confDesc.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("confName='").append(confName).append('\'');
        sb.append(", confValue='").append(confValue).append('\'');
        sb.append(", confType='").append(confType).append('\'');
        sb.append(", confDesc='").append(confDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}