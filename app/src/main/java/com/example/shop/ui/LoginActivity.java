package com.example.shop.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shop.HttpHandler;
import com.example.shop.R;
import com.example.shop.SharedPreferences;
import com.example.shop.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText ipadress;
    private EditText loginEdt;
    private EditText passwordEdt;
    private Button logInBtn;
    private Button logOutBtn;
    private TextView message;
    private TextView ipmessage;
    private Intent intent;
    private User thisUser;
    private ProgressDialog progressDialog;
    private final int PERMISSION_REQUEST_CODE = 1001;


    private static String urlLogin = "http://192.168.1.10:8080/application/json/user";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ipadress = (EditText) findViewById(R.id.ipadress);
        loginEdt = (EditText) findViewById(R.id.loginEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);
        logInBtn = (Button) findViewById(R.id.logInBtn);
        message = (TextView) findViewById(R.id.message);
        ipmessage = (TextView) findViewById(R.id.ipmessage);

        ipadress.setText(loadIP());
        loginEdt.setText(loadLogin());
        passwordEdt.setText(loadPassword());
        thisUser = new User();
        checkAndRequestPermissions();

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        ipadress.setFilters(filters);


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errorIpCheck(ipadress.getText().toString())) {
                    if (!loginEdt.getText().toString().matches("")) {
                        ipmessage.setText(R.string.invalid_ip);
                        message.setText(R.string.invalid_password);
                        ipmessage.setVisibility(View.INVISIBLE);
                        message.setVisibility(View.INVISIBLE);
                        new getUserCheck().execute();
                    } else {
                        message.setText(R.string.username_error);
                    }
                } else {
                    ipmessage.setText(R.string.ip_input_error);
                    ipmessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


    Boolean errorIpCheck(String ip) {
        Integer cnt = 0;
        for (int i = 0; i < ip.length(); i++) {
            if (ip.charAt(i) == '.')
                cnt++;
        }
        if (cnt == 3)
            return true;
        return false;
    }

    String loadIP() {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        return sPref.getString(SharedPreferences.IP_ADRES_SHARED_PREF, "");
    }

    public void saveIP(String ip) {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SharedPreferences.IP_ADRES_SHARED_PREF, ip);
        ed.apply();
    }

    String loadLogin() {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        return sPref.getString(SharedPreferences.LOGIN_SHARED_PREF, "");
    }

    void saveLogin(String login) {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SharedPreferences.LOGIN_SHARED_PREF, login);
        ed.apply();
    }

    String loadPassword() {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        return sPref.getString(SharedPreferences.PASSWORD_SHARED_PREF, "");
    }

    void savePassword(String password) {
        android.content.SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SharedPreferences.PASSWORD_SHARED_PREF, password);
        ed.apply();
    }


    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }


    private class getUserCheck extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Tizimga kirish uchun tekshirilmoqda");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            thisUser.setUsername(loginEdt.getText().toString());
            Log.v("MyLog", convertPassMd5(passwordEdt.getText().toString()));
            thisUser.setUserpass(convertPassMd5(passwordEdt.getText().toString()));
            urlLogin = "http://" + ipadress.getText().toString() + ":8080/application/json/user";
            Log.v("MyLog", urlLogin);
            HttpHandler httpHandler = new HttpHandler();
            Log.v("MyLog", "ha ina qu");
            String jsonStr = httpHandler.makeServiceCallPost(urlLogin, thisUser);
            Log.v("MyLog", "ha beda akan");

//            Log.v("MyLog",jsonStr);
            if (jsonStr != null) {
                try {
//                    JSONArray jsonArray = new JSONArray(jsonStr);
                       /*
                          "id": 10,
                            "clientId": 4,
                            "username": "Bobur",
                            "userpass": "c4ca4238a0b923820dcc509a6f75849b",
                            "fio": "Bobur",
                            "delFlag": 1*/

                    JSONObject jsonObject = new JSONObject(jsonStr);
                    thisUser.setId(jsonObject.getInt("id"));
                    thisUser.setClient_id(jsonObject.getInt("clientId"));
                    thisUser.setUsername(jsonObject.getString("username"));
                    thisUser.setUserpass(jsonObject.getString("userpass"));
                    thisUser.setFio(jsonObject.getString("fio"));
                    thisUser.setDelFlag(jsonObject.getInt("delFlag"));
                    Log.v("MyLog", "Ah sani");

                } catch (final JSONException e) {
                    Log.v("MyTag2", e.getMessage());
                }
            } else {
                Log.v("MyTag2", "serverdan galmadi");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(LoginActivity.this,"Сервер билан муамо бор",Toast.LENGTH_LONG).show();
                        ipmessage.setVisibility(View.VISIBLE);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (thisUser.getId() != null) {
                intent = new Intent(LoginActivity.this, TypeChangeActivity.class);
                intent.putExtra("user", thisUser);
                intent.putExtra("ip", ipadress.getText().toString());
                intent.putExtra("password", passwordEdt.getText().toString());
        //       intent.putExtra("asosId",asosId);
                saveIP(ipadress.getText().toString());
                saveLogin(loginEdt.getText().toString());
                savePassword(passwordEdt.getText().toString());
                startActivity(intent);
                finish();
            } else {
                Log.v("MyLog", "else Hato");
                if (ipmessage.getVisibility() == View.INVISIBLE) {
                    message.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
