package uz.ismoil.notes.data.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val title: String,
    val text: String,
    val image: ByteArray,

    @DrawableRes
    val color: Int,

    val timestamp: Long,
    val pinned: Boolean,
    var state:NoteState

):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (text != other.text) return false
        if (!image.contentEquals(other.image)) return false
        if (timestamp != other.timestamp) return false
        if (pinned != other.pinned) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + pinned.hashCode()
        result = 31 * result + color
        return result
    }
}