package hope.base.http.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 暂时对于服务端的所有证书不验证
 * Created by zhangpeng on 16/1/19.
 */
public class NHX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        NHLog.v("checkClientTrusted authType: %s, chain: ", authType, chain);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        NHLog.v("checkServerTrusted authType: %s, chain: ", authType,
//                (chain != null && chain.length > 0) ? chain[0].toString() : "");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
