package main.unit;

import net.sf.json.JSONObject;

/**
 * Created by tangtao on 2016/5/19.
 *  此处的data为json类型
 */
public class TestJsonResult {
    public int code;
    JSONObject data;
    //JSONArray data;
    public String message;
    public Boolean success;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public JSONObject getData() {
		return data;
	}
}
