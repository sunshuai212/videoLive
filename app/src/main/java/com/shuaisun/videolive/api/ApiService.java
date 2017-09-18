package com.shuaisun.videolive.api;

import com.shuaisun.videolive.model.InKeEntity;


import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by shuaisun on 2017/9/17.
 */

public interface ApiService {

    @GET(ApiConstants.INKE_URL)
    public Observable<InKeEntity> getLives();

}
