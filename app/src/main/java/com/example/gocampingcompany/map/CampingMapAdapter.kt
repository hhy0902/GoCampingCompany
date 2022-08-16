package com.example.gocampingcompany.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gocampingcompany.R
import com.example.gocampingcompany.map.allcampingmapmodel.Item
import com.example.gocampingcompany.search.SearchAdapter.Companion.differ

class CampingMapAdapter(val campingMapItemClick : (Item) -> Unit,
    val campingMapCloseClick: (Item) -> Unit,
    val campingDetailButtonClick : (Item) -> Unit) : ListAdapter<Item, CampingMapAdapter.ViewHolder> (differ) {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : Item) {

            val title = itemView.findViewById<TextView>(R.id.titleTextView)
            val closeButton = itemView.findViewById<Button>(R.id.closeButton)
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            val tel = itemView.findViewById<TextView>(R.id.telTextView)
            val detailButton = itemView.findViewById<Button>(R.id.detailButton)

            title.text = "[${item.doNm} ${item.sigunguNm}]" + item.facltNm
            tel.text = "HP : ${item.tel}"

            itemView.setOnClickListener {
                campingMapItemClick(item)
            }

            closeButton.setOnClickListener {
                campingMapCloseClick(item)
            }

            detailButton.setOnClickListener {
                campingDetailButtonClick(item)
            }



            Glide.with(image.context).load(item.firstImageUrl).into(image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.campingmap_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Item> () {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.contentId == newItem.contentId
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }
}






























