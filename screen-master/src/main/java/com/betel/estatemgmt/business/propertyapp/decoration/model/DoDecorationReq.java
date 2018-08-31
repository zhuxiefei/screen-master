package com.betel.estatemgmt.business.propertyapp.decoration.model;

import com.betel.estatemgmt.business.userapp.decoration.code.DecorationCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DoDecorationReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/24 15:12 <br/>
 * Version: 1.0 <br/>
 */
public class DoDecorationReq {

    @NotNull(message = DecorationCode.RECORDID_NULL)
    private String recordId;

    @NotNull(message = DecorationCode.STATUS_NULL)
    private String status;

    @Size(max = 500, message = DecorationCode.REASON_FORMAT)
    private String reason;

    @Size(max = 500, message = DecorationCode.DESCRIPTION_FORMAT)
    private String description;

    @Size(max = 500, message = DecorationCode.CANCELREASON_FORMAT)
    private String cancelReason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoDecorationReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", status=").append(status);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", cancelReason='").append(cancelReason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
