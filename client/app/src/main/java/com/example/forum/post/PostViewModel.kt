package com.example.forum.post

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forum.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel(private val user: User, private val postId: Long, app: Application) : AndroidViewModel(app) {
  val post = MutableLiveData<Post>()
  val status = MutableLiveData<ForumApiStatus>()
  val comments = MutableLiveData<List<Comment>>()
  val userComment = MutableLiveData<String>()
  val isCommentEditorEnable = MutableLiveData<Boolean>()
  val isUserAGuest = MutableLiveData<Boolean>()
  private val commentsList = mutableListOf<Comment>()

  init {
    getPost(postId)
    getComments(postId)
    isUserAGuest.value = (user.roles.size < 2 && user.roles.contains(UserRole.GUEST))
  }

  private fun getPost(postId: Long) {
    ForumApi.retrofitService.getPost(postId).enqueue(object: Callback<Post> {
      override fun onResponse(call: Call<Post>, response: Response<Post>) {
        post.value = response.body()
        status.value = ForumApiStatus.SUCCESS
      }

      override fun onFailure(call: Call<Post>, t: Throwable) {
        post.value = null
        status.value = ForumApiStatus.ERROR
      }
    })
  }

  private fun getComments(postId: Long) {
    ForumApi.retrofitService.getComments(postId).enqueue(object : Callback<List<Comment>> {
      override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
        response.body()?.let {
          commentsList.addAll(it)
        }
        comments.value = commentsList
        status.value = ForumApiStatus.SUCCESS
      }

      override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
        comments.value = ArrayList()
        status.value = ForumApiStatus.ERROR
      }
    })
  }

  fun displayCommentEditor() {
    isCommentEditorEnable.value = true
  }

  fun hideCommentEditor() {
    userComment.value = ""
    isCommentEditorEnable.value = false
  }

  fun sendComment() {
    ForumApi.retrofitService.addComment(postId, CommentBody(postId, user.id, userComment.value!!)).enqueue(object: Callback<CommentResponse> {
      override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
        response.body()?.let {
          val comment = Comment(it.id, user.login, it.content, it.publishedDate)
          commentsList.add(0, comment)
        }
        comments.value = commentsList
      }

      override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
        Log.e("PostViewModel", t.message!!)
      }
    })
    hideCommentEditor()
  }

  fun deleteComment(position: Int, commentId: Long) {
    ForumApi.retrofitService.deleteComment(commentId).enqueue(object: Callback<Unit> {
      override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        Log.i("PostViewModel", response.message().toString())
        if (response.code() == ForumApiStatus.SUCCESS.code) {
          val commentsList = comments.value as MutableList<Comment>
          commentsList.removeAt(position)
          comments.value = commentsList
        }
      }

      override fun onFailure(call: Call<Unit>, t: Throwable) {
        Log.e("PostViewModel", t.message!!)
      }
    })
  }
}