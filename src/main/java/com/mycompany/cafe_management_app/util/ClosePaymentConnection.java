package com.mycompany.cafe_management_app.util;

import com.mycompany.payment_system.JSONObjUtil;

import java.io.IOException;

public class ClosePaymentConnection {

    public static void closeConnectionWithPaymentServer() throws IOException {
        ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(null, "END"));
    }
}
