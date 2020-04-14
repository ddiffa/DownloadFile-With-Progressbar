package com.hellodiffa.downloadfile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hellodiffa.downloadfile.R
import com.hellodiffa.downloadfile.remote.DummyData
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter(private val list: Array<DummyData>, val onClick: (DummyData) -> Unit) :
    RecyclerView.Adapter<MainAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position])
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.firstOrNull() != null) {
            with(holder.itemView) {
                (payloads.first() as Bundle).getInt("progress").also {
                    progressBar.progress = it
                    progressBar.isVisible = it < 100
                    tvProgress.isVisible = it < 100
                    tvProgress.text = "$it%"
                }
            }
        }
    }

    fun setDownloading(data: DummyData, isDownloading: Boolean) {
        getData(data)?.isDownloading = isDownloading
        notifyItemChanged(list.indexOf(data))
    }

    fun setProgress(data: DummyData, progress: Int) {
        getData(data)?.progress = progress
        notifyItemChanged(list.indexOf(data), Bundle().apply {
            putInt("progress", progress)
        })
    }

    private fun getData(data: DummyData) = list.find { data.id == it.id }

    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(data: DummyData) = view.apply {
            tvTitle.text = data.title
            imgDownload.isVisible = !data.file.exists()
            progressBar.isVisible = data.isDownloading
            tvProgress.isVisible = data.isDownloading
            setOnClickListener {
                onClick(data)
            }
        }
    }
}