package com.infoland.recycleviewapplication

import android.os.Parcel
import android.os.Parcelable

// THIS IS MODEL CLASS, ADD PARCELABLE IF YOU WANT TO SEND BUNDLE FROM ONE ACTIVITY TO ANOTHER ACTIVITY
data class StudentModel(var id: Int,
                        var bannerID: String?,
                        var name: String?,
                        var email: String?,
                        var section: String?,
                        var AY: String?): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(bannerID)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(section)
        parcel.writeString(AY)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentModel> {
        override fun createFromParcel(parcel: Parcel): StudentModel {
            return StudentModel(parcel)
        }

        override fun newArray(size: Int): Array<StudentModel?> {
            return arrayOfNulls(size)
        }
    }

}
