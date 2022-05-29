package infra.network;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class KakaoLocal { //임시로 그냥 여기 놔둔거임
    public static void main(String[] args) {
        create();
    }

    private static final String KAKAO_LOCAL_API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";
    private static final String KAKAO_LOCAL_API_KEY = "KakaoAK 3811b8892ca2c9ba48e96ad065c6e804";

    public static void create(){
        try {

            String address = URLEncoder.encode("대구광역시 중구 동성로2가 동성로2길 81", "UTF-8");
            URL local_url = new URL(KAKAO_LOCAL_API_URL + address);

            HttpURLConnection con = (HttpURLConnection) local_url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", KAKAO_LOCAL_API_KEY);
            con.setRequestProperty("Content-Type", "application/json");

            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));


            String inputLine = "";
            String response = "";

            while ((inputLine = in.readLine()) != null) {
                response = response.concat(inputLine);
            }


            System.out.println("response입니둥" + response.toString());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response);

            JSONArray jsonDocuments = (JSONArray) jsonObj.get("documents");
            int documentsSize = jsonDocuments.size();

            JSONObject itemObj;
            JSONObject addressObj;
            String addressName = "";
            String lon = "";
            String lat = "";
            if(documentsSize >= 1){
                for(int i=0; i<documentsSize; i++){
                    itemObj = (JSONObject) jsonDocuments.get(i);
                    addressObj = (JSONObject) itemObj.get("address");
                    addressName = addressObj.get("address_name").toString();
                    lon = addressObj.get("x").toString();
                    lat = addressObj.get("y").toString();
                    System.out.println("주소명: " + addressName + " x좌표: " + lon + " y좌표: " + lat);
                }

            }else{
                System.out.println("잘못된 주소를 입력하셨습니둥!");
            }



        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("주소 인코딩 실패");
        } catch (MalformedURLException e){
            throw new RuntimeException("url생성 실패");
        } catch (IOException e) {
            throw new RuntimeException("http url connection 실패");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
