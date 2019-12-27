package com.example.android.wifidirectactivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


//client socket
public class SSHConnectService {
    private static final int SOCKET_TIMEOUT = 10000;
    //public String ACTION_SEND_FILE = "com.example.android.wifidirectactivity.SEND_FILE";
    public String EXTRAS_FILE_PATH = "file_url";
    public String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public String EXTRAS_GROUP_OWNER_PORT = "go_port";
    private int port;

    String TAG = "JSCH-Client";

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


    public void setJschConnect(){
        Log.e("JSch-onHandleIntent #", this.EXTRAS_FILE_PATH);
        Log.e("JSch-onHandleIntent ##", this.EXTRAS_GROUP_OWNER_ADDRESS);
        Log.e("JSch-onHandleIntent ###", this.EXTRAS_GROUP_OWNER_PORT);
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeSSHcommand();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
    }

    public void executeSSHcommand(){
        String user = "Haeyoung";
        String password = "1234";
        //String host = "192.168.1.4"; = EXTRAS_GROUP_OWNER_ADDRESS
        try{

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, EXTRAS_GROUP_OWNER_ADDRESS, port);
            session.setPassword(password);

            // 호스트 정보 검사 X
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(SOCKET_TIMEOUT);
            session.connect();
            //ChannelExec channel = (ChannelExec)session.openChannel("sftp");
            Channel channel = (Channel)session.openChannel("sftp");
            //channel.setCommand("your ssh command here");
            channel.connect();

            if(channel.isConnected()){
                Log.e(TAG, "JSch : Connected");
            }
            ChannelSftp channelSftp = (ChannelSftp) channel;

            channel.disconnect();
            if(channel.isClosed()){
                Log.e(TAG, "JSch : Closed");
            }


            Log.e(TAG, "JSch : SUCCESS");
        }
        catch(JSchException e){
            // show the error in the UI
            Log.e(TAG, "JSchException : " + e);
        }
    }

}
