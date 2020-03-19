//package hope.base.http.retrofit;
//
//import hope.base.log.ZLog;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//
///**
// * Created by zhangpeng on 17/2/20.
// */
//@SuppressWarnings("unused")
//public class RetrofitManager {
//
//    private static RetrofitManager mInstance = new RetrofitManager();
//    private static boolean Debug = false;
//
//    public static RetrofitManager get() {
//        return mInstance;
//    }
//
//    private Retrofit mRetrofit;
//
//    private OkHttpClient mOkHttpClient;
//
//    private RetrofitManager() {
//        Retrofit.Builder builder = new Retrofit.Builder();
//    }
//
//    /**
//     * 初始化方法必须在各种设置完成后最后调用
//     * @param builder
//     * @param client
//     */
//    public void initRetrofit(Retrofit.Builder builder, OkHttpClient client) {
//        mRetrofit = builder.client(client).build();
//    }
//
//    public <T> T create(final Class<T> service) {
//        if (Debug) {
//            long start = System.nanoTime();
//            T t =  mRetrofit.create(service);
//            ZLog.d("create %s cost: %sms", service.getSimpleName(),
//                    String.valueOf((System.nanoTime() - start)/ 1000000.0));
//            return t;
//        } else {
//            return mRetrofit.create(service);
//        }
//    }
//
//    public final void create(final Class ...services) {
//        for (Class service : services) {
//            if (Debug) {
//                long start = System.nanoTime();
//                mRetrofit.create(service);
//                ZLog.d("create %s cost: %sms", service.getSimpleName(),
//                        String.valueOf((System.nanoTime() - start)/ 1000000.0));
//            } else {
//                mRetrofit.create(service);
//            }
//        }
//    }
//
//    public OkHttpClient getOkHttpClient() {
//        return mOkHttpClient;
//    }
//
//    public Retrofit getRetrofit() {
//        return mRetrofit;
//    }
//
//    public static void enableDebug() {
//        Debug = true;
//    }
//
//}