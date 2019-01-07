package com.luokes.io;

import com.luokes.Translation;
import com.luokes.config.Config;
import com.luokes.utils.RegexUtils;

import java.io.*;

public class ReadFile {

    /**
     * 读取文件
     *
     * @param string 文件夹位置
     */
    public static void myRead(String string) {
        File file = new File(string);    //1、建立连接
        BufferedReader reader = null;
        String temp;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                String[] text = RegexUtils.getXmlContent(temp);
                if (text[1] != null) {
                    Translation.translation(text);
                    //进行短暂的休眠  防止加载太快谷歌封IP。
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    WriteFile.myWrite(Config.outPutXmlFile,text[0]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取文件失败！");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
