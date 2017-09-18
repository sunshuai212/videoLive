package com.shuaisun.videolive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aihuishou.commonlib.utils.ToastUitl;
import com.aihuishou.httplib.api.ApiConstants;
import com.aihuishou.httplib.retrofit.HttpsUtils;
import com.aihuishou.httplib.retrofit.RetrofitUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuaisun.videolive.adapter.LiveListAdapter;
import com.shuaisun.videolive.api.ApiService;
import com.shuaisun.videolive.model.InKeEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ApiService apiService;

    RecyclerView rvLive;

    ArrayList<InKeEntity.LivesBean> dataList=new ArrayList<>();
    LiveListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvLive= (RecyclerView) findViewById(R.id.rv_live);
        initVars();
        loadData();

    }

    private void loadData() {
        apiService.getLives()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    if(response.getDm_error()==0){
                        dataList.addAll(response.getLives());
                        adapter=new LiveListAdapter(dataList);
                        rvLive.setLayoutManager(new LinearLayoutManager(this));
                        rvLive.setAdapter(adapter);
                        adapter.setOnItemClickListener((adapter1, view, position) -> {
                                PlayerActivity.intentTo(this,dataList.get(position).getStream_addr());
                        });
                    }
                },error->{
                    ToastUitl.showErrorToast(error.getLocalizedMessage());
                });

    }

    private void initVars() {
       Retrofit retrofit = RetrofitUtils.getRetrofit(ApiConstants.getHost(1));
        apiService = retrofit.create(ApiService.class);    }
}
