package com.example.dermaone.api.responses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("isi")
	val isi: String? = null
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(judul)
		parcel.writeString(gambar)
		parcel.writeString(isi)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ArticlesResponse> {
		override fun createFromParcel(parcel: Parcel): ArticlesResponse {
			return ArticlesResponse(parcel)
		}

		override fun newArray(size: Int): Array<ArticlesResponse?> {
			return arrayOfNulls(size)
		}
	}
}
