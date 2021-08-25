package com.juguo.magazine.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesListBean implements Parcelable {

    @SerializedName("code")
    private String code;
    @SerializedName("list")
    private List<Favorites> favorites;
    @SerializedName("msg")
    private String msg;
    @SerializedName("total")
    private int total;

    protected FavoritesListBean(Parcel in) {
        code = in.readString();
        favorites = in.createTypedArrayList(Favorites.CREATOR);
        msg = in.readString();
        total = in.readInt();
    }

    public static final Creator<FavoritesListBean> CREATOR = new Creator<FavoritesListBean>() {
        @Override
        public FavoritesListBean createFromParcel(Parcel in) {
            return new FavoritesListBean(in);
        }

        @Override
        public FavoritesListBean[] newArray(int size) {
            return new FavoritesListBean[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeTypedList(favorites);
        dest.writeString(msg);
        dest.writeInt(total);
    }

    public static class Favorites implements Parcelable {
        @SerializedName("baseShareTimes")
        private int baseShareTimes;
        @SerializedName("baseThumbTimes")
        private int baseThumbTimes;
        @SerializedName("content")
        private String content;
        @SerializedName("contentType")
        private String contentType;
        @SerializedName("coverImgUrl")
        private String coverImgUrl;
        @SerializedName("id")
        private String id;
        @SerializedName("leafNodeCount")
        private int leafNodeCount;
        @SerializedName("level")
        private int level;
        @SerializedName("name")
        private String name;
        @SerializedName("orderNo")
        private int orderNo;
        @SerializedName("parentId")
        private String parentId;
        @SerializedName("shareTime")
        private String shareTime;
        @SerializedName("shareTimes")
        private int shareTimes;
        @SerializedName("shared")
        private int shared;
        @SerializedName("stDesc")
        private String stDesc;
        @SerializedName("star")
        private int star;
        @SerializedName("starTime")
        private String starTime;
        @SerializedName("starTimes")
        private int starTimes;
        @SerializedName("tagList")
        private List<TagList> tagList;
        @SerializedName("tags")
        private String tags;
        @SerializedName("tagsId")
        private String tagsId;
        @SerializedName("tagsName")
        private String tagsName;
        @SerializedName("thumbTimes")
        private int thumbTimes;
        @SerializedName("thumbUp")
        private int thumbUp;
        @SerializedName("thumbUpTime")
        private String thumbUpTime;
        @SerializedName("time")
        private int time;
        @SerializedName("type")
        private String type;
        @SerializedName("viewTimes")
        private int viewTimes;
        @SerializedName("watchTime")
        private String watchTime;
        @SerializedName("watched")
        private int watched;

        protected Favorites(Parcel in) {
            baseShareTimes = in.readInt();
            baseThumbTimes = in.readInt();
            content = in.readString();
            contentType = in.readString();
            coverImgUrl = in.readString();
            id = in.readString();
            leafNodeCount = in.readInt();
            level = in.readInt();
            name = in.readString();
            orderNo = in.readInt();
            parentId = in.readString();
            shareTime = in.readString();
            shareTimes = in.readInt();
            shared = in.readInt();
            stDesc = in.readString();
            star = in.readInt();
            starTime = in.readString();
            starTimes = in.readInt();
            tags = in.readString();
            tagsId = in.readString();
            tagsName = in.readString();
            thumbTimes = in.readInt();
            thumbUp = in.readInt();
            thumbUpTime = in.readString();
            time = in.readInt();
            type = in.readString();
            viewTimes = in.readInt();
            watchTime = in.readString();
            watched = in.readInt();
        }

        public static final Creator<Favorites> CREATOR = new Creator<Favorites>() {
            @Override
            public Favorites createFromParcel(Parcel in) {
                return new Favorites(in);
            }

            @Override
            public Favorites[] newArray(int size) {
                return new Favorites[size];
            }
        };

        public int getBaseShareTimes() {
            return baseShareTimes;
        }

        public void setBaseShareTimes(int baseShareTimes) {
            this.baseShareTimes = baseShareTimes;
        }

        public int getBaseThumbTimes() {
            return baseThumbTimes;
        }

        public void setBaseThumbTimes(int baseThumbTimes) {
            this.baseThumbTimes = baseThumbTimes;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLeafNodeCount() {
            return leafNodeCount;
        }

        public void setLeafNodeCount(int leafNodeCount) {
            this.leafNodeCount = leafNodeCount;
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

        public String getShareTime() {
            return shareTime;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public int getShareTimes() {
            return shareTimes;
        }

        public void setShareTimes(int shareTimes) {
            this.shareTimes = shareTimes;
        }

        public int getShared() {
            return shared;
        }

        public void setShared(int shared) {
            this.shared = shared;
        }

        public String getStDesc() {
            return stDesc;
        }

        public void setStDesc(String stDesc) {
            this.stDesc = stDesc;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getStarTime() {
            return starTime;
        }

        public void setStarTime(String starTime) {
            this.starTime = starTime;
        }

        public int getStarTimes() {
            return starTimes;
        }

        public void setStarTimes(int starTimes) {
            this.starTimes = starTimes;
        }

        public List<TagList> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagList> tagList) {
            this.tagList = tagList;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTagsId() {
            return tagsId;
        }

        public void setTagsId(String tagsId) {
            this.tagsId = tagsId;
        }

        public String getTagsName() {
            return tagsName;
        }

        public void setTagsName(String tagsName) {
            this.tagsName = tagsName;
        }

        public int getThumbTimes() {
            return thumbTimes;
        }

        public void setThumbTimes(int thumbTimes) {
            this.thumbTimes = thumbTimes;
        }

        public int getThumbUp() {
            return thumbUp;
        }

        public void setThumbUp(int thumbUp) {
            this.thumbUp = thumbUp;
        }

        public String getThumbUpTime() {
            return thumbUpTime;
        }

        public void setThumbUpTime(String thumbUpTime) {
            this.thumbUpTime = thumbUpTime;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getViewTimes() {
            return viewTimes;
        }

        public void setViewTimes(int viewTimes) {
            this.viewTimes = viewTimes;
        }

        public String getWatchTime() {
            return watchTime;
        }

        public void setWatchTime(String watchTime) {
            this.watchTime = watchTime;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(baseShareTimes);
            dest.writeInt(baseThumbTimes);
            dest.writeString(content);
            dest.writeString(contentType);
            dest.writeString(coverImgUrl);
            dest.writeString(id);
            dest.writeInt(leafNodeCount);
            dest.writeInt(level);
            dest.writeString(name);
            dest.writeInt(orderNo);
            dest.writeString(parentId);
            dest.writeString(shareTime);
            dest.writeInt(shareTimes);
            dest.writeInt(shared);
            dest.writeString(stDesc);
            dest.writeInt(star);
            dest.writeString(starTime);
            dest.writeInt(starTimes);
            dest.writeString(tags);
            dest.writeString(tagsId);
            dest.writeString(tagsName);
            dest.writeInt(thumbTimes);
            dest.writeInt(thumbUp);
            dest.writeString(thumbUpTime);
            dest.writeInt(time);
            dest.writeString(type);
            dest.writeInt(viewTimes);
            dest.writeString(watchTime);
            dest.writeInt(watched);
        }

        public static class TagList {
            @SerializedName("code")
            private String code;
            @SerializedName("detail")
            private String detail;
            @SerializedName("id")
            private String id;
            @SerializedName("isLeaf")
            private int isLeaf;
            @SerializedName("level")
            private int level;
            @SerializedName("name")
            private String name;
            @SerializedName("orderNo")
            private int orderNo;
            @SerializedName("parentCode")
            private String parentCode;
            @SerializedName("parentId")
            private String parentId;
            @SerializedName("type")
            private String type;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public int getIsLeaf() {
                return isLeaf;
            }

            public void setIsLeaf(int isLeaf) {
                this.isLeaf = isLeaf;
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

            public String getParentCode() {
                return parentCode;
            }

            public void setParentCode(String parentCode) {
                this.parentCode = parentCode;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}

