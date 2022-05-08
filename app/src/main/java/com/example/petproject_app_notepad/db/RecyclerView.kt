package com.example.petproject_app_notepad.db

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petproject_app_notepad.AddActivity
import com.example.petproject_app_notepad.R

class RecyclerViewAdapter(private val listPosts: ArrayList<PostItemClass>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {

    class ViewHolderClass(postItem: View) : RecyclerView.ViewHolder(postItem) {
        private val context = postItem.context
        private val imageItem = postItem.findViewById<ImageView>(R.id.rv_post_item_image)
        private val titleItem = postItem.findViewById<TextView>(R.id.rv_post_item_text_title)
        private val descItem = postItem.findViewById<TextView>(R.id.rv_post_item_text_description)
        private val postContainer = postItem.findViewById<ConstraintLayout>(R.id.rv_post_container)

        fun bind(post: PostItemClass) {
            if (post.uri == "empty") {
                imageItem.isVisible = false
                titleItem.text = post.title
                descItem.text = post.description
            } else {
                imageItem.isVisible = true
                Glide.with(context).load(post.uri).into(imageItem)
                titleItem.text = post.title
                descItem.text = post.description
            }
            postContainer.setOnClickListener {
                val intent = Intent(context, AddActivity::class.java).apply {
                    putExtra(IntentConstant.INTENT_TITLE, post.title)
                    putExtra(IntentConstant.INTENT_DESCRIPTION, post.description)
                    putExtra(IntentConstant.INTENT_URI, post.uri)
                }
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        return ViewHolderClass(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.bind(listPosts[position])
    }

    fun updateAdapter(newListPost: ArrayList<PostItemClass>) {
        listPosts.clear()
        listPosts.addAll(newListPost)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, databaseManager: DatabaseManager) {
        databaseManager.removeFromDatabase(listPosts[position].id.toString())
        listPosts.removeAt(position)
        notifyItemRangeChanged(0, listPosts.size)
        notifyDataSetChanged()
    }


    override fun getItemCount() = listPosts.size

}