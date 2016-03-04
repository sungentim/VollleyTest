package com.example.tim.volleylearn;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tim.volleylearn.vollyUtil.BitmapCache;

import org.json.JSONObject;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.img)
    ImageView mImg;
    @InjectView(R.id.netWorkImg)
    NetworkImageView mNetWorkImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        sendHttpRequest();
    }

    private void sendHttpRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "json--->" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "json--->" + error.getMessage(), error);
            }
        });
        ImageRequest imageRequest = new ImageRequest("http://developer.android.com/images/home/aw_dac.png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImg.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mImg.setImageResource(R.mipmap.ic_launcher);
            }
        });
        queue.add(stringRequest);
        queue.add(jsonObjectRequest);
//        queue.add(imageRequest);

        ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(mImg, R.mipmap.ic_launcher, R.mipmap.msg_fail);

        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageListener, 400, 400);
        mNetWorkImg.setDefaultImageResId(R.mipmap.msg_fail);
        mNetWorkImg.setErrorImageResId(R.mipmap.ic_launcher);
        mNetWorkImg.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",imageLoader);
    }
}
