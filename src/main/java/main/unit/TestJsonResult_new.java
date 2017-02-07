package main.unit;

/**
 * Created by tangtao on 2016/5/27.
 * 此处的date类型为string
 */
public class TestJsonResult_new {
        public int code;
        String data;
        public String message;
        public Boolean success;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public void setData(String data) {
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
        public String getData() {
            return data;
        }
}


