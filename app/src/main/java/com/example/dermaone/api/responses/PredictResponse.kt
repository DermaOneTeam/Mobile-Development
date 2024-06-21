package com.example.dermaone.api.responses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("id_penyakit")
	val idPenyakit: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("tentang")
	val tentang: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("obat")
	val obat: String? = null,

	@field:SerializedName("pencegahan")
	val pencegahan: String? = null,

	@field:SerializedName("image")
	val image: String? = null
): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(idPenyakit)
		parcel.writeString(message)
		parcel.writeString(idUser)
		parcel.writeString(tentang)
		parcel.writeString(id)
		parcel.writeValue(error)
		parcel.writeString(nama)
		parcel.writeString(obat)
		parcel.writeString(pencegahan)
		parcel.writeString(image)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<PredictResponse> {
		override fun createFromParcel(parcel: Parcel): PredictResponse {
			return PredictResponse(parcel)
		}

		override fun newArray(size: Int): Array<PredictResponse?> {
			return arrayOfNulls(size)
		}
	}
}
