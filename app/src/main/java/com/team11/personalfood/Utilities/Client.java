package com.team11.personalfood.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.team11.personalfood.LoginActivity;
import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    private static String TAG = "ClIENT";

    private static final String TAG_CATEGORY = "Category";
    private static final String TAG_FOOD_NAME = "Food_Name";
    private static final String TAG_INGREDIENT = "Ingredient";
    private static final String TAG_POSITIVE_INGREDIENT = "Positive_Ingredient";
    private static final String TAG_NEGATIVE_INGREDIENT = "Negative_Ingredient";
    private static final String TAG_FOOD_URL = "Food_URL";


    private LoginData loginData;
    private InsertData insertData;

    String userId;
    String password;

    private Context mContext;

    private ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;
    private OnFoodLoadListener onFoodLoadListener;


    public Client(Context context) {
        this.mContext = context;
        mArrayList = new ArrayList<>();
//        currentUser = new CurrentUser();

//        GetData task = new GetData();
//        task.execute(url);
    }

    public void getData(String url) {
        new GetData(onFoodLoadListener).execute(url);
//        GetData task = new GetData();
//        task.execute(url);
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        private OnFoodLoadListener onFoodLoadListener;

        public GetData(OnFoodLoadListener onFoodLoadListener) {
            this.onFoodLoadListener = onFoodLoadListener;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();


            Log.d(TAG, "response  - " + result);

            if (result == null) {
                Log.d(TAG, "result  - " + result);
            } else {
                mJsonString = result;
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                mJsonString = sb.toString().trim();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    //음식 데이터 가져오기
    public ArrayList getFoodResult(ArrayList<HashMap> typeIngredientList) {
        String[] token = new String[0];
        String[] negativeToken = new String[0];
        String[] positiveToken = new String[0];
        String negativeString = "";
        String positiveString = "";
        positiveToken = typeIngredientList.get(0).get(TAG_POSITIVE_INGREDIENT).toString().split(",");
        negativeToken = typeIngredientList.get(0).get(TAG_NEGATIVE_INGREDIENT).toString().split(",");
        try {
            JSONArray jsonArray = new JSONArray(mJsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String foodName = item.getString(TAG_FOOD_NAME);
                String ingredient = item.getString(TAG_INGREDIENT);
                String foodUrl = item.getString(TAG_FOOD_URL);
                token = ingredient.split(",");

                HashMap<String, String> filteredHashMap = new HashMap<>();

                filteredHashMap.put(TAG_FOOD_NAME, foodName);

                for (int j = 0; j < token.length; j++) {
                    for (int k = 0; k < negativeToken.length; k++) {
                        if (token[j].equals(negativeToken[k])) {
                            negativeString += token[j] + ", ";
                        }
                    }

                    for (int k = 0; k < positiveToken.length; k++) {
                        if (token[j].equals(positiveToken[k])) {
                            positiveString += token[j] + ", ";
                        }
                    }
                }

                if (negativeString.length() > 0) {
                    filteredHashMap.put(TAG_NEGATIVE_INGREDIENT, negativeString.substring(0, negativeString.length() - 2));
                } else {
                    filteredHashMap.put(TAG_NEGATIVE_INGREDIENT, "");
                }
                if (positiveString.length() > 0) {
                    filteredHashMap.put(TAG_POSITIVE_INGREDIENT, positiveString.substring(0, positiveString.length() - 2));
                } else {
                    filteredHashMap.put(TAG_POSITIVE_INGREDIENT, "");

                }
                if (negativeString.length() > 0 || positiveString.length() > 0) {
                    filteredHashMap.put(TAG_FOOD_URL, foodUrl);
                    mArrayList.add(filteredHashMap);
                }
                negativeString = "";
                positiveString = "";
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
        Log.d(TAG, "showFilteredArrayList : " + mArrayList);

        return mArrayList;
    }

    //체질에 따른 재료 정보 가져오기
    public ArrayList getTypeResult() {

        try {
            JSONArray jsonArray = new JSONArray(mJsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String negIngredient = item.getString(TAG_NEGATIVE_INGREDIENT);
                String posIngredient = item.getString(TAG_POSITIVE_INGREDIENT);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_NEGATIVE_INGREDIENT, negIngredient);
                hashMap.put(TAG_POSITIVE_INGREDIENT, posIngredient);


                mArrayList.add(hashMap);
                Log.d(TAG, "showTypeArrayList : " + mArrayList);
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
        return mArrayList;
    }

    public ArrayList getSearchedFood() {
        try {
            JSONArray jsonArray = new JSONArray(mJsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String negIngredient = item.getString(TAG_NEGATIVE_INGREDIENT);
                String posIngredient = item.getString(TAG_POSITIVE_INGREDIENT);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_NEGATIVE_INGREDIENT, negIngredient);
                hashMap.put(TAG_POSITIVE_INGREDIENT, posIngredient);


                mArrayList.add(hashMap);
                Log.d(TAG, "showTypeArrayList : " + mArrayList);
            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
        return mArrayList;
    }


    public ArrayList<HashMap<String, String>> getmArrayList() {
        return mArrayList;
    }

    public class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext,
                    "Please Wait", null, true, true);

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            Log.d(TAG, "POST response  - " + result);

            if (result.equals("Bad Request")) {
                Toast.makeText(mContext, "서버와 연결이 안되네요", Toast.LENGTH_SHORT);
                SignupActivity.mErrorText.setText("서버와 연결이 안되네요");

            }
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = params[0];
            String password = params[1];
            String name = params[2];
            String birth = params[3];
            String type = params[4];


            String serverURL = "http://13.230.142.157:8080/a/users/join/";


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("userID", params[0]);
                jsonParam.put("password", params[1]);
                jsonParam.put("name", params[2]);
                jsonParam.put("birth", params[3]);
                jsonParam.put("type", params[4]);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonParam.toString().getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


    public class LoginData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        private OnLoginListener onLoginListener;
        private CurrentUser user;

        public void setOnLoginListener(OnLoginListener onLoginListener) {
            this.onLoginListener = onLoginListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            user = new CurrentUser();

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

            if (!result.equals("wrong login")) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject parser = jsonArray.getJSONObject(0);
//                JSONObject parser = new JSONObject(result.substring(1,result.length()-1));

                    user.setUserID(parser.getString("UserID"));
                    user.setPassword(parser.getString("Password"));
                    user.setName(parser.getString("Name"));
                    user.setBirth(parser.getString("Birth"));
                    user.setType(parser.getString("Type"));

                    Log.d(TAG, "currentUser.getUserID() - " + user.getUserID());

                    onLoginListener.onSuccess(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result == null) {
                    Log.d(TAG, errorString);
                } else {

                }
            }


//            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
//        startActivity(intent);
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = params[0];
            String password = params[1];

            String serverURL = "http://13.230.142.157:8080/a/users/login/";

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userID", params[0]);
                jsonParam.put("password", params[1]);
                Log.d(TAG, "jsonParam - " + "[" + jsonParam.toString() + "]");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonParam.toString().getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response message - " + httpURLConnection.getResponseMessage());

                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    public void startLogin(String userId, String password, OnLoginListener onLoginListener) {
        loginData = new LoginData();
        loginData.setOnLoginListener(onLoginListener);
        loginData.execute(userId, password);

    }

    public void startSignup(String userId, String password, String name, String birth, String userType) {
        insertData = new InsertData();
        insertData.execute(userId, password, name, birth, userType);
    }

    public JSONObject resultToJson(String response) {
        JSONObject parser = null;
        try {
            parser = new JSONObject(response);
            String userID = parser.getString("UserID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parser - " + parser);
        return parser;
    }
}
