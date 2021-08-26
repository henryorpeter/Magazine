package com.juguo.magazine.bean;

import com.google.gson.annotations.SerializedName;

public class AppIdBean {

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
        @SerializedName("bugCreator")
        private String bugCreator;
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
        @SerializedName("orderNo")
        private int orderNo;
        @SerializedName("packageName")
        private String packageName;
        @SerializedName("productLeader")
        private String productLeader;
        @SerializedName("source")
        private String source;
        @SerializedName("startAdFlag")
        private String startAdFlag;
        @SerializedName("tencent")
        private String tencent;
        @SerializedName("uiLeader")
        private String uiLeader;
        @SerializedName("version")
        private String version;
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

        public String getBugCreator() {
            return bugCreator;
        }

        public void setBugCreator(String bugCreator) {
            this.bugCreator = bugCreator;
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

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getProductLeader() {
            return productLeader;
        }

        public void setProductLeader(String productLeader) {
            this.productLeader = productLeader;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStartAdFlag() {
            return startAdFlag;
        }

        public void setStartAdFlag(String startAdFlag) {
            this.startAdFlag = startAdFlag;
        }

        public String getTencent() {
            return tencent;
        }

        public void setTencent(String tencent) {
            this.tencent = tencent;
        }

        public String getUiLeader() {
            return uiLeader;
        }

        public void setUiLeader(String uiLeader) {
            this.uiLeader = uiLeader;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVivo() {
            return vivo;
        }

        public void setVivo(String vivo) {
            this.vivo = vivo;
        }
    }
}
