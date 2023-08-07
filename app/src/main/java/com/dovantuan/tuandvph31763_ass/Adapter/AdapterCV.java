package com.dovantuan.tuandvph31763_ass.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dovantuan.tuandvph31763_ass.DAO.DaoCV;
import com.dovantuan.tuandvph31763_ass.DTO.DtoCV;
import com.dovantuan.tuandvph31763_ass.R;

import java.util.ArrayList;


public class AdapterCV extends RecyclerView.Adapter<AdapterCV.ViewHolderCV> {

    ArrayList<DtoCV> list_CV;
    Context context;
    DaoCV daoCV;

    public AdapterCV(ArrayList<DtoCV> list_CV, Context context) {
        this.list_CV = list_CV;
        this.context = context;
        daoCV = new DaoCV(context);
    }

    @NonNull
    @Override
    public ViewHolderCV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_lv_cv,parent,false);
        ViewHolderCV holder = new ViewHolderCV(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCV holder, int position) {
        DtoCV objcvc =list_CV.get(position);
        holder.tv_ten.setText(objcvc.getName());
        holder.tv_trangthai.setText("Trạng Thái: "+objcvc.getStatus());

        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setIcon(R.drawable.thongbao);
                builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");

                builder.setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = list_CV.get(holder.getAdapterPosition()).getId();
                        boolean check = daoCV.removeRow(id);
                        if (check) {
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            list_CV.clear();
                            list_CV = daoCV.getAll();
                            notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        holder.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DtoCV dtoCV = list_CV.get(holder.getAdapterPosition());
                DialogUpdate(dtoCV);
            }
        });

        holder.btn_xemchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DtoCV dtoCV = list_CV.get(holder.getAdapterPosition());
                DialogViewDetail(dtoCV);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_CV.size();
    }

    public class ViewHolderCV extends RecyclerView.ViewHolder{
        TextView tv_ten,tv_trangthai;
        ImageView btn_sua,btn_xoa;

        LinearLayout btn_xemchitiet;
        public ViewHolderCV(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tv_tieude);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_item);
            btn_sua = itemView.findViewById(R.id.btn_sua);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
            btn_xemchitiet = itemView.findViewById(R.id.btn_view);
        }


    }

    public void DialogUpdate(DtoCV Update) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_update,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText Title = view.findViewById(R.id.edt_dialog_name_update);
        EditText content = view.findViewById(R.id.edt_dialog_content_update);
        EditText status = view.findViewById(R.id.edt_dialog_status_update);
        EditText ngaybd = view.findViewById(R.id.edt_dialog_ngaybd_update);
        EditText ngaykt = view.findViewById(R.id.edt_dialog_ngaykt_update);
        Button btnUpdate = view.findViewById(R.id.btn_add_update);
        Button btnCancel = view.findViewById(R.id.btn_cancel_update);

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] type ={"Mới Tạo", "Đang Làm","Hoàn Thành","Xóa Khỏi Danh Sách"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Trạng Thái");
                builder.setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        status.setText(type[i]);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        Title.setText(Update.getName());
        content.setText(Update.getContent());
        status.setText(Update.getStatus());
        ngaybd.setText(Update.getStart());
        ngaykt.setText(Update.getEnd());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Titlee = Title.getText().toString().trim();
                String Contentt = content.getText().toString().trim();
                String Statuss = status.getText().toString().trim();
                String ngaybdd = ngaybd.getText().toString().trim();
                String ngayktt = ngaykt.getText().toString().trim();

                DtoCV cv = new DtoCV(Update.getId(),Titlee,Contentt,Statuss ,ngaybdd, ngayktt);
                boolean check = daoCV.updateRow(cv);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    list_CV.clear();
                    list_CV = daoCV.getAll();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogViewDetail(DtoCV detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_xemchitiet, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextView title = view.findViewById(R.id.edt_dialog_name_xct);
        TextView content = view.findViewById(R.id.edt_dialog_content_xct);
        TextView status = view.findViewById(R.id.edt_dialog_status_xct);
        TextView ngaybd = view.findViewById(R.id.edt_dialog_ngaybd_xct);
        TextView ngaykt = view.findViewById(R.id.edt_dialog_ngaykt_xct);
        Button btnCancel = view.findViewById(R.id.btn_cancel_xct);
        // Thêm dòng này để lấy tham chiếu tới nút btn_hienthi

        title.setText(detail.getName());
        content.setText(detail.getContent());
        status.setText(detail.getStatus());
        ngaybd.setText(detail.getStart());
        ngaykt.setText(detail.getEnd());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
