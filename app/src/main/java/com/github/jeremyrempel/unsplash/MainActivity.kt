package com.github.jeremyrempel.unsplash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.jeremyrempel.unsplash.db.Database
import com.github.jeremyrempel.unsplash.presentation.PhotoActions
import com.github.jeremyrempel.unsplash.presentation.PhotoPresenter
import com.github.jeremyrempel.unsplash.presentation.PhotoView
import com.github.jeremyrempel.unsplash.presentation.model.PictureData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import sample.R
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), PhotoView {

    override var isUpdating: Boolean by Delegates.observable(false) { _, _, isLoading ->
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            button.visibility = View.GONE
            imageView.visibility = View.GONE
            text.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            button.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
        }
    }

    private val actions: PhotoActions by lazy {
        PhotoPresenter(Dispatchers.Main, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            actions.onRequestData()
        }

        // SQLDelight usage sample from Android code
        val db = Database.getInstance(this).pictureQueries
        db.selectAll().executeAsList().forEach {
            log(LogLevel.DEBUG, "DB App", it.toString())
        }

        actions.onViewReady()
    }

    override fun onUpdate(data: PictureData) {
        text.text = data.description

        Glide
            .with(this)
            .load(data.url)
            .thumbnail(.25f)
            .into(imageView)
    }

    override fun showError(error: Throwable) {
        text.text = error.toString()
    }
}
