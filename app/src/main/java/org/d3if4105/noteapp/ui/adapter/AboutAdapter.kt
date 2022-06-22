package org.d3if4105.noteapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.d3if4105.noteapp.R
import org.d3if4105.noteapp.databinding.ItemAboutBinding
import org.d3if4105.noteapp.model.About
import org.d3if4105.noteapp.network.AboutApi

class AboutAdapter : RecyclerView.Adapter<AboutAdapter.AboutViewHolder>() {

    private val items = mutableListOf<About>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListTipsData(data : List<About>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AboutViewHolder(
        ItemAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AboutViewHolder, position: Int) = with(holder) {
        bind(items[position])
    }

    override fun getItemCount() = items.size

    class AboutViewHolder(var binding: ItemAboutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(about: About) = with(binding) {
            Glide.with(this.root)
                .load(AboutApi.getAboutUrl(about.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(ivAbout)
            tvAboutName.text = about.name
            tvAboutDesc.text = about.description

            root.setOnClickListener {

                Snackbar.make(
                    root,
                    about.name,
                    Snackbar.LENGTH_SHORT,

                    ).show()
            }
        }
    }
}