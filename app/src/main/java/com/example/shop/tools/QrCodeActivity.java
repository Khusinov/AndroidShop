package com.example.shop.tools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



import com.example.shop.R;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class QrCodeActivity extends AppCompatActivity implements Detector.Processor<Barcode>, SurfaceHolder.Callback {
    public static final int ALL_PERMISSIONS_RESULT = 978;


    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private Boolean qrDetected = false;
    private MediaPlayer mediaPlayer;
    private String barcode;
    private SurfaceView sv;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest = new ArrayList<>();

    private int barcodeType = -1;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.take_qr_code));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setElevation(4f);
        }

        makePermissionList();

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("barcode_type"))
                barcodeType = bundle.getInt("barcode_type");
        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_3);
        setBarcodeReaderConfigurations();
    }

    private void setBarcodeReaderConfigurations() {
        if (barcodeType == Constants.BARCODE_TYPE.READ_BARCODE)
            barcodeDetector = new BarcodeDetector.Builder(this)
                    .setBarcodeFormats(
                            Barcode.EAN_13 |
                                    Barcode.EAN_8 |
                                    Barcode.UPC_A |
                                    Barcode.UPC_E |
                                    Barcode.CODE_39 |
                                    Barcode.CODE_93 |
                                    Barcode.CODE_128 |
                                    Barcode.CODABAR
                    )
                    .build();
        else if (barcodeType == Constants.BARCODE_TYPE.READ_QRCODE)
            barcodeDetector = new BarcodeDetector.Builder(this)
                    .setBarcodeFormats(Barcode.QR_CODE)
                    .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(10000, 16000)
                .setAutoFocusEnabled(true)
                .build();

        sv = findViewById(R.id.barcodeCamera);
        sv.getHolder().addCallback(this);
        barcodeDetector.setProcessor(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {

        if (qrDetected)
            return;

        SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() > 0) {
            mediaPlayer.start();

            qrDetected = true;
            barcode = barcodes.valueAt(0).displayValue;
            setResult(barcode);
        }
    }

    private void setResult(String barcode) {
        Intent data = new Intent();
        data.putExtra("barcode", barcode);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                cameraPermission();
                return;
            }

            cameraSource.start(sv.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == ALL_PERMISSIONS_RESULT) {
            if (!checkCameraPermission()) {
                finish();
            }
        }
    }

    private boolean checkCameraPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void makePermissionList() {
        permissions.add(Manifest.permission.CAMERA);
        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[0]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        final ArrayList<String> result = new ArrayList<>();
        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null) {
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraPermission();
    }

    private void cameraPermission() {
        if (!checkCameraPermission())
            makePermissionList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[0]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

}