package com.franklin.juhesdk;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.franklin.http.MyHttpMethod;
import com.franklin.tools.MyNumberFormat;

public class JsonParser {
	private String apiKey = "b86e5cf5a183bb5aa589c0b8cf353428";
	public List<String> getLonLat(List<String> bsInfo) {
		List<String> gpsInfo = new ArrayList<String>();
		String mnc = "0";//移动是0，联通是1
		String url = "http://v.juhe.cn/cell/get";
		if(2==bsInfo.size()) {
			String lac = bsInfo.get(0);
			String cellid = bsInfo.get(1);
			String key = apiKey;
			String queryString = new StringBuffer("?").append("mnc=").append(mnc).
					append("&lac=").append(lac).append("&cell=").append(cellid).
					append("&key=").append(key).toString();
			url = new StringBuffer(url).append(queryString).toString();
			String charset = "UTF-8";
			String bodyInfo = new MyHttpMethod().doGetMethod(url);
			try {
				JSONObject object = new JSONObject(bodyInfo);
				String code = object.getString("error_code");
				if(code.equals("0")) { //得到了正确的数据
					JSONObject jsonObject = (JSONObject) object.getJSONObject("result");
					JSONObject data = (JSONObject) jsonObject.getJSONArray("data").get(0);
					double lon = Double.valueOf(data.getString("LNG")); 
					double lat = Double.valueOf(data.getString("LAT"));  
					String lonString = MyNumberFormat.numberPrecise(lon); //为了控制输出的位数 默认是6位小数
					String latString = MyNumberFormat.numberPrecise(lat); //为了控制输出的位数 
 					gpsInfo.add(latString); //先放纬度
					gpsInfo.add(lonString); //后放经度
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return gpsInfo;
	}
}
