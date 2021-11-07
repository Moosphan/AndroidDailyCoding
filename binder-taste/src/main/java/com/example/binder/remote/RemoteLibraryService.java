package com.example.binder.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Moosphon
 * @date 2021/11/6 10:20 上午
 * @desc Remote service.
 */
public class RemoteLibraryService extends Service {
    public static final String TAG = RemoteLibraryService.class.getSimpleName();
    private List<Book> mBookList = new CopyOnWriteArrayList<>();
    private IBinder mBinder = new ILibrary.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            printLibraryRecord();
            return mBookList;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        initLibrary();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void initLibrary() {
        Book book1 = new Book(1204, "造世说");
        Book book2 = new Book(1206, "海底世界");
        Book book3 = new Book(1208, "十万个为什么");
        mBookList.add(book1);
        mBookList.add(book2);
        mBookList.add(book3);
        printLibraryRecord();
    }

    public void printLibraryRecord() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Book book : mBookList) {
            stringBuilder.append("{ id: ")
                    .append(book.getId())
                    .append(", name: ")
                    .append(book.getName())
                    .append("},");
        }
        stringBuilder.append("]");
        Log.i(TAG, stringBuilder.toString());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
