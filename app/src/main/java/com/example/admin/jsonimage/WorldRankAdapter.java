package com.example.admin.jsonimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class WorldRankAdapter extends RecyclerView.Adapter<WorldRankAdapter.MyViewHolder> {
    Context context;
    List<WorldRank> worldRanks;

    public WorldRankAdapter(Context context, List<WorldRank> worldRanks) {
        this.context = context;
        this.worldRanks = worldRanks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())

                .inflate(R.layout.world_rank_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.rank.setText(worldRanks.get(i).getRank());
        myViewHolder.country.setText(worldRanks.get(i).getCountry());
        myViewHolder.population.setText(worldRanks.get(i).getPopulation());
        //myViewHolder.flag.setImageResource(worldRanks.get(i).getFlag());
        String url = worldRanks.get(i).getFlag();
        new DownloadPage(myViewHolder.flag).execute(url);


    }

    @Override
    public int getItemCount() {
        return worldRanks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView country, rank, population;
        ImageView flag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            rank = itemView.findViewById(R.id.rank);
            population = itemView.findViewById(R.id.population);
            flag = itemView.findViewById(R.id.flag);


        }
    }


    private class DownloadPage extends AsyncTask<String,Void,Bitmap> {
        ImageView flag;

        public DownloadPage(ImageView flag) {
            this.flag=flag;

        }
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL = strings[0];
            Bitmap bitmap = null;
            StringBuilder sb = new StringBuilder(imageURL);

            sb.insert(4, 's');

            String furl=sb.toString();

            try {

                InputStream input = new URL(furl).openStream();

                bitmap = BitmapFactory.decodeStream(input);

                input.close();

                return bitmap;



            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }
        protected void onPostExecute(Bitmap bitmap) {

            super.onPostExecute(bitmap);

            flag.setImageBitmap(bitmap);

//            progressDialog.dismiss();

        }
    }
}
