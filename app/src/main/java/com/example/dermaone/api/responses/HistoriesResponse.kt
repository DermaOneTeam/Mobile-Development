package com.example.dermaone.api.responses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HistoriesResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("tentang")
	val tentang: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("obat")
	val obat: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("pencegahan")
	val pencegahan: String? = null
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(image)
		parcel.writeString(tentang)
		parcel.writeString(nama)
		parcel.writeString(obat)
		parcel.writeString(id)
		parcel.writeString(pencegahan)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<HistoriesResponse> {
		override fun createFromParcel(parcel: Parcel): HistoriesResponse {
			return HistoriesResponse(parcel)
		}

		override fun newArray(size: Int): Array<HistoriesResponse?> {
			return arrayOfNulls(size)
		}
	}
}
