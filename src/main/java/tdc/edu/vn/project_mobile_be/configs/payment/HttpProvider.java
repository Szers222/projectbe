package tdc.edu.vn.project_mobile_be.configs.payment;


import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class HttpProvider {
    public static JSONObject sendPost(String URL, RequestBody formBody) {
        JSONObject data = new JSONObject();
        try {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectionSpecs(Collections.singletonList(spec))
                    .callTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                data = null;
            } else {
                data = new JSONObject(response.body().string());
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
