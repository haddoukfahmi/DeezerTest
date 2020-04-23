package com.deezer.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.deezer.test.R
import com.deezer.test.ui.albumlist.AlbumFragmentList
import com.deezer.test.utils.CheckConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.lifecycleScope
import kotlinx.coroutines.GlobalScope

class MainActivity : AppCompatActivity() {

    private lateinit var checkConnectivity: CheckConnectivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkConnectivity = CheckConnectivity(this)

        setupKoinFragmentFactory(lifecycleScope)

        GlobalScope.launch(Dispatchers.Main) {
            checkConnectivity.observe(this@MainActivity, Observer {
                if (it!!) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.id_container, AlbumFragmentList())
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.id_container, OfflineFragment())
                        .commit()
                }
            })
        }

    }


}
