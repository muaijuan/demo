package com.muaj.demo.common;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 *
 * @auth muaj
 * @date 2020/09/28.
 */
public interface IPersonManager extends IInterface {

    void addPerson(Person person) throws RemoteException;

    List<Person> getPersonList() throws RemoteException;
}
