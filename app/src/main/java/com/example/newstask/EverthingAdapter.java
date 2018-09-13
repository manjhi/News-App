package com.example.newstask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EverthingAdapter extends RecyclerView.Adapter<EverthingAdapter.MyViewHolder> {

        List<NewsModel> newsModels;
        List<NewsModel.Article> articles;
        Context context;

    myclick myc;


    public interface myclick{

        public void myonclick(int postion);
    }

public EverthingAdapter(List<NewsModel> newsModels, Context context,myclick myc) {
        this.newsModels = newsModels;
        this.context = context;
        this.myc=myc;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);

        return new MyViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        NewsModel news=newsModels.get(position);
//        holder.auth.setText(news.getArticles().get(position).getAuthor().toString());
//        NewsModel news=newsData.get(position);
//        NewsModel.Article data=articles.get(position);
        if (newsModels.get(position).getArticles().get(position).getAuthor()==null){
//        if (newsModels.get(position).getArticles().get(position).getUrlToImage()!=null){
        holder.auth.setText("null");
        holder.newsTitle.setText(news.getArticles().get(position).getTitle().toString());
        holder.newsName.setText("null");

        Picasso.get().load(news.getArticles().get(position).getUrlToImage()).into(holder.newsImage);
//            holder.newsImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Position "+newsModels.get(position).getArticles().get(position).getUrlToImage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//        holder.auth.setText("null");
//        holder.newsTitle.setText(news.getArticles().get(position).getTitle().toString());
//        holder.newsName.setText("null");
//        }

        }else {
        holder.auth.setText(news.getArticles().get(position).getAuthor().toString());
        holder.newsTitle.setText(news.getArticles().get(position).getTitle().toString());
        holder.newsName.setText(news.getArticles().get(position).getDescription().toString());

        Picasso.get().load(news.getArticles().get(position).getUrlToImage()).into(holder.newsImage);

//            holder.newsImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Position "+newsModels.get(position).getArticles().get(position).getUrlToImage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }


        System.out.println(news.getArticles().get(position).getAuthor());

//        holder.auth.setText(newsData.get(position).getArticles(articles).get(position).getAuthor());
//        holder.newsName.setText(newsData.get(position).getName());
//        holder.newsTitle.setText(newsData.get(position).getTitle());
//        Picasso.get().load(newsData.get(position).getUrlToImage()).into(holder.newsImage);
        }

@Override
public int getItemCount() {
        return newsModels.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView auth,newsTitle,newsName;
    ImageView newsImage;
    public MyViewHolder(View itemView) {
        super(itemView);

        auth=(TextView)itemView.findViewById(R.id.newsauthor);
        newsTitle=(TextView)itemView.findViewById(R.id.newstitle);
        newsName=(TextView)itemView.findViewById(R.id.newsname);
        newsImage=(ImageView)itemView.findViewById(R.id.imgurl);
        newsImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        myc.myonclick(getLayoutPosition());
    }
}
    public void clear(){
        newsModels.clear();
        notifyDataSetChanged();
    }
}
