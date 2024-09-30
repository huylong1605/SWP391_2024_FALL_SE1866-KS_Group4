package org.example.kindergarten_management_system_g4.controller.authencation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.example.kindergarten_management_system_g4.constant.Iconstant;

import java.io.IOException;

public class GoogleToken {
    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Iconstant.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }
}
