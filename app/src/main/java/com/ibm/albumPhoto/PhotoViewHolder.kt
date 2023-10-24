package com.ibm.albumPhoto

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var photoIV: ImageView = itemView.findViewById(R.id.photo_iv)
    private var authorTV: TextView = itemView.findViewById(R.id.author_tv)

    fun bind(photo: Photos) {
        authorTV.text = photo.photographer

        Glide.with(photoIV.context)
            .load(photo.src?.original)
            .into(photoIV)
    }
}

data class Photo(
    val photographer: String,
    val src: PhotoSource?
)

data class PhotoSource(
    val original: String
)
