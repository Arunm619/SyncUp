package io.arunbuilds.syncupnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Snackbar.make(findViewById(android.R.id.content),"The Api Key is ${BuildConfig.NEWS_API_KEY}",Snackbar.LENGTH_INDEFINITE).show()
    }
}