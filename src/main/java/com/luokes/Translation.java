package com.luokes;

import com.alibaba.fastjson.JSONArray;
import com.luokes.config.Config;
import com.luokes.google.Google;
import com.luokes.io.ReadFile;
import com.luokes.io.WriteFile;
import com.luokes.network.AbstractCallback;
import com.luokes.network.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Translation {

    public static void main(String[] strings) {
        ReadFile.myRead(Config.inPutXmlFile);
    }

    public static void translation(String[] texts) {
        String tk = Google.token(texts[1]);
        Map<String, Object> map = new HashMap<>(6);
        map.put("client", "webapp");
        map.put("sl", Config.inputLanguage);
        map.put("tl", Config.outPutLanguage);
        map.put("dt", "t");
        map.put("tk", tk);
        map.put("q", texts[1]);
        String urlEnd = HttpUtils.getUrlParams(map);
        String url = "https://translate.google.cn/translate_a/single" + urlEnd;
        HttpUtils.get(url, new AbstractCallback<ArrayList>() {
            @Override
            public void onSuccess(ArrayList response) {
                JSONArray arr = (JSONArray) response.get(0);
                String msg = (String) ((JSONArray) arr.get(0)).get(0);
                WriteFile.myWrite(
                        Config.outPutXmlFile,
                        texts[0].replace(texts[1], msg)
                                .replace("％1 $ s", "％1$s")
                                .replace("％2 $ s", "％2$s")
                );
                System.out.println("结果\n" + texts[0].replace(texts[1], msg));
            }

            @Override
            public void onError(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(Exception e) {
                WriteFile.myWrite(Config.outPutXmlFile, texts[0]+"<!--todo 此行翻译错误需要手动翻译-->");

            }
        });
    }
}
