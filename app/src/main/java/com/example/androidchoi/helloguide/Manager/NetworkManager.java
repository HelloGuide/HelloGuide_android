package com.example.androidchoi.helloguide.Manager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.begentgroup.xmlparser.XMLParser;
import com.example.androidchoi.helloguide.model.PlaceData;
import com.example.androidchoi.helloguide.model.PlaceList;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private static NetworkManager instance;
    RequestQueue request = Volley.newRequestQueue(MyApplication.getContext());
    private XMLParser parser;
    private Gson gson;

    // 싱글톤 인스턴스 get Method
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){
        parser = new XMLParser();
        gson = new Gson();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(String error);
    }

    // 건물 상세 정보 요청 method
    public void getPlaceInfo(String ccbaKdcd, String ccbaCtcd, String ccbaAsno,
                             final OnResultListener<PlaceData> listener) {
        String url = String.format("http://www.cha.go.kr/cha/SearchKindOpenapiDt.do?ccbaKdcd=%s&ccbaCtcd=%s&ccbaAsno=%s", ccbaKdcd, ccbaCtcd, ccbaAsno);
        request.add(new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        ByteArrayInputStream bais = new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8")));
                        Log.i("response", response);
                        PlaceData placeLab = parser.fromXml(bais, "item", PlaceData.class);
                        listener.onSuccess(placeLab);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("Error", error.getMessage());
                        listener.onFail(error.getMessage());
                    }
                }));
    }
    private static final String SERVER = "http://52.192.85.150:8000";

    // 건물 리스트 정보 요청 method
    private static final String GET_PLACE_LIST = SERVER + "/getPlaceList";
    public void getPlaceList(final OnResultListener<PlaceList> listener){
        request.add(new StringRequest(Request.Method.GET, GET_PLACE_LIST,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        PlaceList placeList = gson.fromJson(response, PlaceList.class);
                        listener.onSuccess(placeList);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", error.getMessage());
                        listener.onFail(error.getMessage());
                    }
                }));

    }

    //로그인
    private static final String LOG_IN = SERVER + "/login";
    public void login(final String id, final String pw, final OnResultListener<String> listener){
        request.add(new StringRequest(Request.Method.POST, LOG_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        listener.onSuccess(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("Error", volleyError.getMessage());
                        listener.onFail(volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("pw", pw);
                return params;
            }
        });
    }
}
