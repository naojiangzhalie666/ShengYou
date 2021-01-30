package com.xiaoshanghai.nancang.net.bean;

public class RecordResult extends BaseResult {

    private String id;                  //	申请表id
    private String clanName;            //	申请家族名称
    private String applicantId;         //	申请者id
    private String applyTime;           //	申请时间
    private String approverId;          //	审核者id
    private String auditTime;           //	审核时间
    private String auditReason;         //	审核意见（拒绝时必填）
    private int applyStatus;            //	申请状态 0审核中1审核通过 2审核拒绝
    private String approverName;        //  审核者名称


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
}
