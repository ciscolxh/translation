package com.luokes.google;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Google {

    /**
     * 通过谷歌翻译的js算出要翻译内容的token
     * @param value 要翻译的文字
     * @return token
     */
    public static String token(String value) {
        String result = "";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        try {
//            FileReader reader = new FileReader("google.js");
            //todo 暂时没有找到相对路径的方法 需要自己替换 ，如果有人找到方法可以告知我这个知识点 谢谢！
            FileReader reader = new FileReader("/Users/howard/IdeaProjects/translation/src/main/java/com/luokes/google/google.js");
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;
                result = String.valueOf(invoke.invokeFunction("token", value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
