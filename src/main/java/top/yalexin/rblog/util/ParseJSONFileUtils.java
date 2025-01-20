/**
 * Author: Yalexin
 * Email: me@yalexin.top
 **/
package top.yalexin.rblog.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.TypeReference;
import org.springframework.util.ResourceUtils;
import top.yalexin.rblog.entity.Link;

import java.io.*;
import java.util.List;

public class ParseJSONFileUtils {
    private final static String FILE_NAME = "./link/link.json";

    //读取json文件
    private static String readJsonFile(String fileName) throws IOException {
        fileName = FILE_NAME;
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = buffer.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Link> getJSONObject() throws IOException {
        String jsonFile = readJsonFile(FILE_NAME);
        List<Link> links = JSON.parseObject(jsonFile, new TypeReference<List<Link>>() {
        });
        return links;
    }

    public static void main(String[] args) throws FileNotFoundException {

//        String path = "link/link.json";
//        ParseJSONFileUtils parseJSONFileUtils = new ParseJSONFileUtils();
//        String s = parseJSONFileUtils.readJsonFile(path);
//        JSONObject jobj = JSON.parseObject(s);
//        System.out.println(jobj);
    }
}
