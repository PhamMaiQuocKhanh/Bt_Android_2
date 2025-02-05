package com.example.bt_anroid3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    int dem=0;
    private static final String CHANNEL_ID = "my_channel_id";
    private static final int REQUEST_CALL_PHONE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Context mycontext = this;
        Button bt1=(Button)findViewById(R.id.cmd_tb1);
        Button bt2=(Button)findViewById(R.id.cmd_tb2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dem++;
                Toast.makeText(mycontext, "Chào mừng bạn đến với ứng dụng! dem="+dem, Toast.LENGTH_LONG).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm hiển thị thông báo
                showNotification("SOS Call 113 ngay", "Ban can goi 113 ngay!");
            }
        });
    }

    private void showNotification(String title, String content) {
        // Tạo NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo NotificationChannel (chỉ cần với Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Tên kênh thông báo",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Mô tả kênh thông báo");
            notificationManager.createNotificationChannel(channel);
        }
        // Intent để xử lý khi bấm vào thông báo
//        Intent intent = new Intent(this, ShowMSG.class); // Chuyển hướng về MainActivity
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

        // Intent mở màn hình quay số
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:113")); // Nhập sẵn số 113

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE // Yêu cầu để chạy trên Android 12+
        );

        // Tạo Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background) // Icon nhỏ cho thông báo (thêm icon vào res/drawable)
                .setContentTitle(title)                   // Tiêu đề thông báo
                .setContentText(content)                 // Nội dung thông báo
                .setContentIntent(pendingIntent) //bam vao thi mo activity nao
                .setAutoCancel(true)         //bam vao thi close Thong bao
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Đặt mức ưu tiên

        // Hiển thị thông báo
        notificationManager.notify(1, builder.build());
    }
}