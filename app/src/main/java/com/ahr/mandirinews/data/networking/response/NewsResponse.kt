package com.ahr.mandirinews.data.networking.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val news: List<NewsDto>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class NewsDto(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("source")
	val source: SourceDto? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

)
