package com.team11.personalfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Models.Food;
import com.team11.personalfood.Models.FoodModel;
import com.team11.personalfood.Utilities.Client;
import com.team11.personalfood.Utilities.FoodListRecyclerAdapter;
import com.team11.personalfood.Utilities.OnFoodLoadListener;
import com.team11.personalfood.Utilities.OnLoginListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends BaseActivity implements OnFoodLoadListener{


    private static final String TAG = "ListActivity";
    private static final String TAG_CATEGORY = "Category";
    private static final String TAG_FOOD_NAME = "Food_Name";
    private static final String TAG_INGREDIENT = "Ingredient";
    private static final String TAG_POSITIVE_INGREDIENT = "Positive_Ingredient";
    private static final String TAG_NEGATIVE_INGREDIENT = "Negative_Ingredient";
    private static final String TAG_FOOD_URL = "Food_URL";
    private static final String CONSTITUTION_URL = "http://13.230.142.157:8080/a/constitution/";

    private RecyclerView foodListRecyclerView;
    private EditText searchEditText;
    private FoodListRecyclerAdapter adapter;
    private FoodModel foodModel;

    private Button allCategoryBtn;
    private Button riceCategoryBtn;
    private Button kimchiCategoryBtn;
    private Button breadCategoryBtn;
    private Button noodleCategoryBtn;
    private Button saladCategoryBtn;
    private Button iceCreamCategoryBtn;
    private Button soupCategoryBtn;
    private Button pizzaCategoryBtn;
    private Button msgCategoryBtn;
    private Button sideDishCategoryBtn;
    private Button sauceCategoryBtn;
    private Button etcCategoryBtn;
    private Button searchBtn;


    private Client typeClient;
    private Client allCategoryClient;
    private Client riceClient;
    private Client kimchiClient;
    private Client breadClient;
    private Client noodleClient;
    private Client saladClient;
    private Client iceCreamClient;
    private Client soupClient;
    private Client pizzaClient;
    private Client msgClient;
    private Client sideDishClient;
    private Client sauceClient;
    private Client etcClient;
    private Client searchClient;

    private String userType;
    private CurrentUser user;
    private int counter= 0;
    private String mUrl;

    public static Context listActivityContext;



    private ArrayList<HashMap<String, String>> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        user = (CurrentUser) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        setSupportActionBar(toolbar);

        listActivityContext = this;

        userType = user.getType();
        foodListRecyclerView = findViewById(R.id.foodList_recyclerView);
        searchEditText = findViewById(R.id.search_editText);

        allCategoryBtn = findViewById(R.id.allCategory_button);
        riceCategoryBtn = findViewById(R.id.riceCategory_button);
        kimchiCategoryBtn = findViewById(R.id.kimchiCategory_button);
        breadCategoryBtn = findViewById(R.id.breadCategory_button);
        noodleCategoryBtn = findViewById(R.id.noodleCategory_button);
        saladCategoryBtn = findViewById(R.id.saladCategory_button);
        iceCreamCategoryBtn = findViewById(R.id.iceCreamCategory_button);
        soupCategoryBtn = findViewById(R.id.soupCategory_button);
        pizzaCategoryBtn = findViewById(R.id.pizzaCategory_button);
        msgCategoryBtn = findViewById(R.id.msgCategory_button);
        sideDishCategoryBtn = findViewById(R.id.sideDishCategory_button);
        sauceCategoryBtn = findViewById(R.id.sauceCategory_button);
        etcCategoryBtn = findViewById(R.id.etcCategory_button);
        searchBtn = findViewById(R.id.search_button);

        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

        typeClient = new Client(this);
        allCategoryClient = new Client(this);
        riceClient = new Client(this);
        kimchiClient = new Client(this);
        breadClient = new Client(this);
        noodleClient = new Client(this);
        saladClient = new Client(this);
        iceCreamClient = new Client(this);
        soupClient = new Client(this);
        pizzaClient = new Client(this);
        msgClient = new Client(this);
        sideDishClient = new Client(this);
        sauceClient = new Client(this);
        etcClient = new Client(this);
        searchClient = new Client(this);

        mUrl = CONSTITUTION_URL + user.getType();
        Log.d(TAG,"mURL - " + mUrl);
        Log.d(TAG,"userType - " + userType);
        typeClient.getData(mUrl);
//        typeClient.getData("http://13.230.142.157:8080/a/constitution/태음인");
        allCategoryClient.getData("http://13.230.142.157:8080/a/data/");
        riceClient.getData("http://13.230.142.157:8080/a/data/밥-죽");
        kimchiClient.getData("http://13.230.142.157:8080/a/data/김치-젓갈-장아찌");
        breadClient.getData("http://13.230.142.157:8080/a/data/떡-빵-과자");
        noodleClient.getData("http://13.230.142.157:8080/a/data/면류-만두");
        saladClient.getData("http://13.230.142.157:8080/a/data/샐러드-수프");
        iceCreamClient.getData("http://13.230.142.157:8080/a/data/음료류-빙과-유제품");
        soupClient.getData("http://13.230.142.157:8080/a/data/국물요리");
        pizzaClient.getData("http://13.230.142.157:8080/a/data/피자-스파게티-스테이크");
        msgClient.getData("http://13.230.142.157:8080/a/data/장류-조미료-가루류");
        sideDishClient.getData("http://13.230.142.157:8080/a/data/반찬");
        sauceClient.getData("http://13.230.142.157:8080/a/data/ 잼-드레싱-소스");
        etcClient.getData("http://13.230.142.157:8080/a/data/기타 요리별 레시피");


        setUpFoodListView();

        allCategoryBtn.setOnClickListener(btnOnClickListener);
        riceCategoryBtn.setOnClickListener(btnOnClickListener);
        kimchiCategoryBtn.setOnClickListener(btnOnClickListener);
        breadCategoryBtn.setOnClickListener(btnOnClickListener);
        noodleCategoryBtn.setOnClickListener(btnOnClickListener);
        saladCategoryBtn.setOnClickListener(btnOnClickListener);
        iceCreamCategoryBtn.setOnClickListener(btnOnClickListener);
        soupCategoryBtn.setOnClickListener(btnOnClickListener);
        pizzaCategoryBtn.setOnClickListener(btnOnClickListener);
        msgCategoryBtn.setOnClickListener(btnOnClickListener);
        sideDishCategoryBtn.setOnClickListener(btnOnClickListener);
        sauceCategoryBtn.setOnClickListener(btnOnClickListener);
        etcCategoryBtn.setOnClickListener(btnOnClickListener);
        searchBtn.setOnClickListener(btnOnClickListener);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_chat) {
            Log.d(TAG, "menu chat button clicked");
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        } else if (id == R.id.menu_back) {
            Log.d(TAG, "menu back button clicked");
            Activity activity = getParent();
            activity.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void setUpFoodListView() {
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        foodListRecyclerView.setLayoutManager(manager);
        adapter = new FoodListRecyclerAdapter();
        foodListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onFetchFood(List<Food> foodList) {
        adapter.setItems(foodList);
        adapter.notifyDataSetChanged();
    }


    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.allCategory_button:
                    Log.d(TAG,"getType - " + user.getType());
                    Log.d(TAG, "allCategoryBtn Clicked");
                    mArrayList = allCategoryClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.riceCategory_button:
                    Log.d(TAG, "riceCategoryBtn Clicked");
                    mArrayList = riceClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.kimchiCategory_button:
                    Log.d(TAG, "kimchiCategoryBtn Clicked");
                    mArrayList = kimchiClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.breadCategory_button:
                    Log.d(TAG, "breadCategoryBtn Clicked");
                    mArrayList = breadClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.noodleCategory_button:
                    Log.d(TAG, "noodleCategoryBtn Clicked");
                    mArrayList = noodleClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.saladCategory_button:
                    Log.d(TAG, "saladCategoryBtn Clicked");
                    mArrayList = saladClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.iceCreamCategory_button:
                    Log.d(TAG, "iceCreamCategoryBtn Clicked");
                    mArrayList = iceCreamClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.soupCategory_button:
                    Log.d(TAG, "soupCategoryBtn Clicked");
                    mArrayList = soupClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.pizzaCategory_button:
                    Log.d(TAG, "pizzaCategoryBtn Clicked");
                    mArrayList = pizzaClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.msgCategory_button:
                    Log.d(TAG, "msgCategoryBtn Clicked");
                    mArrayList = msgClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.sideDishCategory_button:
                    Log.d(TAG, "sideDishCategoryBtn Clicked");
                    mArrayList = sideDishClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.sauceCategory_button:
                    Log.d(TAG, "sauceCategoryBtn Clicked");
                    mArrayList = sauceClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.etcCategory_button:
                    Log.d(TAG, "etcCategoryBtn Clicked");
                    mArrayList = etcClient.getFoodResult(typeClient.getTypeResult());
                    setFood();
                    break;
                case R.id.search_button:
                    Log.d(TAG, "searchBtn Clicked");
                    if(counter==0) {
                        Log.d(TAG, "searchBtn counter 0");
                        String searchingFood = searchEditText.getText().toString();
                        String url = "http://13.230.142.157:8080/a/data/food/" + searchingFood;
                        searchClient.getData(url);

                        counter++;
                    } else if(counter == 1) {
                        Log.d(TAG, "searchBtn counter 1");
                        mArrayList = searchClient.getFoodResult(typeClient.getTypeResult());
                        for(int i = 0 ; i < searchClient.getmArrayList().size(); i++){
//                        System.out.println("searchClient" + searchClient.getmArrayList().get(i));
                            Log.d(TAG, "SearchBtn searchClient - " + searchClient.getmArrayList().get(i));
                        }
                        setFood();
                        counter = 0;
                    }


//                    mArrayList = searchClient.getFoodResult(typeClient.getTypeResult());
//                    setFood();
                    break;
            }
        }
    }

    public void setFood() {
        foodModel = new FoodModel(mArrayList, TAG_FOOD_NAME, TAG_FOOD_URL, TAG_POSITIVE_INGREDIENT, TAG_NEGATIVE_INGREDIENT);
        foodModel.setOnFoodLoadListener(this);
        foodModel.fetchFood();
        mArrayList.clear();
    }
}
