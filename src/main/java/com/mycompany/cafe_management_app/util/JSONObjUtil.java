package com.mycompany.cafe_management_app.util;

import com.mycompany.cafe_management_app.model.Bill;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Map;

public class JSONObjUtil {
    public static String toJson(Bill b, String opt) {
        JSONObject main = new JSONObject();
        main.put("header", opt);
        JSONObject body = new JSONObject();
        body.put("id", b.getId());
        body.put("total", b.getTotalPrice());
        main.put("body", body);

        return main.toJSONString();
    }

    public static String getHeader(String json) {
        Object obj = JSONValue.parse(json);
        JSONObject jsonObject = (JSONObject) obj;

        return (String) jsonObject.get("header");
    }

    public static Object getBody(String json) {
        Object obj = JSONValue.parse(json);
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject.get("body");
    }
}
