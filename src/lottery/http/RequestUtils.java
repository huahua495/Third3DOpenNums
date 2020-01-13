package lottery.http;

import lottery.shishi.Sign;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    public static void main(String[] args) {
//        LotteryContent(Sign.CQ_SSC_ID,"20191102-011");
        String result = httpGet("https://tools.17500.cn/tb/tjssc/chgs?limit=200");
        System.out.println(result);
    }


    public static void LotteryContent(String requestId, String delineNums) {
        String requestUrl = Sign.BASE_REQUEST_URL;
        requestUrl = requestUrl.concat("?").concat("appkey=").concat(Sign.APPKEY)
                .concat("&issueno=").concat(delineNums)
                .concat("&num=").concat("20")
                .concat("&cpid=").concat(requestId);

        System.out.println(requestUrl);
        httpGet(requestUrl);
    }


    /**
     * HttpURLConnection  GET请求
     *
     * @param methodUrl
     */
    private static String httpGet(String methodUrl) {
        String resultData = "";

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodUrl);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    if (line.contains("_.unescape")) {
                        line = line.replace("&quot;", String.valueOf('"'));

                        int index = line.indexOf(String.valueOf("{"));
                        int lasLastIndex = line.lastIndexOf("}");

                        line = line.substring(index, lasLastIndex);
//                        System.out.println(line);
                        resultData = line;
                    }
//                    result.append(line).append(System.getProperty("line.separator"));// "\n"


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return resultData;
    }


    /**
     * HttpURLConnection    post请求
     *
     * @param requestPath
     * @param params
     */
    private static void httpPost(String requestPath, Map<String, Object> params) {
        HttpURLConnection connection = null;
        OutputStream dataout = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(requestPath);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
            connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
            connection.setRequestMethod("POST");// 设置请求方式为post,默认GET请求
            connection.setUseCaches(false);// post请求不能使用缓存设为false
            connection.setConnectTimeout(3000);// 连接主机的超时时间
            connection.setReadTimeout(3000);// 从主机读取数据的超时时间
            connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
            connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("Authorization", "Bearer 66cb225f1c3ff0ddfdae31rae2b57488aadfb8b5e7");
            connection.connect();// 建立TCP连接,getOutputStream会隐含的进行connect,所以此处可以不要


            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
            dataout.write(stringBuffer.toString().getBytes());


            dataout.flush();
            dataout.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));//
                }
                connection.disconnect();  //关闭http连接
                System.out.println(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
    }
}
