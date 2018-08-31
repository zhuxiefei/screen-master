package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: MemberNameSupport <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/21 14:30 <br/>
 * Version: 1.0 <br/>
 */
public class MemberNameSupport {
    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberNameSupport{");
        sb.append("memberName='").append(memberName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
