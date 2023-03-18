package com.mycompany.payment_system;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONObjUtil {
    public static String toJson(String obj, String opt) {
        JSONObject main = new JSONObject();
        main.put("header", opt);
        main.put("body", obj);

        return main.toJSONString();
    }

    public static String getHeader(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);

        return jsonObject.get("header").toString();

    }

    public static Object getBody(String json) throws ParseException{
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);

        return jsonObject.get("body");
    }
}
