package com.example.binder.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.binder.R;
import com.example.binder.remote.Book;
import com.example.binder.remote.ILibrary;
import com.example.binder.remote.RemoteLibraryService;

public class ClientSampleActivity extends AppCompatActivity {
    public static final String TAG = "ClientSampleActivity";
    private ILibrary mLibrary;
    private int bookId = 1255 << 2;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mLibrary = ILibrary.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mLibrary = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sample);
        initService();
        EditText editText = findViewById(R.id.edit_book);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonQuery = findViewById(R.id.buttonQuery);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(bookId, TextUtils.isEmpty(editText.getText().toString()) ? "" : editText.getText().toString());
                try {
                    mLibrary.addBook(book);
                    ++bookId;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mLibrary.getBookList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initService() {
        Intent intent = new Intent(this, RemoteLibraryService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}