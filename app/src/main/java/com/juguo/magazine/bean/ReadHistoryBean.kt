package com.juguo.magazine.bean


import com.google.gson.annotations.SerializedName

data class ReadHistoryBean(
    @SerializedName("code")
    val code: String,
    @SerializedName("list")
    val list: List<ReadHistory>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("total")
    val total: Int
) {
    data class ReadHistory(
        @SerializedName("baseShareTimes")
        val baseShareTimes: Int,
        @SerializedName("baseThumbTimes")
        val baseThumbTimes: Int,
        @SerializedName("content")
        val content: String,
        @SerializedName("contentType")
        val contentType: String,
        @SerializedName("coverImgUrl")
        val coverImgUrl: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("leafNodeCount")
        val leafNodeCount: Int,
        @SerializedName("level")
        val level: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("orderNo")
        val orderNo: Int,
        @SerializedName("parentId")
        val parentId: String,
        @SerializedName("shareTime")
        val shareTime: String,
        @SerializedName("shareTimes")
        val shareTimes: Int,
        @SerializedName("shared")
        val shared: Int,
        @SerializedName("stDesc")
        val stDesc: String,
        @SerializedName("star")
        val star: Int,
        @SerializedName("starTime")
        val starTime: String,
        @SerializedName("starTimes")
        val starTimes: Int,
        @SerializedName("tagList")
        val tagList: List<Tag>,
        @SerializedName("tags")
        val tags: String,
        @SerializedName("tagsId")
        val tagsId: String,
        @SerializedName("tagsName")
        val tagsName: String,
        @SerializedName("thumbTimes")
        val thumbTimes: Int,
        @SerializedName("thumbUp")
        val thumbUp: Int,
        @SerializedName("thumbUpTime")
        val thumbUpTime: String,
        @SerializedName("time")
        val time: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("viewTimes")
        val viewTimes: Int,
        @SerializedName("watchTime")
        val watchTime: String,
        @SerializedName("watched")
        val watched: Int
    ) {
        data class Tag(
            @SerializedName("code")
            val code: String,
            @SerializedName("detail")
            val detail: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("isLeaf")
            val isLeaf: Int,
            @SerializedName("level")
            val level: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("orderNo")
            val orderNo: Int,
            @SerializedName("parentCode")
            val parentCode: String,
            @SerializedName("parentId")
            val parentId: String,
            @SerializedName("type")
            val type: String
        )
    }
}