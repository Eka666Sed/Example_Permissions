package com.yandexpracticum.example_permissions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.markodevcic.peko.PermissionRequester


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionRequester.initialize(applicationContext)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PermissionFragment())
            .commit()

    }

}