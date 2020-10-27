package com.muaj.demo.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.muaj.demo.common.Person
import com.muaj.demo.common.Stub


/**
 * 远程服务
 * @auth muaj
 * @date 2020/10/27.
 */
class RemoteService : Service() {
    private var persons: ArrayList<Person> = ArrayList()

    override fun onBind(intent: Intent?): IBinder? {
        persons = ArrayList()
        return iBinder
    }


    private val iBinder: IBinder = object : Stub() {
        @Throws(RemoteException::class)
        override fun addPerson(person: Person?) {
            persons.add(person!!)
            Log.e("muaj", "[RemoteService] persons=$persons, thread=${Thread.currentThread()}")
        }

        @Throws(RemoteException::class)
        override fun getPersonList(): List<Person?>? {
            return persons
        }
    }
}