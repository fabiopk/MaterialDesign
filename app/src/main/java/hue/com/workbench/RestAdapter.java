package hue.com.workbench;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Fábio on 11/2/2015.
 */
public class RestAdapter extends RecyclerView.Adapter<RestAdapter.MyViewHolder> {
        private final LayoutInflater inflater;
        List<Restaurante> data = Collections.emptyList();
    public RestAdapter(Context context, List<Restaurante> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurante current = data.get(position);
        holder.name.setText(current.name);
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(current.imlink.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        holder.icon.setImageBitmap(bmp);
        holder.icon.setImageResource(R.drawable.ic_cast_light);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.restName);
            icon = (ImageView) itemView.findViewById(R.id.restIcon);

        }
    }
}
