package ru.netology.nework.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nework.dto.Attachment
import ru.netology.nework.dto.Coordinates
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.UsersArray

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val authorId: Int,
    val author: String,
    val authorJob: String,
    val authorAvatar: String?,
    val content: String,
    val published: String,
//    val coords: Coordinates? = null,
    val link: String? = null,
//    val mentionIds: List<Int>? = null,
    val mentionedMe: Boolean,
//    val likeOwnerIds: List<Int>? = null,
    val likedByMe: Boolean,
//    val users: UsersArray? // too many questions, but works


    @Embedded
    val attachment: Attachment?,
    @Embedded
    val coords: Coordinates?,

    ) {
    fun toDto() = Post(
        id,
        authorId,
        author,
        authorJob,
        authorAvatar,
        content,
        published,
        coords,
        link,
        null,
        mentionedMe,
        null,
        likedByMe,
        attachment,
        null,
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.authorId,
                dto.author,
                dto.authorJob,
                dto.authorAvatar,
                dto.content,
                dto.published,
                //    val coords: Coordinates? = null,
                dto.link,
                //    val mentionIds: List<Int>? = null,
                dto.mentionedMe,
                //    val likeOwnerIds: List<Int>? = null,
                dto.likedByMe,
//    val users: UsersArray? // too many questions, but works
                coords = dto.coords,
                attachment = dto.attachment
            )

    }
}



