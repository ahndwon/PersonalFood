package com.team11.personalfood;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.team11.personalfood.Models.Chat;
import com.team11.personalfood.Models.ChatModel;
import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Utilities.ChatClient;
import com.team11.personalfood.Utilities.ChatListRecyclerAdapter;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.OnChatLoadListener;
import com.team11.personalfood.Utilities.OnLoginListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements OnChatLoadListener {
    private static final String TAG = "ChatActivity";

    //View
    private ImageButton filterBtn;
    @BindView(R.id.send_message_btn)
    private Button sendMessageBtn;


    @BindView(R.id.chatList_recyclerView)
    RecyclerView chatListRecyclerView;
    private ChatListRecyclerAdapter adapter;
    @BindView(R.id.field_message)
    AppCompatEditText fieldMessage;

    private ChatClient chatClient;
    private CurrentUser user = new CurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = (CurrentUser) getIntent().getSerializableExtra("user");

        filterBtn = findViewById(R.id.filter_button);
        sendMessageBtn = findViewById(R.id.send_message_btn);
        fieldMessage = findViewById(R.id.field_message);
        chatListRecyclerView = findViewById(R.id.chatList_recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        setSupportActionBar(toolbar);

//        communicator = new Communicator(this);
//        connectChatServer();
//        chatModel = new ChatModel();
//        client = new Client(this);
//        chatModel.setOnChatLoadListener(this);
//        chatModel.fetchChat();
        chatClient = new ChatClient(this);
        chatClient.connect("", user.getUserID());

        setUpChatListView();

        //팝업 토글 온클릭
        ImageButton.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.filter_button:
                    LinearLayout mainLayout = findViewById(R.id.layout_chat);

                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_toggle, null);

                    TextView taeyangText = popupView.findViewById(R.id.toggle_taeyang_text);
                    TextView taeeumText = popupView.findViewById(R.id.toggle_taeeum_text);
                    TextView soyangText = popupView.findViewById(R.id.toggle_soyang_text);
                    TextView soeumText = popupView.findViewById(R.id.toggle_soeum_text);

                    Typeface typeface = Typeface.createFromAsset(popupView.getContext().getAssets(), "210 직장인의한마디R.ttf");
                    taeyangText.setTypeface(typeface);
                    taeeumText.setTypeface(typeface);
                    soyangText.setTypeface(typeface);
                    soeumText.setTypeface(typeface);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

                    ToggleButton tae_yang = popupView.findViewById(R.id.toggle_taeyang);
                    ToggleButton tae_eum = popupView.findViewById(R.id.toggle_taeeum);
                    ToggleButton so_yang = popupView.findViewById(R.id.toggle_soyang);
                    ToggleButton so_eum = popupView.findViewById(R.id.toggle_soeum);

                    tae_yang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chatClient.connect("태양인", user.getUserID());
                        }
                    });

                    tae_eum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chatClient.connect("태음인", user.getUserID());

                        }
                    });

                    so_yang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chatClient.connect("소양인", user.getUserID());

                        }
                    });

                    so_eum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chatClient.connect("소음인", user.getUserID());

                        }
                    });

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
                        chatClient.setMessage(user.getUserID(), user.getType(), fieldMessage.getText().toString());
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

//                Chat chat = new Chat(myID, myType, fieldMessage.getText().toString());


                chatListRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
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
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onAddChat(Chat chat) {
        adapter.addItem(chat);
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
}
