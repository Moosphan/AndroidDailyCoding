package com.example.binder.remote;

import android.os.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/**
 * @author Moosphon
 * @date 2021/11/6 9:00 上午
 * @desc Remote interface of ability.
 */
public interface ILibrary extends IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends Binder implements ILibrary {
        private static final String DESCRIPTOR = "com.example.binder.remote.ILibrary";
        static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;
        static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        /**
         * Cast an IBinder object into an com.example.binder.remote.ILibrary interface,
         * generating a proxy if needed.
         */
        public static ILibrary asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
            if (iInterface instanceof ILibrary) {
                return (ILibrary) iInterface;
            }
            return new ILibrary.Stub.Proxy(obj);
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_addBook:
                    data.enforceInterface(DESCRIPTOR);
                    Book arg0;
                    int status = data.readInt();
                    if (status == 1) {
                        arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        arg0 = null;
                    }
                    this.addBook(arg0);
                    assert reply != null;
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getBookList:
                    data.enforceInterface(DESCRIPTOR);
                    List<Book> books = this.getBookList();
                    assert reply != null;
                    reply.writeNoException();
                    reply.writeTypedList(books);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements ILibrary {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if (book != null) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        // Write status: 1-success, 0-failure.
                        _data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }

            @Override
            public List<Book> getBookList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                List<Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
                return _result;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
    }

    /**
     * Add new book in library.
     * @param book New book.
     * @throws RemoteException
     */
    void addBook(Book book) throws RemoteException;

    /**
     * Get books in library.
     * @return All books in library.
     * @throws RemoteException
     */
    List<Book> getBookList() throws RemoteException;
}
