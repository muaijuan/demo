package com.muaj.demo.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 *
 * @auth muaj
 * @date 2020/09/28.
 */
public class Proxy implements IPersonManager {

    private static final String DESCRIPTOR = "com.muaj.demo.common.IPersonManager";

    private IBinder mRemote;

    public Proxy(IBinder remote) {
        mRemote = remote;
    }

    @Override
    public void addPerson(Person person) throws RemoteException{
        Parcel data = Parcel.obtain();  //client需要server处理的数据
        Parcel reply = Parcel.obtain(); //server有返回值时的返回值
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if ((person != null)) {
                data.writeInt(1);
                person.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            mRemote.transact(Stub.TRANSACTION_addPerson, data, reply, 0); //跨进程 transact调用后会挂起
            reply.readException();
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    @Override
    public List<Person> getPersonList() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Person> result;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            mRemote.transact(Stub.TRANSACTION_getPersonList, data, reply, 0);
            reply.readException();
            result = reply.createTypedArrayList(Person.CREATOR);
        } finally {
            reply.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
