package com.luokes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 解析xml中的内容
     * @param text 每一行xml
     * @return 标签中的内容 没有的话原样返回
     */
    public static String[] getXmlContent(String text) {

        String regEx = "\\s*<string.*>(.*)</string>\\s*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return new String[]{text,matcher.group(1)};
        } else {
            return new String[]{text,getXmlExegesis(text)};
        }
    }


    /**
     *
     * @param text 注释行文本
     * @return 注释内容 没有的话原样返回
     */
    private static String getXmlExegesis(String text) {
        String regEx = "\\s*<!--(.*)-->\\s*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
