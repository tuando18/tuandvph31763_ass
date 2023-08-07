package com.dovantuan.tuandvph31763_ass.Fragment;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dovantuan.tuandvph31763_ass.Adapter.AdapterCV;
import com.dovantuan.tuandvph31763_ass.DAO.DaoCV;
import com.dovantuan.tuandvph31763_ass.DTO.DtoCV;
import com.dovantuan.tuandvph31763_ass.MainActivity;
import com.dovantuan.tuandvph31763_ass.Notify.NotifyConfig;
import com.dovantuan.tuandvph31763_ass.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Date;

public class Frag_Home extends Fragment {
    FloatingActionButton fab;
    RecyclerView rc_cv;
    ArrayList<DtoCV> list_cv = new ArrayList<>();
    DaoCV daoCV = new DaoCV(getActivity());
    AdapterCV adapterCV;

    EditText ed_name, ed_content, ed_status, ed_ngaybd, ed_ngaykt;
    Button btn_cancel, btn_addcv;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999999) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, hiển thị notify
                showNotification();
            } else {
                // Quyền bị từ chối, thông báo cho người dùng
                Toast.makeText(requireContext(), "Ứng dụng cần quyền để hiển thị notify", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_home, container, false);
        fab = view.findViewById(R.id.fab);
        rc_cv = view.findViewById(R.id.rc_cv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rc_cv.setLayoutManager(layoutManager);
        daoCV = new DaoCV(getActivity());
        loadDataFromSQL();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.layout_dialog_add, null);
                builder.setView(v);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                btn_cancel = v.findViewById(R.id.btn_cancel);
                btn_addcv = v.findViewById(R.id.btn_add);
                ed_status = v.findViewById(R.id.edt_dialog_status);
                ed_name = v.findViewById(R.id.edt_dialog_name);
                ed_content = v.findViewById(R.id.edt_dialog_content);
                ed_ngaybd = v.findViewById(R.id.edt_dialog_ngaybd);
                ed_ngaykt = v.findViewById(R.id.edt_dialog_ngaykt);

                btn_addcv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = ed_name.getText().toString().trim();
                        String content = ed_content.getText().toString().trim();
                        String status = ed_status.getText().toString().trim();
                        String ngaybd = ed_ngaybd.getText().toString().trim();
                        String ngaykt = ed_ngaykt.getText().toString().trim();
                        if (title.isEmpty() || content.isEmpty() || status.isEmpty() || ngaykt.isEmpty() || ngaybd.isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng điền đủ thông tin vào tất cả ca trường", Toast.LENGTH_SHORT).show();
                        } else {
                            DtoCV objectNew = new DtoCV(title, content, status, ngaybd, ngaykt);
                            daoCV.addRow(objectNew);
                            list_cv.clear();
                            list_cv.addAll(daoCV.getAll());
                            loadDataFromSQL();
                            adapterCV.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            showNotification();
                        }
                    }
                });
                ed_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] type = {"Mới Tạo", "Đang Làm", "Hoàn Thành", "Xóa Khỏi Danh Sách"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Trạng Thái");
                        builder.setItems(type, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ed_status.setText(type[i]);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return view;
    }

    public void loadDataFromSQL() {
        list_cv = daoCV.getAll();
        adapterCV = new AdapterCV(list_cv, getActivity());
        rc_cv.setAdapter(adapterCV);

    }


    public void onResume() {
        super.onResume();
        getActivity().setTitle("Quản Lý Công Việc");
    }

    public void showNotification() {
        // Khai báo Intent để nhận tương tác khi bấm vào notify
        Intent intentDemo = new Intent(getContext(), MainActivity.class);
        intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        intentDemo.putExtra("duLieu", "Dữ liệu gửi từ notify vào activity");

        // ***** tạo Stack để gọi activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(requireContext());
        stackBuilder.addNextIntentWithParentStack(intentDemo);

        // Lấy pendingIntent để truyền vào notify
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // ảnh
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(requireContext(), NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Quản lí công việc")
                .setContentText("Bạn vừa thêm 1 công việc mới")
                .setContentIntent(resultPendingIntent) // intent để nhận tương tác

                //thiết lập cho ảnh to
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(logo).bigLargeIcon(null))
                .setLargeIcon(logo)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireContext());

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 999999);
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime(); // lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy, customNotification);
    }

}
