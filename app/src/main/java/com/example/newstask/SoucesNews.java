package com.example.newstask;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoucesNews extends Fragment {

    ArrayList<NewsModel> newsModels=new ArrayList<>();
    ArrayList<NewsModel.Article> articles=new ArrayList<>();


    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SourceNewsAdapter sourceNewsAdapter;
    int x=0;
    ZoomageView imageView;
    Button button;
    public SoucesNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_souces_news, container, false);


        recyclerView=(RecyclerView)v.findViewById(R.id.newsrecy);
        recyclerView.setHasFixedSize(true);


        imageView=v.findViewById(R.id.image_detail);
        button=v.findViewById(R.id.close);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });


        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        x=1;

        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swip_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Cretificate.handleHTTPS();
                getNewsData();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark,R.color.newcolor,R.color.newcolo1);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                Cretificate.handleHTTPS();
                getNewsData();
            }
        });

        CircleImageView fb=v.findViewById(R.id.actionButton);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x==0){
                    RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    x=1;
                }else if (x==1){
                    RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    x=2;
                }else if (x==2){
//                    RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    x=0;
                }
            }
        });
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        Cretificate.handleHTTPS();
        getNewsData();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getNewsData() {
        Api api = ApiClient.apiclient().create(Api.class);
        Call<NewsModel> call = api.getSourceNews();
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                Toast.makeText(getContext(), "Sucess ", Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                Log.d("Data", json);

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");

                    String status = response.body().getStatus();
                    Log.d("Status", status);

                    int Arraylength = response.body().getTotalResults();
                    Log.d("Length", String.valueOf(Arraylength));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        NewsModel.Article art = new NewsModel.Article();

                        NewsModel newsModel = new NewsModel();

                        newsModel.setArticles(articles);
                        System.out.println(response.body().getArticles().get(i).getAuthor());
                        art.setAuthor(response.body().getArticles().get(i).getAuthor());
                        art.setDescription(response.body().getArticles().get(i).getDescription());
                        art.setUrlToImage(response.body().getArticles().get(i).getUrlToImage());

                        art.setTitle(response.body().getArticles().get(i).getTitle());

                        articles.add(art);
                        newsModel.setArticles(articles);
                        newsModels.add(newsModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
                sourceNewsAdapter = new SourceNewsAdapter(newsModels, getContext(), new SourceNewsAdapter.myclick() {
                    @Override
                    public void myonclick(int postion) {
                        imageView.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        Log.d("Position", String.valueOf(postion));
                        Toast.makeText(getContext(), "Position"+postion, Toast.LENGTH_SHORT).show();
                        Picasso.get().load(newsModels.get(postion).getArticles().get(postion).getUrlToImage()).into(imageView);
                    }
                });
                sourceNewsAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(sourceNewsAdapter);


            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(getContext(), "Fail " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
