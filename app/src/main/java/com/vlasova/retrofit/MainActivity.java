package com.vlasova.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> arrayListArticle;
    newsAdapter newsAdapter;
    Context context;

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> listToArrayList (List<articles> list) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++ ){
            HashMap<String, String> map = new HashMap<>();
            map.put("title", list.get(i).getTitle());
            map.put("description", list.get(i).getDescription());
            map.put("getUrl", list.get(i).getUrl());
            map.put("getUrlToImage", list.get(i).getUrlToImage());

            arrayList.add(i, map);
        }
        return arrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Message[] s = {new Message()};
        setContentView(R.layout.activity_main);

        String url = "https://newsapi.org/";
        arrayListArticle = new ArrayList<>();
        context =  this;

        if (hasConnection(context)) { //проверка на соединение
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            MessagesAPI messagesAPI = retrofit.create(MessagesAPI.class);

            messagesAPI.messages()
                    .subscribeOn(Schedulers.io()) //rx - нужна друшая реализация
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Message>() {
                        @Override
                        public void onSuccess(Message message) { //воврашеяется только один объект, и для него можно реализовать только onSuccess
                            arrayListArticle = listToArrayList(message.getArticles());
                            newsAdapter = new newsAdapter(context, arrayListArticle);
                            ListView lst = (ListView) findViewById(R.id.listNews);
                            lst.setAdapter(newsAdapter); // нужна отдельная реализация для отправки результата в адаптер (для вывода на экран)
                            int i = 0;
                        }

                        @Override
                        public void onError(Throwable e) {
                            int i = 0;
                        }
                    });
        } else {
            Toast toast = Toast.makeText (getApplicationContext(), "Отсутствует соединение с Интернетом", Toast.LENGTH_SHORT);
            toast.show();
        }




        /*Call<Message> messages = messagesAPI.messages();


        messages.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    List<articles> list =  response.body().getArticles();
                    arrayListArticle = (ArrayList<HashMap<String, String>>) listToArrayList(list);
                    int i=0;
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
            }
        });*/
    }
}
