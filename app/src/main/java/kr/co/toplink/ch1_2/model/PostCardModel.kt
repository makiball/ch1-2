package kr.co.toplink.ch1_2.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostCardModel(val post: Post, @DrawableRes val drawablesRes: Int) : Parcelable
