package com.juguo.magazine.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author : Administrator
 * date : 2021/8/23 14:02
 * description :
 *
 * @Author : yangjinjing
 */
public class CategoryBean {

    @SerializedName("code")
    private String code;
    @SerializedName("list")
    private java.util.List<Category> Category;
    @SerializedName("msg")
    private String msg;
    @SerializedName("total")
    private int total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CategoryBean.Category> getCategory() {
        return Category;
    }

    public void setCategory(List<CategoryBean.Category> category) {
        Category = category;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class Category {
        @SerializedName("code")
        private String code;
        @SerializedName("dataCode")
        private String dataCode;
        @SerializedName("detail")
        private String detail;
        @SerializedName("id")
        private String id;
        @SerializedName("level")
        private int level;
        @SerializedName("name")
        private String name;
        @SerializedName("orderNo")
        private int orderNo;
        @SerializedName("parentId")
        private String parentId;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDataCode() {
            return dataCode;
        }

        public void setDataCode(String dataCode) {
            this.dataCode = dataCode;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
