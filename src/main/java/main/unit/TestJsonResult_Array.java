package main.unit;

import com.google.gson.JsonArray;

/**
 * Created by tangtao on 2016/6/1.
 */
public class TestJsonResult_Array {
    public int code;
    JsonArray data;
    public String message;
    public Boolean success;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
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
	public JsonArray getData() {
		return data;
	}
	public void setData(JsonArray data) {
		this.data = data;
	}
}
