package com.example.tripapp2.domain.entities

sealed class CommentsState {
    data class CommentHolder(
        val comments: List<Comment>
    ): CommentsState()

    data object Loading: CommentsState()
    data object Error: CommentsState()
}