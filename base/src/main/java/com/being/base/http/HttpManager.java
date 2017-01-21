package com.being.base.http;

import com.being.base.utils.FileUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.being.base.http.callback.DownloadCallback;
import com.being.base.http.callback.GsonResponseCallbackImpl;
import com.being.base.http.callback.ResponseCallback;
import com.being.base.http.exception.FileDownloadExecption;
import com.being.base.http.security.NHX509TrustManager;
import com.being.base.log.NHLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Http请求管理类
 * Created by Zhp on 2014/5/19.
 * <pre>
 * Simple Demo:
 * HttpManager.getInstance().post(mTestUrl, requestParams, new ResponseCallbackImpl<TestGsonModel>() {

            &#064;Override
            public void onStart() {
            }

            &#064;Override
            public void onSuccess(TestGsonModel baseData) {

            }

            &#064;Override
            public void onFail(TestGsonModel failData, Throwable error) {

            }
        });
 * </pre>
 */
@SuppressWarnings({"unchecked", "unused", "WeakerAccess"})
public class HttpManager {

    private static HttpManager mInstance;

    private AsyncOkHttp mAsyncOkHttp;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    private HttpManager() {
        mAsyncOkHttp = new AsyncOkHttp();
    }

    public AsyncOkHttp getAsyncOkHttp() {
        return mAsyncOkHttp;
    }

    /**
     * 基础post请求，在回调中返回获取的数据。
     * 所有的回调都运行在UI线程中
     * 使用Gson处理数据
     * @param url     请求的url
     * @param params  请求的参数
     * @param responseCallback   请求的回调
     * @return RequestHandle
     */
    public CallHandler post(final String url, final RequestParams params, final ResponseCallback responseCallback) {
        return mAsyncOkHttp.post(url, params, new GsonResponseCallbackImpl(responseCallback));
    }

    /**
     * 基础get请求，在回调中返回获取的数据。
     * 所有的回调都运行在UI线程中
     * @param url     请求的url
     * @param params  请求的参数
     * @param responseCallback   请求的回调
     * @return CallHandler
     */
    public CallHandler get(final String url, final RequestParams params, final ResponseCallback responseCallback) {
        return mAsyncOkHttp.get(url, params, new GsonResponseCallbackImpl(responseCallback));
    }

    /**
     * 基础download请求，在回调中返回获取的数据。
     * 所有的回调都运行在UI线程中
     *
     * @param url              请求的url
     * @param filePath         需要保存的文件路径
     *                         如果是文件夹，会从url中解析文件名，如果是绝对路径，则按照指定文件名保存
     * @param responseCallback 请求的回调
     * @return CallHandler
     */
    public BaseDownloadTask download(final String url, final String filePath, final DownloadCallback responseCallback) {
        return download(url, filePath, false, responseCallback);
    }

    /**
     * 基础download请求，在回调中返回获取的数据。
     * 所有的回调都运行在download线程中
     *
     * @param url              请求的url
     * @param filePath         需要保存的文件路径
     *                         如果是文件夹，会从url中解析文件名，如果是绝对路径，则按照指定文件名保存
     * @param responseCallback 请求的回调
     * @return CallHandler
     */
    public BaseDownloadTask download(final String url, final String filePath, boolean sync, final DownloadCallback responseCallback) {
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url).setPath(filePath, new File(filePath).isDirectory()).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (responseCallback != null) {
                    responseCallback.onProgress(soFarBytes, totalBytes, totalBytes == soFarBytes);
                }
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (responseCallback != null) {
                    long total = task.getSmallFileTotalBytes();
                    responseCallback.onProgress(total, total, true);
                    responseCallback.onSuccess(new File(filePath));
                }
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if (responseCallback != null) {
                    String messsage = task.getUrl() + "\n" + (e != null ? e.getMessage() : "");
                    responseCallback.onFail(0, new File(task.getPath()), new FileDownloadExecption(messsage, e));
                }
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        });
        baseDownloadTask.setSyncCallback(sync);
        baseDownloadTask.start();
        return baseDownloadTask;
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mAsyncOkHttp.cancelAll();
    }

    public void setCertificates(InputStream... certificates) {
        setCertificates(certificates, null, null);
    }

    /**
     * 设置信任证书
     * @param certificates 服务端证书，如果不需要验证服务端证书，则传空
     * @param bksFile      客户端证书
     * @param password     客户端证书密码
     */
    @SuppressWarnings("TrulyRandom")
    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(keyManagers, new TrustManager[]{new HttpTrustManager(chooseTrustManager(trustManagers))}, new SecureRandom());
            mAsyncOkHttp.setSslSocketFactory(sslContext.getSocketFactory());
//            if (BuildConfig.HOST != 0) {
//                mAsyncOkHttp.setHostnameVerifier(new NHHostNameVerifier());
//            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    private TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0) return new TrustManager[]{new NHX509TrustManager()};
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)

                {
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) return null;

            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    private class HttpTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public HttpTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            localTrustManager.checkClientTrusted(chain, authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
//                NHLog.v("defaultTrustManager check failed, try localTrustManager!!!");
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return defaultTrustManager.getAcceptedIssuers();
        }
    }

}