package com.wirednest.apps.hairstyle.network.service;


import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wirednest.apps.hairstyle.models.HairstylesObject;

import java.io.IOException;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.functions.Func1;


public class APIService {
    private static final String WEB_SERVICE_BASE_URL = "http://192.168.1.104:8000";
    private final APIEndpointInterface mWebService;

    public APIService() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("User-Agent", "Hairstyle-App")
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEB_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        mWebService = retrofit.create(APIEndpointInterface.class);
    }

    public interface APIEndpointInterface {
        @GET("/hairstyles/rest")
        Observable<List<HairstylesObject>> getHairstyles(@Query("imei") String imei);

    }

    public Observable<List<HairstylesObject>> getHairstyles(final String imei){
        return mWebService.getHairstyles(imei)
                .map(new Func1<List<HairstylesObject>, List<HairstylesObject>>() {
                    @Override
                    public List<HairstylesObject> call(List<HairstylesObject> hairstyles) {
                        return hairstyles;
                    }
                });

    }
//    private class APIDataEnvelope {
//        private int httpCode;
//
//        class API {
//
//        }
//
//        /**
//         * The web service always returns a HTTP header code of 200 and communicates errors
//         * through a 'cod' field in the JSON payload of the response body.
//         */
//        public Observable filterWebServiceErrors() {
//            if (httpCode == 200) {
//                return Observable.just(this);
//            } else {
//                return Observable.error(
//                        new HttpException("There was a problem fetching the weather data."));
//            }
//        }
//    }
}
