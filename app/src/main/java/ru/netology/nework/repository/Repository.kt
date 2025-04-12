package ru.netology.nework.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.AttachmentType
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.Media
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.User


interface Repository {
    // POSTS
    val posts: Flow<PagingData<Post>>

    suspend fun getPosts()
    suspend fun disLikeById(post: Post)
    suspend fun likeById(post: Post)
    suspend fun save(post: Post)
    suspend fun saveWithAttachment(post: Post, upload: MediaUpload, attachmentType: AttachmentType)
    suspend fun upload(upload: MediaUpload): Media
    suspend fun removePostById(id: Int)

    //AUTH
    suspend fun signIn(login: String, password: String)
    suspend fun signUp(login: String, password: String, name: String)
    suspend fun signUpWithAvatar(login: String, password: String, name: String, upload: MediaUpload)

    // EVENTS
    val events: Flow<PagingData<Event>>

    suspend fun disLikeEventById(event: Event)
    suspend fun likeEventById(event: Event)
    suspend fun saveEvent(event: Event)
    suspend fun saveEventWithAttachment(event: Event, upload: MediaUpload, attachmentType: AttachmentType)
    suspend fun removeEventById(id: Int)

    //USERS

    val users: Flow<List<User>>

    suspend fun getAllUsers()
}
