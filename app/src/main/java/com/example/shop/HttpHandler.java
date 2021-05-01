package com.example.shop;

import android.util.Log;

import com.example.shop.model.AsosModell;
import com.example.shop.model.Product;
import com.example.shop.model.STovar;
import com.example.shop.model.SeriesModel;
import com.example.shop.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    private static String TAG = "MyHttp";
    private static String url = "http://";

    public HttpHandler() {

    }


    public String makeServiceCallPost(String reqUrl, User user) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("username", user.getUsername());
            jsonParam.put("userpass", user.getUserpass());


            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes());
            os.flush();
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (ProtocolException e) {
            Log.v(TAG, "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v(TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v(TAG, "IOException: " + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;

    }

    public String makeServiceCreate(String reqUrl, AsosModell asosModell) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            Log.v("Http", asosModell.getUserId() + "");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("client_id", asosModell.getClient_id());
            jsonParam.put("userId", asosModell.getUserId());
            jsonParam.put("xodimId", asosModell.getXodimId());
            jsonParam.put("haridorId", asosModell.getHaridorId());
            jsonParam.put("sana", asosModell.getSana());
            jsonParam.put("diler_id", asosModell.getDiler_id());
            jsonParam.put("tur_oper", asosModell.getTur_oper());
            jsonParam.put("summa", asosModell.getSumma());
            jsonParam.put("sotuv_turi", asosModell.getSotuv_turi());
            jsonParam.put("nomer", asosModell.getNomer());
            jsonParam.put("del_flag", asosModell.getDel_flag());
            jsonParam.put("dollar", asosModell.getDollar());
            jsonParam.put("kurs", asosModell.getKurs());
            jsonParam.put("sum_d", asosModell.getSum_d());
            jsonParam.put("kol", asosModell.getKol());

            Log.d("err", jsonParam.toString());
             /*{
                    "client_id": 4,
                    "userId": 30,
                    "xodimId": 10,
                    "haridorId": 0,
                    "sana": "",
                    "dilerId": 1,
                    "turOper": 2,
                    "summa": 4500.0,
                    "sotuvTuri": 1,
                    "nomer": "4521",
                    "del_flag": 1,
                    "dollar": 4526,
                    "kurs": 1,
                    "sum_d": 1452,
                    "kol": 2
            }
*/
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes());
            os.flush();
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (ProtocolException e) {
            Log.v(TAG, "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v(TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v(TAG, "IOException: " + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;

    }


    public Integer makeServiceAddNewProducts(String reqUrl, STovar newProducts, User user) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            object.put("id", newProducts.getId());
            object.put("user_id", user.getUser_id());
            object.put("nom", newProducts.getNom());
            Log.d("nom_shshs", newProducts.getNom_sh());
            object.put("nom_sh", newProducts.getNom_sh());
            object.put("shtrix", newProducts.getShtrix());
            object.put("shtrix1", newProducts.getShtrix1());
            object.put("shtrix2", newProducts.getShtrix2());
            object.put("brend", 0);
            object.put("papka", 0);
            object.put("tz_id", 0);
            object.put("shtrix_full", newProducts.getShtrix_full());
            object.put("qr", 0);
            object.put("kg", 0);
            object.put("tkol", newProducts.getTkol());
            object.put("tkol_in", newProducts.getTkol_in());
            object.put("nom_ru", "");
            object.put("shtrixkod", 1);
            object.put("qrkod", 0);
            object.put("izm_id", 1);
            object.put("del_flag", 0);
            object.put("client_id", user.getClient_id());
            object.put("brend", 0);
            object.put("sotish", newProducts.getSotish());
            object.put("ulg1", newProducts.getUlg1());
            object.put("ulg2", newProducts.getUlg2());
            object.put("ulg1_pl", newProducts.getUlg1_pl());
            object.put("ulg2_pl", newProducts.getUlg2_pl());
            object.put("bank", newProducts.getBank());
            object.put("sena", newProducts.getSena());
            object.put("kol_in", newProducts.getKol_in());
            object.put("sena_d", newProducts.getSena_d());
            object.put("sena_in_d", newProducts.getSena_in_d());
            object.put("seriya", newProducts.getSeriya());


            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());


        } catch (IOException e) {
            Log.v("MyTag", e.getMessage());
        } catch (JSONException e) {
            Log.v("MyTag", e.getMessage());
        }
        return 0;
    }

    public Integer makeServiceChangeProducts(String reqUrl, STovar newProducts, User user, Integer kat, Integer brend, Integer papka, String nom_sh) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            object.put("id", newProducts.getId());
            object.put("user_id", user.getUser_id());
            object.put("nom", newProducts.getNom());
            object.put("nom_sh", nom_sh);
            object.put("shtrix", newProducts.getShtrix());
            object.put("shtrix1", newProducts.getShtrix1());
            object.put("shtrix2", newProducts.getShtrix2());
            object.put("brend", brend);
            object.put("papka", papka);
            object.put("tz_id", 0);
            object.put("shtrix_full", 0);
            object.put("qr", 0);
            object.put("kg", 0);
            object.put("tkol", newProducts.getTkol());
            object.put("tkol_in", newProducts.getTkol_in());
            object.put("shtrix_in", 0);
            object.put("nom_ru", "");
            object.put("shtrixkod", 1);
            object.put("qrkod", 0);
            object.put("izm_id", 1);
            object.put("del_flag", 0);
            object.put("client_id", user.getClient_id());
            object.put("brend", brend);
            Log.d("katofs", brend.toString());
            object.put("kat", kat);
            object.put("sotish", newProducts.getSotish());
            object.put("ulg1", newProducts.getUlg1());
            object.put("ulg2", newProducts.getUlg2());
            object.put("ulg1_pl", newProducts.getUlg1_pl());
            object.put("ulg2_pl", newProducts.getUlg2_pl());
            object.put("bank", newProducts.getBank());
            object.put("sena", newProducts.getSena());
            object.put("kol_in", newProducts.getKol_in());
            object.put("sena_d", newProducts.getSena_d());
            object.put("sena_in_d", newProducts.getSena_in_d());
            object.put("seriya", newProducts.getSeriya());


            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());


        } catch (IOException e) {
            Log.v("MyTag", e.getMessage());
        } catch (JSONException e) {
            Log.v("MyTag", e.getMessage());
        }
        return 0;
    }


    public Integer makeServicePostSeries(String reqUrl, SeriesModel serial, Integer slaveId) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            Log.d("errros", slaveId + "  " + serial.getSerial());


            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());


        } catch (IOException e) {
            Log.v("MyTag", e.getMessage());
        }
        return 0;
    }

    public Integer makeServicePostSeriesWithSlave(String reqUrl, SeriesModel serial, Integer slaveId) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            object.put("slave_id", slaveId);
            object.put("serial", serial.getSerial());
            Log.d("errros", slaveId + serial.getSerial());


            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Log.d("HttpID", response.toString());
            return Integer.parseInt(response.toString());


        } catch (IOException | JSONException e) {
            Log.v("MyTag", e.getMessage());
        }
        return 0;
    }

    // delete qilish makeservice
    public Integer makeServiceDelete(String reqUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            //   object.put("id", id);
            // Log.d("ERRORinMakeService", id.toString() + object.toString());

            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // edit qilish
    public Integer makeServiceEdit(String reqUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT"); // aniqmi shu
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
//            object.put("id" , id);
//            object.put("tkol" , tkol);
//            object.put("tkol_in" , tkol_in);
            //object.put("value" , value);
            //   object.put("id", id);
            // Log.d("ERRORinMakeService", id.toString() + object.toString());

            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Integer makeServiceAddNewProduct(String reqUrl, Product selectedItem) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            try {
                object.put("id", selectedItem.getPutId());
                object.put("name", selectedItem.getName());
                object.put("count", selectedItem.getCount());
                object.put("incount", selectedItem.getIncount());
                object.put("price", selectedItem.getPrice());
                object.put("inprice", selectedItem.getInprice());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Integer makeServiceAddProduct(String reqUrl, Product selectedItem) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            try {
                object.put("id", selectedItem.getPutId());
                object.put("productId", selectedItem.getId());
                object.put("name", selectedItem.getName());
                object.put("count", selectedItem.getCount());
                object.put("incount", selectedItem.getIncount());
                object.put("price", selectedItem.getPrice());
                object.put("inprice", selectedItem.getInprice());
                object.put("incnt", selectedItem.getIncnt());
                /*{
                    "id": 3407,
                        "productId": 1088,
                        "nameShort": null,
                        "name": "1091. Фенал 1820 ",
                        "count":  10,
                        "incount": 9,
                        "price": 20000,
                        "inprice": 0,
                        "shtrix": "8803465418203",
                        "incnt": 12
                }*/
                Log.v("Http", selectedItem.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return Integer.parseInt(response.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putProduct(String reqUrl, Product selectedItem) {

        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            try {
                object.put("id", selectedItem.getPutId());
                object.put("productId", selectedItem.getId());
                object.put("name", selectedItem.getName());
                object.put("count", selectedItem.getCount());
                object.put("incount", selectedItem.getIncount());
                object.put("price", selectedItem.getPrice());
                object.put("inprice", selectedItem.getInprice());
                object.put("incnt", selectedItem.getIncnt());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void makeServiceDelItem(String reqUrl) {
        String response = "0";
        HttpURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);

            if (conn.getResponseCode() == 204) {
                Log.d(TAG, "Deleted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Void makeServiceBlockAsos(String reqUrl, Integer asosId) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            JSONObject object = new JSONObject();
            try {
                object.put("id", asosId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(object.toString());
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            Log.v("MyLog4", conn.getResponseMessage() + "");

        } catch (ProtocolException e) {
            Log.v(TAG, "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v(TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v(TAG + "4", "IOException: " + e.getMessage());
        }
        return null;

    }


    public String makeServiceCall(String reqUrl) {

        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (ProtocolException e) {
            Log.v("asasa", "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v("sasa", "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v("uiuiu", "IOException: " + e.getMessage());
        }
        return response;
    }

    public String getSeriesService(String reqUrl) {

        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (ProtocolException e) {
            Log.v("asasa", "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v("sasa", "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v("uiuiu", "IOException: " + e.getMessage());
        }
        return response;
    }


    public String makeServiceCreateAsos(String reqUrl, User user, Integer haridorId, Integer type) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            /* {
                "clientId": 4,
                "userId": 99,
                "xodimId": 99,
                "haridorId":
                "dilerId":0,
                "turOper": 2



        }*/

            jsonParam.put("client_id", user.getClient_id());
            jsonParam.put("userId", user.getId());
            jsonParam.put("xodimId", user.getId());
            jsonParam.put("haridorId", haridorId);
            jsonParam.put("diler_id", 11); // zatish garak
            jsonParam.put("tur_oper", 2);
            jsonParam.put("sotuv_turi", type);


            conn.setConnectTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes());
            os.flush();
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (ProtocolException e) {
            Log.v(TAG, "ProtocolExceptio: " + e.getMessage());
        } catch (MalformedURLException e) {
            Log.v(TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.v(TAG, "IOException: " + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Integer makeServiceChangeAsos(String reqUrl, AsosModell inserAsos) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject object = new JSONObject();
            Log.v("HttpInserAsos", inserAsos.toString() + "");

            object.put("id", inserAsos.getId());
            Log.d("HTTPasosID", inserAsos.getId().toString());
            object.put("client_id", inserAsos.getClient_id());
            object.put("userId", inserAsos.getUserId());
            object.put("xodimId", inserAsos.getXodimId());
            object.put("haridorId", inserAsos.getHaridorId());
            object.put("sana", inserAsos.getSana());
            object.put("diler_id", inserAsos.getDiler_id());
            object.put("tur_oper", inserAsos.getTur_oper());
            object.put("summa", inserAsos.getSumma());
            object.put("sotuv_turi", inserAsos.getSotuv_turi());
            object.put("nomer", inserAsos.getNomer());
            object.put("del_flag", inserAsos.getDel_flag());
            object.put("dollar", inserAsos.getDollar());
            object.put("kurs", inserAsos.getKurs());
            object.put("sum_d", inserAsos.getSum_d());
            object.put("kol", inserAsos.getKol());

            Log.d("err", object.toString());


            String jsonInputString = object.toString();
            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.capacity();//  Integer.parseInt(response.toString());


        } catch (IOException e) {
            Log.v("MyTag", e.getMessage());
        } catch (JSONException e) {
            Log.v("MyTag", e.getMessage());
        }
        return 0;
    }


    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


}
