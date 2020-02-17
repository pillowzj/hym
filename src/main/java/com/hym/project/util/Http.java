package com.hym.project.util;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author koulijun
 * @Date: 2018/8/27
 * @company
 */
public class Http {
    private static final Logger log = LoggerFactory.getLogger(Http.class);
//private static final Logger log = LoggerFactory.getLogger(Http.class);
    private final static int CONNECT_TIMEOUT = 60;
    private final static int READ_TIMEOUT = 100;
    private final static int WRITE_TIMEOUT = 60;
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static String get(String url)  {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                log.info("response::::\n" + string + "\n---end---\n");
                return string;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String post(String url, String[] head, String JsonObject) throws IOException {
        log.info("request::::\n"+url+"\n"+JsonObject+"\n---end---\n");
        if (head.length != 2) {
            return "RequestHead Error!!!";
        }
        RequestBody body = RequestBody.create(JSON, JsonObject);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("security_factor", head[0])
                .addHeader("signeddata", head[1])
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String string = response.body().string();
            log.info("response::::\n"+string+"\n---end---\n");
//          log.info("response===2==========="+string.replace("\\",""));
            return string;
        } else {

                log.error("response::::\n"+response+"\n---end---\n");

            log.info("response===="+response);
           // throw new IOException("Unexpected code " + response);
            return "";
        }
    }
}
