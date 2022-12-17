package com.example.apiwork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateApi extends AppCompatActivity {

    private EditText UpdUser, UpdKonfirurate, UpdZena;
    private Button UpdButton;
    private ImageView imageButtonUpd;
    String ImgUpd="";
    Mask mask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_api);

        mask=getIntent().getParcelableExtra("zakaz");

        UpdUser = findViewById(R.id.userUpd);
        UpdUser.setText(mask.getUser());

        UpdKonfirurate = findViewById(R.id.konfigurateUpd);
        UpdKonfirurate.setText(mask.getKonfiguracia());

        UpdZena = findViewById(R.id.zenaUpd);
        UpdZena.setText(Integer.toString(mask.getZena()));

        UpdButton = findViewById(R.id.updButton);
        imageButtonUpd = findViewById(R.id.imageViewUpd);
        imageButtonUpd.setImageBitmap(getImgBitmap(mask.getImg()));
        configureBackButton();
    }
    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(UpdateApi.this.getResources(),
                    R.drawable.ic_launcher_playstore);
        }

    }

    private void configureBackButton() {
        Button back = (Button) findViewById(R.id.otmUpd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void onClickChooseImage(View view)
    {
        getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButtonUpd.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButtonUpd.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage() {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }
    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ImgUpd=Base64.getEncoder().encodeToString(bytes);
            return ImgUpd;
        }
        return "";
    }

    public void Update_click(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateApi.this);
        builder.setTitle("Изменение")
                .setMessage("Вы уверены что хотите изменить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ImgUpd == null)
                        {
                            ImgUpd = mask.getImg();
                        }
                        else {
                            Update(UpdUser.getText().toString(), UpdKonfirurate.getText().toString(), UpdZena.getText().toString(), ImgUpd);
                            SystemClock.sleep(1000);
                            Next();
                        }
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    private void Update(String user, String konfiguracia, String zena,String Image)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/МанаковКА/api/zakazis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUpdate update = retrofit.create(APIUpdate.class);
        DataModal modal = new DataModal(user,konfiguracia,Integer.parseInt(zena),Image);
        Call<DataModal> call = update.updateData(mask.getId_zakaza(),modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(UpdateApi.this, "Данные изменены", Toast.LENGTH_SHORT).show();
                DataModal responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }
    public  void delete()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/МанаковКА/api/zakazis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIDelete delete = retrofit.create(APIDelete.class);
        Call<DataModal> call = delete.deleteData(mask.getId_zakaza());

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(UpdateApi.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                DataModal responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });

    }
    public void Delet_Click(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateApi.this);
        builder.setTitle("Удалить")
                .setMessage("Вы уверены что хотите Удалить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete();
                        new CountDownTimer(1000, 1000) {
                            public void onFinish() {
                                Next();
                            }

                            public void onTick(long millisUntilFinished) {

                            }
                        }.start();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}