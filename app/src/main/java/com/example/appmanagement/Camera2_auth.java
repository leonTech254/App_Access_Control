package com.example.appmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;

import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;





public class Camera2_auth extends CameraActivity {


    private CameraBridgeViewBase mOpenCvCamewaView;
    protected static String OpenCVLog = "OPENCV_LOG";
    private static boolean sendData=false;
    static Context context;
    private static int MIN_IMAGES=2;
    ImageView preview;
    private  static  int registrationFaceCount;

//    define data to send
    private static String EMailSend;
    private static String name;
    private  String passwordSend;
    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.d(OpenCVLog, "opencv loaded");
                    mOpenCvCamewaView.enableView();

                }
                break;
                default: {
                    super.onManagerConnected(status);

                }
                break;

            }
            super.onManagerConnected(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_auth);

        if (OpenCVLoader.initDebug()) {
            System.out.println("opencv initiated");
            Log.d(OpenCVLog, "open cv initiated");

        }
        mOpenCvCamewaView = (CameraBridgeViewBase) findViewById(R.id.openCvCamera);

    }
    public void EnrollFace(View view)
    {
        EditText Name=(EditText) findViewById(R.id.Rusername);
        EditText EMail=(EditText) findViewById(R.id.Remail);
        EditText password=(EditText) findViewById(R.id.Rpassword);
        if(Name.getText().toString().equals("d")||EMail.getText().toString().equals("d") ||password.getText().toString().equals("d") )
        {
            Toast.makeText(this, "All field required", Toast.LENGTH_SHORT).show();

        }else
        {
            sendData=true;
            Toast.makeText(this, "Starting Camera", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Scanning face rec", Toast.LENGTH_SHORT).show();

            EMailSend=EMail.getText().toString();
            name=Name.getText().toString();
            passwordSend=password.getText().toString();

            mOpenCvCamewaView.setVisibility(SurfaceView.VISIBLE);
            mOpenCvCamewaView.setCvCameraViewListener(cvCameraViewListener);
            mOpenCvCamewaView.setCameraIndex(1);
        }


    }
    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCamewaView);

    }
    private CameraBridgeViewBase.CvCameraViewListener2 cvCameraViewListener = new CameraBridgeViewBase.CvCameraViewListener2() {
        @Override
        public void onCameraViewStarted(int width, int height) {

        }

        @Override
        public void onCameraViewStopped() {


        }











        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            Mat mRgba = inputFrame.rgba();
            //            converting image to grey scale
            Mat greyImage = inputFrame.gray();




            MatOfRect faces = new MatOfRect();
//            rotating the images colored
            Mat mRgbaT = mRgba.t();
            Core.flip(mRgba.t(), mRgbaT, 0);
            Imgproc.resize(mRgbaT, mRgbaT, mRgba.size());
            //            rotating the images colored
            Mat greyImageT = greyImage.t();
            Core.flip(mRgba.t(), greyImageT, 0);
            Imgproc.resize(mRgbaT, greyImageT, mRgba.size());



            try
            {
                InputStream is=getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
                File cascadeDir=getDir("cascade",Context.MODE_PRIVATE);
                File mCascadeFile=new File(cascadeDir,"haarcascade_frontalface_alt.xml");
                FileOutputStream os = new FileOutputStream(mCascadeFile);
                byte[] buffer=new byte[4096];
                int bytesRead;
                while((bytesRead = is.read(buffer)) !=-1)
                {
                    os.write(buffer,0,bytesRead);
                }
                is.close();
                os.close();
                CascadeClassifier faceDetector =new CascadeClassifier((mCascadeFile.getAbsolutePath()));
                faceDetector.detectMultiScale(greyImageT, faces);
//                 Draw rectangles around detected faces
                Rect[] facesArray = faces.toArray();
                for(int i=0; i<facesArray.length;i++)
                {

                    Imgproc.rectangle(mRgbaT, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
                    System.out.println("face recongonation detected");
                    if(sendData)//if a button is clicked
                    {
                        registrationFaceCount++;
                        if(registrationFaceCount>=MIN_IMAGES)
                        {
                            Bitmap bmp = Bitmap.createBitmap(mRgbaT.cols(), mRgbaT.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(mRgbaT, bmp);

                            Mat mat = inputFrame.rgba();
                            Bitmap SendImage = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(mat, SendImage);
                            ToRegisterUser(bmp,SendImage);



//                    destroy image After Training
//                    onDestroy();


                        }

                    }

                }
//
                System.out.println("okay");
                cascadeDir.delete();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println("eror");

            }




            return mRgbaT;
        }

        public void ToRegisterUser(Bitmap Image,Bitmap OriginImage) {

            System.out.println("hello bmp");
            // setting bitmap to ImageView on the UI thread
            // Create Intent and send Bitmap as extra
            Intent intent = new Intent(Camera2_auth.this, RegisterUser.class);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

//            orinal image
            ByteArrayOutputStream stream_original = new ByteArrayOutputStream();
            OriginImage.compress(Bitmap.CompressFormat.PNG, 100, stream_original);
            byte[] byteArray_original = stream_original.toByteArray();




            intent.putExtra("image", byteArray);
            intent.putExtra("image_original", byteArray_original);
            intent.putExtra("email",EMailSend);
            intent.putExtra("name",name);
            intent.putExtra("password",passwordSend);


            startActivity(intent);

        }





    };
    @Override
    public  void onPause() {
        super.onPause();
        if(mOpenCvCamewaView!=null)
        {
            mOpenCvCamewaView.disableView();

        }
    }
    @Override
    public  void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(OpenCVLog, "openCv not found initializing");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallBack);

        }else
        {
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        }






    }
    @Override
    public  void onDestroy() {
        super.onDestroy();
        if(mOpenCvCamewaView!=null)
        {
            mOpenCvCamewaView.disableView();
        }
    }













    public void toApps(View view)
    {
        Intent intent = new Intent(this,AppListManage.class);
        startActivity(intent);
    }
}



