package com.example.gocampingcompany.star

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gocampingcompany.R
import com.example.gocampingcompany.star.model.StarItem

class StarAdapter(val itemClick : (StarItem) -> Unit) : ListAdapter<StarItem, StarAdapter.ViewHolder> (differ) {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : StarItem) {

            val title = itemView.findViewById<TextView>(R.id.titleTextView)
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            val address = itemView.findViewById<TextView>(R.id.addressTextView)
            val tel = itemView.findViewById<TextView>(R.id.telTextView)

            title.text = "[${item.doName} ${item.siName}]" + item.title
            address.text = "${item.addr1} ${item.addr2}"
            tel.text = "HP : ${item.tel}"
            Glide
                .with(image.context)
                .load(item.image)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(image)

            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.star_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<StarItem> () {
            override fun areItemsTheSame(oldItem: StarItem, newItem: StarItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: StarItem, newItem: StarItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}











































