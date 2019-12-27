// Copyright 2011 Google Inc. All Rights Reserved.

package com.example.android.wifidirectactivity;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.android.wifidirectactivity.DeviceDetailFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 10000;
    public String ACTION_SEND_FILE = "com.example.android.wifidirectactivity.SEND_FILE";
    public String EXTRAS_FILE_PATH = "file_url";
    public String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public int port;

    private Context context;

    public FileTransferService(Context context) {
        super("FileTransferService");
        this.context = context;
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    void setEXTRAS_FILE_PATH(String EXTRAS_FILE_PATH){
        this.EXTRAS_FILE_PATH = EXTRAS_FILE_PATH;
    }

    void setEXTRAS_GROUP_OWNER_ADDRESS(String EXTRAS_GROUP_OWNER_ADDRESS){
        this.EXTRAS_GROUP_OWNER_ADDRESS = EXTRAS_GROUP_OWNER_ADDRESS;
    }

    void setEXTRAS_GROUP_OWNER_PORT(String EXTRAS_GROUP_OWNER_PORT){
        this.EXTRAS_GROUP_OWNER_PORT = EXTRAS_GROUP_OWNER_PORT;
        this.port = Integer.parseInt(EXTRAS_GROUP_OWNER_PORT);
    }
    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

//        Context context = getApplicationContext();
//        if (intent.getAction().equals(ACTION_SEND_FILE)) {
//            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
//            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
//            Socket socket = new Socket();
//            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
//
//            try {
//                Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
//                socket.bind(null);
//                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
//
//                Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());
//                OutputStream stream = socket.getOutputStream();
//                ContentResolver cr = context.getContentResolver();
//                InputStream is = null;
//                try {
//                    is = cr.openInputStream(Uri.parse(fileUri));
//                } catch (FileNotFoundException e) {
//                    Log.d(WiFiDirectActivity.TAG, e.toString());
//                }
//                DeviceDetailFragment.copyFile(is, stream);
//                Log.d(WiFiDirectActivity.TAG, "Client: Data written");
//            } catch (IOException e) {
//                Log.e(WiFiDirectActivity.TAG, e.getMessage());
//            } finally {
//                if (socket != null) {
//                    if (socket.isConnected()) {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            // Give up
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        }
    }


    public void connectionSocket() {
        Log.e("JSch-connectionSocket", ": now fuction = connectionSocket");
        Log.e("JSch-Path", EXTRAS_FILE_PATH);
        Log.e("JSch-Address", EXTRAS_GROUP_OWNER_ADDRESS);
        Log.e("JSch-port", EXTRAS_GROUP_OWNER_PORT);
        if (context == null) {
            Log.e("JSch-connectionSocket", ": getApplicationContext = null");
        }
        new Thread() {
            public void run() {
                threadConnect();
            }
        }.start();
    }

    public void threadConnect(){
        Log.e("JSch-threadConnect", ": hello");
       // Context context = getApplicationContext();
        Socket socket = new Socket();

        try {
            Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
            socket.bind(null);
            socket.connect((new InetSocketAddress(EXTRAS_GROUP_OWNER_ADDRESS, port)), SOCKET_TIMEOUT);
            Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());
            OutputStream stream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream is = null;
            try {
                Log.d("Uri.parse - ", Uri.parse(EXTRAS_FILE_PATH).toString());
                is = cr.openInputStream(Uri.parse(EXTRAS_FILE_PATH));
            } catch (FileNotFoundException e) {
                Log.d(WiFiDirectActivity.TAG, e.toString());
            }
            Log.e("DETAIL : "," pre copyFile");
            DeviceDetailFragment.copyFile(is, stream);
            Log.d(WiFiDirectActivity.TAG, "Client: Data written");
        } catch (IOException e) {
            Log.e(WiFiDirectActivity.TAG, e.getMessage());
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // Give up
                        e.printStackTrace();
                    }
                }
            }
        }

//        if (intent.getAction().equals(ACTION_SEND_FILE)) {
//            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
//            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
//            Socket socket = new Socket();
//            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
//
//            try {
//                Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
//                socket.bind(null);
//                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
//
//                Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());
//                OutputStream stream = socket.getOutputStream();
//                ContentResolver cr = context.getContentResolver();
//                InputStream is = null;
//                try {
//                    is = cr.openInputStream(Uri.parse(fileUri));
//                } catch (FileNotFoundException e) {
//                    Log.d(WiFiDirectActivity.TAG, e.toString());
//                }
//                DeviceDetailFragment.copyFile(is, stream);
//                Log.d(WiFiDirectActivity.TAG, "Client: Data written");
//            } catch (IOException e) {
//                Log.e(WiFiDirectActivity.TAG, e.getMessage());
//            } finally {
//                if (socket != null) {
//                    if (socket.isConnected()) {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            // Give up
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        }
    }
}
