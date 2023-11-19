package com.kobe.binder_chuansongmeng;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private IBinder binder2 = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            Log.v("Kobe", "binder2.onTransact");
            return true;
        }
    };

    private void init() {
        Intent intent = new Intent(this, MyService.class);
        this.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IBinder binder1 = service;
                Parcel data = Parcel.obtain();
                data.writeStrongBinder(binder2);
                Parcel reply = Parcel.obtain();//暂不使用
                try {
                    Log.v("Kobe", "binder1.transact");
                    binder1.transact(1, data, reply, 0);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                IBinder binder3 = reply.readStrongBinder();
                Parcel data3 = Parcel.obtain();
                Parcel reply3 = Parcel.obtain();
                try {
                    Log.v("Kobe", "binder3.transact");
                    binder3.transact(1, data3, reply3, 0);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }
}