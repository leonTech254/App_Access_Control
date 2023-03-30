package com.example.appmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class RegisterUser extends AppCompatActivity {
    private String email;
    private  String name;
    private String password;
    private byte[] byteArray;
    private  byte[] byteArray_original;
    Dbhelper db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        ImageSet();


    }


    public void ImageSet()
    {
        // Get the ByteArray extra from the Intent
        byteArray = getIntent().getByteArrayExtra("image");
        byteArray_original = getIntent().getByteArrayExtra("image_original");

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");

        TextView nameView=findViewById(R.id.name);
        nameView.setText(name);
        TextView emailView=findViewById(R.id.email);
        emailView.setText(email);

// Convert ByteArray to Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
// Display the Bitmap in an ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

    }
    public void toRegister(View view)
    {
        Intent intent = new Intent(this,Camera2_auth.class);
        startActivity(intent);

    }
    public  void SendRegisterUser(View view)
    {
        SendImage(email,byteArray_original);
    }




    public  void SendImage(String EMailSend,byte[] imageBytes)
    {
//        // Convert frame to Bitmap
//        Mat mat = inputFrame.rgba();
//        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(mat, bitmap);
//
//// Convert Bitmap to byte array
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        byte[] imageBytes = outputStream.toByteArray();

// Encode byte array as Base64 string
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

// Create request body
        HashMap<String, String> params = new HashMap<>();
        params.put("image", encodedImage);
        params.put("email",EMailSend);
        JSONObject requestBody = new JSONObject(params);

// Send request using Volley
        String url = "http://192.168.129.71:5000/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Handle response from server here
                try {
                    String message = response.getString("info");
                    String UserID=response.getString("userId");
                    if (message.equals("success"))
                    {


                        db= new Dbhelper(getApplicationContext());
                       Boolean dbResponse= db.AddUsers(name,UserID,password,EMailSend);
                       if(dbResponse)
                       {
        //                           manage path
                           sharedPreferences =getSharedPreferences("alert", Context.MODE_PRIVATE);
                           String value = sharedPreferences.getString("user", "");
                           if(value.equals("admin"))
                           {
                               Intent intent = new Intent(getApplicationContext() ,Admin_Success_Register.class);
                               startActivity(intent);


                           }else
                           {
                               Intent intent=new Intent(getApplicationContext(),EnrollSuccess.class);
                               intent.putExtra("email",EMailSend);
                               intent.putExtra("usedId",UserID);
                               startActivity(intent);

                           }


                       }else
                       {
                           Toast.makeText(RegisterUser.this, "Failed to save", Toast.LENGTH_SHORT).show();
                       }



                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response from server here
                System.out.println(error);
            }
        });


// Add request to Volley request queue
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }




}