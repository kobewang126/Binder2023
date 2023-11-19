package com.kobe.binder_chuansongmeng;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {

    private IBinder binder1 = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            Log.v("Kobe", "binder1.onTransact");
            reply.writeStrongBinder(binder3);
            IBinder binder2 = data.readStrongBinder();
            Parcel data2 = Parcel.obtain();
            Parcel reply2 = Parcel.obtain();//暂不使用
            Log.v("Kobe", "binder2.transact");
            binder2.transact(1, data2, reply2, 0);
            return true;
        }
    };

    private IBinder binder3 = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            Log.v("Kobe", "binder3.onTransact");
            return super.onTransact(code, data, reply, flags);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder1;
    }
}
