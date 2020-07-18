package com.example.u2020.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.u2020.R
import com.example.u2020.data.api.WalletApi
import com.example.u2020.ui.AppContainer
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var appContainer: AppContainer
    @Inject lateinit var walletApi: WalletApi
    @Inject lateinit var httpUrl: HttpUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = appContainer.getContainer(this)
        layoutInflater.inflate(R.layout.activity_main, container)

        button.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                walletApi.getLookup("VALIDATE_AGE").execute()
            }
        }

        url.text = httpUrl.toString()

    }
}