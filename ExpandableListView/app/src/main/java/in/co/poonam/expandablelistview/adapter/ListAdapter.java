package in.co.poonam.expandablelistview.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.co.poonam.expandablelistview.R;
import in.co.poonam.expandablelistview.model.DataModel;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public Context context;
    public List<DataModel> listItem;

    public ListAdapter(Context context, List<DataModel> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        ViewHolder myHolder = new ViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(listItem.get(position).getDeviceTitle());
        holder.txtQuantity.setText(String.valueOf(listItem.get(position).getDeviceQuantity()));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(listItem.get(position).getInventoryDate() * 1000);
        String date = c.get(Calendar.DATE) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        holder.txtDate.setText(date);
        holder.ratingBar.setRating(listItem.get(position).getRating());

        //Checked internet connection for loading image
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Used glide for loading image
            Glide.with(context)
                    .load(listItem.get(position).getImage_path())
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(holder.img_device_image);
        } else {
            holder.img_device_image.setBackgroundResource(R.drawable.ic_placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtTitle;
        protected TextView txtQuantity;
        protected TextView txtDate;
        private RatingBar ratingBar;
        private ImageView img_device_image;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            img_device_image = itemView.findViewById(R.id.img_device_image);
            txtDate = itemView.findViewById(R.id.txtDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
