// Copyright 2011 Google Inc. All Rights Reserved.

package com.example.amira.atelierje;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A service that process each video transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner
 */
public class VideoTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_STREAM_VIDEO = "com.example.amira.atelierje.STREAM_VIDEO";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";

    public VideoTransferService(String name) {
        super(name);
    }

    public VideoTransferService() {
        super("VideoTransferService");
    }

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();



        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_STREAM_VIDEO)) {
            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                Log.d(WiFiDirectActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(WiFiDirectActivity.TAG, "Client socket - " + socket.isConnected());

                //InputStream stream = socket.getInputStream();

                if (bundle != null) {
                    Messenger messenger = (Messenger) bundle.get("messenger");
                    Message msg = Message.obtain();
                    bundle.putString("address", socket.getLocalAddress().getHostAddress());
                    bundle.putInt("port", socket.getLocalPort());
                    msg.setData(bundle); //put the data here
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        Log.i("error", "error");
                    }
                }
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

        }
    }
}
