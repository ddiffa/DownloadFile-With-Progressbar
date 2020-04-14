package com.hellodiffa.downloadfile.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellodiffa.downloadfile.R
import com.hellodiffa.downloadfile.remote.DummyData
import com.hellodiffa.downloadfile.remote.Resource
import com.hellodiffa.downloadfile.utils.data
import com.hellodiffa.downloadfile.utils.openFile
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var mainAdapter: MainAdapter
    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).orientation
            ).apply {
                addItemDecoration(this)
            }
            mainAdapter = MainAdapter(data, this@MainActivity::onItemClicked)
            adapter = mainAdapter
        }

        observeData()
    }

    private fun observeData() {
        viewModel.file.observe(this, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        mainAdapter.setDownloading(
                            data = data,
                            isDownloading = false
                        )
                    }
                }
                Resource.Status.LOADING -> {
                    it.data?.let { data ->
                        it.progress?.let { progress ->
                            mainAdapter.setProgress(
                                data = data,
                                progress = progress
                            )
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    it.data?.let { it1 -> mainAdapter.setDownloading(it1, false) }
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun onItemClicked(data: DummyData) {
        when {
            data.isDownloading -> {

            }
            data.file.exists() -> {
                openFile(data.file)
            }
            else -> {
                viewModel.downloadFile(data)
            }
        }
    }
}
