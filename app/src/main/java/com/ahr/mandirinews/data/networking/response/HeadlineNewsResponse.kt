package com.ahr.mandirinews.data.networking.response

import com.google.gson.annotations.SerializedName

data class HeadlineNewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val headlineNews: List<HeadlineNewsDto>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class HeadlineNewsDto(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("source")
	val source: SourceDto? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	)

data class SourceDto(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val sourceId: String? = null
)
