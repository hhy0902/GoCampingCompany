package com.example.gocampingcompany.post

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gocampingcompany.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.net.URI

class PostAdapter : ListAdapter<PostModel, PostAdapter.ViewHolder> (differ){

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {

        fun bind(item : PostModel) {

            val storage = Firebase.storage
            val storageRef = storage.reference

            val title = itemView.findViewById<TextView>(R.id.titleTextView)
            val image = itemView.findViewById<ImageView>(R.id.postImageView)
            val name = itemView.findViewById<TextView>(R.id.nameTextView)
            val date = itemView.findViewById<TextView>(R.id.dateTextView)

            storageRef.child("post/image").child("${item.name}${item.time}.png").downloadUrl
                .addOnSuccessListener {
                    Glide.with(image.context)
                        .load(it)
                        .into(image)
                }
                .addOnFailureListener {
                    Log.d("post/image", "${item.name}${item.time}")
                    Log.d("error","${it.message}")
                }

            title.text = item.title
            name.text = item.name
            date.text = item.writeDate


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.post_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem == newItem
            }

        }
    }


}




































