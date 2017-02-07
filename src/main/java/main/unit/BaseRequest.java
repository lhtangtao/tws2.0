package main.unit;

/**
 * Created by tangtao on 2016/6/15.
 */
public class BaseRequest {
//    @NotEmpty(message = "systemId不能为空")
    private String systemId;
//    @NotEmpty(message = "openId不能为空")
    private String openId;
//    @NotNull(message = "platformType不能为空")
//    @Range(min = 1, message = "platformType数据错误")
    private int platformType;
//    @NotEmpty(message = "signature不能为空")
    private String signature;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}

