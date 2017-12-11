package com.team11.personalfood;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ToggleButton;

import com.team11.personalfood.Models.Chat;
import com.team11.personalfood.Models.ChatModel;
import com.team11.personalfood.Utilities.ChatClient;
import com.team11.personalfood.Utilities.ChatListRecyclerAdapter;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.OnChatLoadListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements OnChatLoadListener {
    private static final String TAG = "ChatActivity";



    //View
    private ImageButton filterBtn;
    private ToggleButton toggleTaeyangBtn;
    private ToggleButton toggleTaeeumBtn;
    private ToggleButton toggleSoyangBtn;
    private ToggleButton toggleSoeumBtn;

    @BindView(R.id.send_message_btn)
    private Button sendMessageBtn;


    @BindView(R.id.chatList_recyclerView)
    RecyclerView chatListRecyclerView;
    private ChatListRecyclerAdapter adapter;
    @BindView(R.id.field_message)
    AppCompatEditText fieldMessage;

    private Client client;
    private ChatClient chatClient;
    private ChatModel chatModel;
    private String myID;
    private String myType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        filterBtn = findViewById(R.id.filter_button);
        sendMessageBtn = findViewById(R.id.send_message_btn);
        fieldMessage = findViewById(R.id.field_message);
        chatListRecyclerView = findViewById(R.id.chatList_recyclerView);

        setUpChatListView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        setSupportActionBar(toolbar);

//        communicator = new Communicator(this);
//        connectChatServer();
        chatModel = new ChatModel();
//        client = new Client(this);
        chatModel.setOnChatLoadListener(this);
        chatModel.fetchChat();

        myID = Client.getCurrentUser().getUserID();
        myType = Client.getCurrentUser().getType();

        //팝업 토글 온클릭
        ImageButton.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.filter_button:
                    LinearLayout mainLayout = findViewById(R.id.layout_chat);

                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_toggle, null);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

                    dimBehind(popupWindow);
                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                    break;
            }
        };


        sendMessageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Log.d(TAG, "sendBtn Clicked");

                if (chatClient.getOs() != null) {
                    Log.d(TAG, " - DOS NOT NULL -" + chatClient.getOs());
                    try {
                        chatClient.setMessage(myID, myType, fieldMessage.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Chat chat = new Chat(myID, myType, fieldMessage.getText().toString());
                adapter.addItem(chat);

                chatListRecyclerView.scrollToPosition(adapter.getItemCount()-1);
                fieldMessage.setText("");

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);

        filterBtn.setOnClickListener(onClickListener);

        new Thread() {
                    public void run() {
                        chatClient = new ChatClient();
                        if (chatClient.getOs() != null) {
                            Log.d(TAG, " - DOS NOT NULL -" + chatClient.getOs());
                        }

                    }
                }.start();
    }


    // 토글 팝업 뒷배경 흐림
    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    private void setUpChatListView() {
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        chatListRecyclerView.setLayoutManager(manager);

        adapter = new ChatListRecyclerAdapter();
        chatListRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onFetchChat(List<Chat> chatList) {

        adapter.setItems(chatList);
        adapter.notifyDataSetChanged();
    }

}
