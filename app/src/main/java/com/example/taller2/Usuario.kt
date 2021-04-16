package com.example.taller2

import android.os.Parcel
import android.os.Parcelable

class Usuario () : Parcelable {

    var name: String = ""
    var last_name: String = ""
    var type_document: String = ""
    var number_document: String = ""
    var date: String = ""
    var hobbies_text:String = ""
    var hobbies_check:BooleanArray = booleanArrayOf()
    var password: String = ""

    constructor(name:String , last_name:String, type_document:String, number_document:String, birth_date:String, hobbies_v:String, hobbies_c:BooleanArray, password:String) : this() {
        this.name = name
        this.last_name = last_name
        this.type_document = type_document
        this.number_document = number_document
        this.date = birth_date
        this.hobbies_text = hobbies_v
        this.hobbies_check = hobbies_c
        this.password = password
    }

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        last_name = parcel.readString().toString()
        type_document = parcel.readString().toString()
        number_document = parcel.readString().toString()
        date = parcel.readString().toString()
        hobbies_text =parcel.readString().toString()
        hobbies_check = parcel.createBooleanArray() as BooleanArray
        password = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(last_name)
        parcel.writeString(type_document)
        parcel.writeString(number_document)
        parcel.writeString(date)
        parcel.writeString(hobbies_text)
        parcel.writeBooleanArray(hobbies_check)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

}