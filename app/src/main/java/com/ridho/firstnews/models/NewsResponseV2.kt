package com.ridho.firstnews.models

data class NewsResponseV2(
    val sources: List<ArticleV2>,
    val status: String
)