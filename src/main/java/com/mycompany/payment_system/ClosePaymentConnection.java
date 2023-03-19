package com.mycompany.payment_system;

import com.mycompany.cafe_management_app.util.ClientUtil;

import java.io.IOException;

public class ClosePaymentConnection {

    public static void closeConnectionWithPaymentServer() throws IOException {
        ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(null, "END"));
    }
}
