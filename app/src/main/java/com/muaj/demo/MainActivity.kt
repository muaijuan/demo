package com.muaj.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.muaj.demo.client.ClientActivity
import com.muaj.demo.hook.HookActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @auth muaj
 * @date 2020/10/27.
 */
class MainActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAidl.setOnClickListener(this)
        btnHook.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnAidl -> startActivity(Intent(this, ClientActivity::class.java))
            R.id.btnHook -> startActivity(Intent(this, HookActivity::class.java))

        }
    }
}