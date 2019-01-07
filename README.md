## Android国际化翻译脚本

 最近在做一个Android项目需要用国际化翻译，但是需要自己对strings文件里的内容翻译，
 看了一下配置文件里，有四百行的翻译需要分别翻译成韩文，日文，英文，以及繁体字，
 想想就头大，但是聪明的我们怎么会那么傻的去做一些重复的事情呢。所以就有了以下脚本。
 
## 使用
   
  项目是用的IntelliJ IDEA 写的，直接拉下代码改变配置文件内的 配置路径。
  以及翻译语言的源语言、目标语言然后运行项目即可翻译。
  
```$java

    //输入的xml文件
        public final static String inPutXmlFile = "/Users/howard/AndroidTest/C60/app/src/main/res/values/strings.xml";
        //输出的xml文件
        public final static String outPutXmlFile = "/Users/howard/AndroidTest/C60/app/src/main/res/values/test1.xml";
        //输入的语言
        public final static String inputLanguage = "zh-CN";
        //输出的语言
        public final static String outPutLanguage = "ja";
```


  