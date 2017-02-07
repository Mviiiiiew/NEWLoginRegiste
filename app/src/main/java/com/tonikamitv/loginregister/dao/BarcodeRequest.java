package com.tonikamitv.loginregister.dao;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BarcodeRequest extends StringRequest {
    private static final String BARCODE_REQUEST_URL = "http://10.0.3.2/test/Barcode.php";
    private Map<String, String> params;

    public BarcodeRequest(String barcode, Response.Listener<String> listener) {
        super(Method.POST, BARCODE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("barcode", barcode);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
