package com.juguo.magazine.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VersionUpdateBean {

    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private Result result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("appConfig")
        private String appConfig;
        @SerializedName("appId")
        private String appId;
        @SerializedName("detail")
        private String detail;
        @SerializedName("huawei")
        private String huawei;
        @SerializedName("id")
        private String id;
        @SerializedName("ifPay")
        private int ifPay;
        @SerializedName("millet")
        private String millet;
        @SerializedName("name")
        private String name;
        @SerializedName("oppo")
        private String oppo;
        @SerializedName("platform")
        private String platform;
        @SerializedName("recAccountList")
        private List<RecAccountList> recAccountList;
        @SerializedName("size")
        private int size;
        @SerializedName("startAdFlag")
        private String startAdFlag;
        @SerializedName("status")
        private String status;
        @SerializedName("tencent")
        private String tencent;
        @SerializedName("updateContent")
        private String updateContent;
        @SerializedName("updateV")
        private UpdateV updateV;
        @SerializedName("url")
        private String url;
        @SerializedName("vRemark")
        private String vRemark;
        @SerializedName("vType")
        private String vType;
        @SerializedName("version")
        private String version;
        @SerializedName("versionId")
        private String versionId;
        @SerializedName("vivo")
        private String vivo;

        public String getAppConfig() {
            return appConfig;
        }

        public void setAppConfig(String appConfig) {
            this.appConfig = appConfig;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getHuawei() {
            return huawei;
        }

        public void setHuawei(String huawei) {
            this.huawei = huawei;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIfPay() {
            return ifPay;
        }

        public void setIfPay(int ifPay) {
            this.ifPay = ifPay;
        }

        public String getMillet() {
            return millet;
        }

        public void setMillet(String millet) {
            this.millet = millet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOppo() {
            return oppo;
        }

        public void setOppo(String oppo) {
            this.oppo = oppo;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public List<RecAccountList> getRecAccountList() {
            return recAccountList;
        }

        public void setRecAccountList(List<RecAccountList> recAccountList) {
            this.recAccountList = recAccountList;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getStartAdFlag() {
            return startAdFlag;
        }

        public void setStartAdFlag(String startAdFlag) {
            this.startAdFlag = startAdFlag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTencent() {
            return tencent;
        }

        public void setTencent(String tencent) {
            this.tencent = tencent;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public UpdateV getUpdateV() {
            return updateV;
        }

        public void setUpdateV(UpdateV updateV) {
            this.updateV = updateV;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVRemark() {
            return vRemark;
        }

        public void setVRemark(String vRemark) {
            this.vRemark = vRemark;
        }

        public String getVType() {
            return vType;
        }

        public void setVType(String vType) {
            this.vType = vType;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getVivo() {
            return vivo;
        }

        public void setVivo(String vivo) {
            this.vivo = vivo;
        }

        public static class UpdateV {
            @SerializedName("appId")
            private String appId;
            @SerializedName("auditRemark")
            private String auditRemark;
            @SerializedName("channel")
            private String channel;
            @SerializedName("id")
            private String id;
            @SerializedName("onlineTime")
            private String onlineTime;
            @SerializedName("platform")
            private String platform;
            @SerializedName("remark")
            private String remark;
            @SerializedName("size")
            private int size;
            @SerializedName("status")
            private String status;
            @SerializedName("updateContent")
            private String updateContent;
            @SerializedName("updateType")
            private String updateType;
            @SerializedName("url")
            private String url;
            @SerializedName("version")
            private String version;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getAuditRemark() {
                return auditRemark;
            }

            public void setAuditRemark(String auditRemark) {
                this.auditRemark = auditRemark;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOnlineTime() {
                return onlineTime;
            }

            public void setOnlineTime(String onlineTime) {
                this.onlineTime = onlineTime;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUpdateContent() {
                return updateContent;
            }

            public void setUpdateContent(String updateContent) {
                this.updateContent = updateContent;
            }

            public String getUpdateType() {
                return updateType;
            }

            public void setUpdateType(String updateType) {
                this.updateType = updateType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }
        }

        public static class RecAccountList {
            @SerializedName("id")
            private String id;
            @SerializedName("ifSandbox")
            private int ifSandbox;
            @SerializedName("name")
            private String name;
            @SerializedName("payerType")
            private String payerType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIfSandbox() {
                return ifSandbox;
            }

            public void setIfSandbox(int ifSandbox) {
                this.ifSandbox = ifSandbox;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPayerType() {
                return payerType;
            }

            public void setPayerType(String payerType) {
                this.payerType = payerType;
            }
        }
    }
}
