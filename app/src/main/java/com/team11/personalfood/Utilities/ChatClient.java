package com.team11.personalfood.Utilities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


interface OnReaderListener {
    void onRead(String message);

}

class ReaderThread extends Thread {
    private InputStream is;
    private OnReaderListener onReaderListener;

    void setOnReaderListener(OnReaderListener readerListener) {
        this.onReaderListener = readerListener;
    }

    public ReaderThread(InputStream is, OnReaderListener onReaderListener) {
        this.is = is;
        this.onReaderListener = onReaderListener;
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        try {
            while (true) {
                int len = is.read(data);
                if (len == -1)
                    break;

                String message = new String(data, 0, len);
                if(onReaderListener != null){
                    onReaderListener.onRead(message);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ChatClient {
    private static final String TAG = "ChatClient";
    private Socket socket;
    private OutputStream os;
    private ReaderThread readerThread;
    private BufferedReader bufferedReader = null;
    private String responseString = "";

    public OutputStream getOs() {
        return os;
    }

    private OnReaderListener onReaderListener = new OnReaderListener() {
        @Override
        public void onRead(String message) {
            System.out.println(message);
        }
    };

    public ChatClient() {
        connect();
    }


    public void connect(){

        new Thread() {
            public void run() {
                try {
                    socket = new Socket("13.230.142.157", 8081);
                    os = socket.getOutputStream();

                    boolean result = socket.isConnected();
                    if (result) Log.d(TAG, "서버에 연결됨");
                    else Log.d(TAG, "서버 연결 실패");
                    os.flush();

                    readerThread = new ReaderThread(socket.getInputStream(), onReaderListener);
                    readerThread.start();


                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }.start();


    }

    public void setMessage(String userId, String type, String message) throws IOException {
        new Thread() {
            public void run() {
                try {

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("userID", userId);
                    jsonObject.put("protocol", "message");
                    jsonObject.put("type", type);
                    jsonObject.put("message", message);

                    byte[] setMsg = jsonObject.toString().getBytes();
//                    dos.writeInt(setMsg.length);
//                    dos.write(setMsg);

//                    os.writeInt(setMsg.length);
                    os.write(setMsg);
                    Log.d(TAG, "sended message - " +jsonObject.toString());

                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str;

                    Log.d(TAG, "buffered reader.readLine()- " + bufferedReader.readLine());
                    Log.d(TAG, "buffered reader - " + bufferedReader.toString());
                    Log.d(TAG, "buffered reader test- ");
                    while ((str = bufferedReader.readLine()) != null) {
//                        responseString.append(str);
                        responseString = str;
                        Log.d(TAG, "From server - " + responseString);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        String message = "[{"+"userID:"+"dongwonS2"+","+"protocol:"+"message"+","+
//                "type:"+"태음인"+","+"message:"+"회원가입 왜 안되냐"+"}]";
//        byte[] setMsg = message.getBytes();
//        dos.writeInt(setMsg.length);
//        dos.write(setMsg);
//        Log.d(TAG,"sended message");
    }

}
