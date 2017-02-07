package main.unit;

/**
 * Created by tangtao on 2016/6/15.
 */
public class createSign {
    private static final String signatureKey = "f3166e2a-0b54-11e6-be36-7427ea62a240";
    public static String createSignature(BaseRequest request) {
        String signature = MD5Util.MD5Encode(
                new StringBuilder(request.getSystemId()).append('&').
                        append(request.getOpenId()).append('&').
                        append(request.getPlatformType()).append('&').
                        append(signatureKey).toString(), "UTF-8");
        return signature;
    }
    public static void main(String[] args) {
        BaseRequest request = new BaseRequest();
        request.setOpenId("oFoSyt_kgKJCMLIhZXP48Dd060P8");
        request.setSystemId("weimaidan");
        request.setPlatformType(2);
        request.setSignature(createSign.createSignature(request));
        request.getSignature();
        System.out.println(request.getSignature());//获取签名的方法
    }
}
