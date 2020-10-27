package com.muaj.demo.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.muaj.demo.R
import com.muaj.demo.common.IPersonManager
import com.muaj.demo.common.Person
import com.muaj.demo.common.Stub
import com.muaj.demo.server.RemoteService
import kotlinx.android.synthetic.main.activity_client.*


/**
 *
 * @auth muaj
 * @date 2020/09/28.
 */
class ClientActivity : AppCompatActivity(), View.OnClickListener {
    private var iPersonManager: IPersonManager? = null
    private var mIsBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        btnBind.setOnClickListener(this)
        btnUnbind.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBind -> bindRemoteService()
            R.id.btnUnbind -> unbindRemoteService()
            R.id.btnSubmit -> submit()
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            iPersonManager = Stub.asInterface(service) // proxy 返回代理对象
            btnSubmit.isEnabled = true
            textView.setText(R.string.binded)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            iPersonManager = null
        }
    }

    /**
     * 绑定远程服务
     */
    private fun bindRemoteService() {
        if(mIsBound) return
        val intent = Intent(this@ClientActivity, RemoteService::class.java)
        intent.action = IPersonManager::class.java.name
        mIsBound = bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * 解除绑定远程服务
     */
    private fun unbindRemoteService() {
        if(mIsBound){
            unbindService(mConnection)
            mIsBound = false
            btnSubmit.isEnabled = false
            textView.setText(R.string.unbinded)
        }
    }

    /**
     * 发送数据到远程服务
     */
    private fun submit() {
        try {
            var name = etName.editableText.toString()
            var grade = etGrade.editableText.toString()
            if(name.isEmpty()) name = "muaj"
            if(grade.isEmpty()) grade = "0"

            iPersonManager!!.addPerson(Person(name, grade.toInt()))
            val persons: List<Person> = iPersonManager!!.personList
            textView.text = persons.toString()
            Log.e("muaj", "[ClientActivity] persons=$persons, thread=${Thread.currentThread()}")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

}