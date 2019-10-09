package com.vlasova.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MessagesAPI {
    @GET("v2/top-headlines?sources=google-news-ru&apiKey=4b1b5539788e4bf3be958892a276769f")
    //Call <Message> messages();
    Single<Message> messages();
}
