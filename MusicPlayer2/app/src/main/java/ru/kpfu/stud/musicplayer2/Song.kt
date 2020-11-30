package ru.kpfu.stud.musicplayer2

import android.content.ComponentName.readFromParcel
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

class Song() : Parcelable {
    lateinit var id: String
    lateinit var author: String
    lateinit var title:  String
    @DrawableRes var image: Int = -1
    @RawRes var mp3: Int = -1

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song> {
            return Array(size) { Song() }
        }
    }

    private constructor(inParcel: Parcel) : this() {
        readFromParcel(inParcel)
    }

    constructor(id: String, author: String, title: String, image: Int, mp3: Int) : this() {
        this.author = author
        this.title = title
        this.image = image
        this.mp3 = mp3
        this.id = id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeInt(image)
        parcel.writeInt(mp3)
    }

    override fun describeContents(): Int = 0

}