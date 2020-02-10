package com.mohamedmoanes.photoweather.ui.gallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.utils.loadFile
import java.io.File

class GalleryAdapter(private val list: List<File>, val open: (filePath: String) -> Unit):RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false))
    }

    override fun getItemCount(): Int {
    return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.loadFile(list[position])
        holder.itemView.setOnClickListener {
            open(list[position].path)
        }
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView:AppCompatImageView= view.findViewById(R.id.image)
    }
}