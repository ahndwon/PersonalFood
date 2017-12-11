package com.team11.personalfood.Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DongWon on 2017-12-11.
 */

interface ReaderListener {
    void onTurnChange(String message);

    void onUsers(String message);

    void onDraw(String message);

    void onSuccess(String message);

    void onFail(String message);

    void onDisconnect(String message);

    void onRefresh(String message);

}

class ReaderThread extends Thread {
    private InputStream is;
    private DataInputStream dis;
    private ReaderListener listener;

    void setReaderListener(ReaderListener readerListener) {
        this.listener = readerListener;
    }

    public ReaderThread(InputStream is, ReaderListener listener) {
        this.is = is;
        this.dis = new DataInputStream(is);
        this.listener = listener;
    }

    public static void read(DataInputStream is, byte[] data, int size)
            throws IOException {
        int left = size;
        int offset = 0;

        while (left > 0) {
            int len = is.read(data, offset, left);
            left -= len;
            offset += len;
        }
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        try {
            while (true) {

//                int len = is.read(data);
//                if (len == -1)
//                    break;

                int packetLen = dis.readInt();
                read(dis, data, packetLen);
                String message = new String(data, 0, packetLen);
                System.out.println(message);

                // create sea 8000
                listener.onTurnChange(message);
                listener.onUsers(message);
                listener.onDraw(message);
                listener.onSuccess(message);
                listener.onFail(message);
                listener.onDisconnect(message);
                listener.onRefresh(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ChatClient {
    private Socket socket;
    private OutputStream os;
    private DataOutputStream dos;
    private ReaderThread readerThread;

    public void connect() throws IOException {

        new Thread() {
            public void run() {
                try{
                    socket = new Socket("13.230.142.157", 8081);
                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);

                } catch(IOException e){
                    e.printStackTrace();

                }
            }
        }.start();


    }
    public void setMessage() throws IOException {
        String message = "[{"+"userID:"+"dongwonS2"+","+"protocol:"+"message"+","+
                "type:"+"태음인"+","+"message:"+"회원가입 왜 안되냐"+"}]";
        byte[] setMsg = message.getBytes();
        dos.writeInt(setMsg.length);
        dos.write(setMsg);
    }

}