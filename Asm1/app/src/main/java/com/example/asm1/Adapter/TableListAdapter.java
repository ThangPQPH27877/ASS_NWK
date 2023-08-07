package com.example.asm1.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm1.Model.ProductModel;
import com.example.asm1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> {
    private List<ProductModel> list;

    private Callback callback;
    public TableListAdapter(List<ProductModel> list,Callback callback) {
        this.list = list;
        this.callback = callback;
    }
    public void setTableItems(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TableListAdapter.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableListAdapter.TableViewHolder holder, int position) {
        ProductModel model = list.get(position);
        if(model == null){
            return;
        }
        holder.priceTextView.setText(String.valueOf(model.getPrice()));
        holder.nameTextView.setText(model.getName());
        holder.quantityTextView.setText(String.valueOf(model.getQuantity()));
        holder.tvColor.setText(model.getColor());
        holder.tvDescription.setText(model.getDescription());
        Picasso.get().load(model.getImg()).into(holder.imgProduct);
        holder.imgEdit.setOnClickListener(view -> {
            callback.editPr(model);
        });
        holder.imgDelete.setOnClickListener(view -> {
            callback.deletePr(model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView priceTextView;
        private ImageView imgProduct;
        private TextView tvColor;
        private TextView tvDescription;

        private TextView quantityTextView;
        private ImageView imgEdit;
        private ImageView imgDelete;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            priceTextView = itemView.findViewById(R.id.tv_price);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvColor = itemView.findViewById(R.id.tv_color);
            tvDescription = itemView.findViewById(R.id.tv_description);
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }
    public  interface Callback{
        void editPr(ProductModel model);
        void deletePr(ProductModel model);
    }

}
