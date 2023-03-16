package com.mycompany.cafe_management_app.util;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSONObjUtil {
    public static String toJson(Object obj, String opt) {
        JSONObject json = new JSONObject();
        json.put("header", opt);
        json.put("body", obj);

        return json.toJSONString();
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
