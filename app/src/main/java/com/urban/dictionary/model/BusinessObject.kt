package com.urban.dictionary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class BusinessObject

@Parcelize
data class DictionaryResponse(
    val list: List<SearchItem>
) : Parcelable, BusinessObject()

@Parcelize
data class SearchItem(
    val author: String?,
    val current_vote: String?,
    val defid: Long,
    val definition: String?,
    val example: String?,
    val permalink: String?,
    val thumbs_down: Int,
    val thumbs_up: Int,
    val word: String?
) : Parcelable, BusinessObject()


