package com.luokes.network;

import com.luokes.utils.JsonUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class HttpUtils {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String JSON = "application/json";

    private static HttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    private HttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private synchronized static HttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new HttpUtils();
        }
        return mInstance;
    }

    /**
     * GET 请求的请求体
     *
     * @param url      url
     * @param callback callback
     */
    private void getRequest(String url, final AbstractCallback callback) {

        Request request ;

        request = new Request.Builder()
                .addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .url(url)
                .build();

        doResult(callback, request);
    }

    private void postRequest(String url, final AbstractCallback callback, Object param) {
        Request request = buildPostRequest(url, param);
        doResult(callback, request);
    }


    private void doResult(final AbstractCallback callback, final Request request) {

        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure( Call call,  IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse( Call call, Response response) {

                try {
                    String str = Objects.requireNonNull(response.body()).string();
                    System.out.println("返回参数:" + str);

                        sendSuccessCallBack(callback, JsonUtils.deserialize(str, callback.mType));
                } catch (final Exception e) {
                    sendFailCallback(callback, e);
                    e.printStackTrace();
                }
            }
        });
    }


    private void sendFailCallback(final AbstractCallback callback, final Exception e) {
        callback.onFailure(e);
        e.printStackTrace();
    }

    @SuppressWarnings("unchecked")
    private void sendSuccessCallBack(final AbstractCallback callback, final Object obj) {
        if (callback != null) {
            try {
                callback.onSuccess(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Request buildPostRequest(String url, Object param) {
        RequestBody requestBody;
        System.out.println("请求参数:\n" + JsonUtils.serialize(param));
        requestBody = RequestBody.create(JSON_TYPE, JsonUtils.serialize(param));


        return new Request.Builder()
                .url(url)
                .header("Content-Type", JSON)
                .post(requestBody)
                .build();
    }

    /**
     * Post的JSON请求
     *
     * @param url      接口地址
     * @param callback 回调
     * @param param    请求数据
     */
    public static void post(String url, final AbstractCallback callback, Object param) {
        getInstance().postRequest(url, callback, param);
    }

    /**
     * get请求
     * @param url url
     * @param callback callBack
     */
    public static void get(String url, AbstractCallback callback) {
        System.out.println("GET请求"+url);
        getInstance().getRequest(url, callback);
    }

    /**
     * post 请求
     *
     * @param url      URL
     * @param callback 回调
     * @param map      map
     * @param files    文件
     */
    public static void postMultipart(String url, AbstractCallback callback, Map<String, String> map, Map<String, File> files) {
        getInstance().multipartRequest(url, callback, map, files);
    }

    /**
     * From 的POST 请求
     * @param url url
     * @param callback callback
     * @param map map
     */
    public static void postFrom(String url, AbstractCallback callback, Map<String, String> map) {
        getInstance().fromRequest(url, callback, map);
    }


    private void fromRequest(String url, AbstractCallback callback, Map<String, String> map) {

        FormBody.Builder builder = new FormBody.Builder();
        if (map != null && map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .addHeader("from","app")
                .post(body)
                .url(url)
                .build();

        doResult(callback, request);
    }


    /**
     * 将map转换成url
     *
     * @param map map
     * @return s
     */
    public static String getUrlParams(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        String getCode = "&";
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append(getCode);
        }
        String s = sb.toString();
        if (s.endsWith(getCode)) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }


    /**
     * GET 请求的请求体
     *
     * @param url      url
     * @param callback callback
     */
    private void multipartRequest(String url, final AbstractCallback callback, Map<String, String> map, Map<String, File> files) {

        final MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String s : map.keySet()) {
            body.addFormDataPart(s, map.get(s));
        }

        for (String fileName : files.keySet()) {
            String header = "form-data; name=\"" + fileName + "\";filename=\" " + files.get(fileName).getName() + "\"";
            body.addPart(Headers.of("Content-Disposition", header), RequestBody.create(MediaType.parse("image/jpeg"), files.get(fileName)));
            File f = files.get(fileName);
        }
        RequestBody requestBody = body.build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        doResult(callback, request);
    }

}
