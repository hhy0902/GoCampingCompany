package com.example.gocampingcompany.search

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
import com.example.gocampingcompany.search.searchmodel.Item

class SearchAdapter : ListAdapter<Item, SearchAdapter.ViewHolder> (differ) {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind (item : Item) {
            val title = itemView.findViewById<TextView>(R.id.titleTextView)
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            val address = itemView.findViewById<TextView>(R.id.addressTextView)
            val tel = itemView.findViewById<TextView>(R.id.telTextView)
            val iconImage = itemView.findViewById<TextView>(R.id.iconImageView)

            title.text = "[${item.doNm} ${item.sigunguNm}]" + item.facltNm
            address.text = "${item.addr1} ${item.addr2}"
            tel.text = "HP : ${item.tel}"
            Glide
                .with(image.context)
                .load(item.firstImageUrl)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(image)

//            val icon = "https://www.gocamping.or.kr/img/2018/sub/ico_volt.png"

            val icon = item.sbrsCl?.contains("전기")
            when(icon) {
                true -> {
                    iconImage.text = "전기 true"
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.search_item_list,parent,false))
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Item> (){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.contentId == newItem.contentId
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }

}