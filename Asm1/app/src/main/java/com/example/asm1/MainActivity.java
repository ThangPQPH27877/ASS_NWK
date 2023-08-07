package com.example.asm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm1.Adapter.TableListAdapter;
import com.example.asm1.Api.ApiService;
import com.example.asm1.Model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TableListAdapter.Callback {
    private RecyclerView rcvProduct;
    private TableListAdapter adapter;
    private FloatingActionButton fab;
    private List<ProductModel> mList;
    private String[] colorOptions = {"Blue", "Red", "Brown", "Yellow", "Green", "Orange", "Purple", "Pink", "Black", "White"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        rcvProduct = (RecyclerView) findViewById(R.id.rcv_table);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(itemDecoration);

        adapter = new TableListAdapter(new ArrayList<>(), this);
        rcvProduct.setAdapter(adapter);
        callApiGetTableList();

    }

    private void hienThiDuLieu(List<ProductModel> tableItems) {
        if (tableItems != null) {
            adapter.setTableItems(tableItems);
            adapter.notifyDataSetChanged();
        }
    }

    private void callApiGetTableList() {
        // lay danh sach
        ApiService.apiService.getProduct().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductModel> tableItems = response.body();
                    hienThiDuLieu(tableItems);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.FullScreenDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product, null);
        builder.setView(dialogView);

        final EditText edtName = dialogView.findViewById(R.id.edt_name);
        final EditText edtPrice = dialogView.findViewById(R.id.edt_price);
        final EditText edtQuantity = dialogView.findViewById(R.id.edt_quantity);
        final EditText edtColor = dialogView.findViewById(R.id.edt_color);
        final EditText edtImg = dialogView.findViewById(R.id.edt_img);
        final EditText edtDescription = dialogView.findViewById(R.id.edt_description);
        final Button btnAdd = dialogView.findViewById(R.id.btn_add);
        final Button btnCanel = dialogView.findViewById(R.id.btn_canel);

        final AlertDialog dialog = builder.create();
        edtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder colorDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                colorDialogBuilder.setTitle("Choose Color");
                colorDialogBuilder.setItems(colorOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedColor = colorOptions[which];
                        edtColor.setText(selectedColor);
                    }
                });
                AlertDialog colorDialog = colorDialogBuilder.create();
                colorDialog.show();
            }
        });

        dialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                String color = edtColor.getText().toString().trim();
                String img = edtImg.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                String quantity = edtQuantity.getText().toString().trim();
                if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || color.isEmpty() || img.isEmpty() || description.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    addNewData(name, Integer.parseInt(price), color, img, Integer.parseInt(quantity), description);
                    dialog.dismiss();
                }

            }
        });
        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                dialog.dismiss();
            }
        });

    }

    private void addNewData(String name, int price, String color, String img, int quantity, String description) {
        ProductModel product = new ProductModel();
        product.setName(name);
        product.setPrice(price);
        product.setColor(color);
        product.setImg(img);
        product.setDescription(description);
        product.setQuantity(quantity);

        ApiService.apiService.addCar(product).enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(MainActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();

                    List<ProductModel> tableItems = response.body();
                    hienThiDuLieu(tableItems);
                } else {
                    // Xử lý lỗi khi thêm dữ liệu
                    Toast.makeText(MainActivity.this, "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCar(String id, String name, int price, String color, String img, int quantity, String description) {
        ProductModel productModel = new ProductModel();
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setColor(color);
        productModel.setImg(img);
        productModel.setDescription(description);
        productModel.setQuantity(quantity);

        Call<List<ProductModel>> call = ApiService.apiService.updateCar(id, productModel);
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    List<ProductModel> tableItems = response.body();
                    hienThiDuLieu(tableItems);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }

    @Override
    public void editPr(ProductModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.FullScreenDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        EditText edName = dialog.findViewById(R.id.edt_name);
        EditText edPrice = dialog.findViewById(R.id.edt_price);
        EditText edColor = dialog.findViewById(R.id.edt_color);
        EditText edImg = dialog.findViewById(R.id.edt_img);
        EditText edDescription = dialog.findViewById(R.id.edt_description);
        EditText edQuantity = dialog.findViewById(R.id.edt_quantity);

        Button btnEdit = dialog.findViewById(R.id.btn_add);
        Button btnCancel = dialog.findViewById(R.id.btn_canel);

        btnEdit.setText("Sửa");
        edName.setText(model.getName());
        edPrice.setText(String.valueOf(model.getPrice()));
        edColor.setText(model.getColor());
        edImg.setText(model.getImg());
        edDescription.setText(model.getDescription());
        edQuantity.setText(String.valueOf(model.getQuantity()));

        btnEdit.setOnClickListener(v -> {
            String name = edName.getText().toString().trim();
            String price = edPrice.getText().toString().trim();
            String color = edColor.getText().toString().trim();
            String img = edImg.getText().toString().trim();
            String description = edDescription.getText().toString().trim();
            String quantity = edQuantity.getText().toString().trim();
            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || color.isEmpty() || description.isEmpty() || img.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                updateCar(model.getId(), name, Integer.parseInt(price), color, img, Integer.parseInt(quantity), description);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    @Override
    public void deletePr(ProductModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            deleteProduct(model);
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteProduct(ProductModel model) {
        String id = model.getId();
        Call<List<ProductModel>> call = ApiService.apiService.deleteCars(id);
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    List<ProductModel> tableItems = response.body();
                    hienThiDuLieu(tableItems);
                    //callApiGetTableList();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }

}