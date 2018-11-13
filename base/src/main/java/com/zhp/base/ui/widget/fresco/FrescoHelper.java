//package com.zhp.base.ui.widget.fresco;
//
//import android.content.Context;
//import android.net.Uri;
//
//import com.facebook.binaryresource.BinaryResource;
//import com.facebook.binaryresource.FileBinaryResource;
//import com.facebook.cache.common.CacheKey;
//import com.facebook.cache.disk.DiskCacheConfig;
//import com.facebook.common.internal.Supplier;
//import com.facebook.common.logging.FLog;
//import com.facebook.datasource.DataSource;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
//import com.facebook.imagepipeline.cache.MemoryCacheParams;
//import com.facebook.imagepipeline.core.ImagePipeline;
//import com.facebook.imagepipeline.core.ImagePipelineConfig;
//import com.facebook.imagepipeline.core.ImagePipelineFactory;
//import com.facebook.imagepipeline.listener.RequestListener;
//import com.facebook.imagepipeline.listener.RequestLoggingListener;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;
//import com.zhp.base.BuildConfig;
//import com.zhp.base.utils.StorageUtils;
//
//import java.io.File;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by zhangpeng on 16/1/12.
// */
//public class FrescoHelper {
//
//    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";
//
//    public enum TransformType{
//        NONE,
//        CIRCLE,
//        ROUNDCORNER
//    }
//
//    public enum ProgressBarType{
//        NONE,
//        CIRCLE,
//    }
//
//    public static void init(Context context) {
//        ImagePipelineConfig imagePipelineConfig = getImagePipelineConfig(context);
//        Fresco.initialize(context, imagePipelineConfig);
//    }
//
//    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
//        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
//        configureCaches(configBuilder, context);
//        configureLoggingListeners(configBuilder);
//        configureOptions(configBuilder);
//        return configBuilder.build();
//    }
//
//    public static DataSource<Void> prefetchToDiskCache(String url) {
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .build();
//        return imagePipeline.prefetchToDiskCache(request, null);
//    }
//
//    public static File getImageFileFromCache(String uriString) {
//        ImageRequest imageRequest=ImageRequest.fromUri(uriString);
//        CacheKey cacheKey= DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, null);
//        BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
//        File file= null;
//        if (resource != null) {
//            file = ((FileBinaryResource)resource).getFile();
//        }
//        return file;
//    }
//
//    public static void clearMemoryCaches() {
//        Fresco.getImagePipeline().clearMemoryCaches();
//    }
//
//    public static void clearDiskCaches() {
//        Fresco.getImagePipeline().clearDiskCaches();
//    }
//
//    public static void clearCache() {
//        Fresco.getImagePipeline().clearCaches();
//    }
//
//    /**
//     * Configures disk and memory cache not to exceed common limits
//     */
//    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
//        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
//                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
//                Integer.MAX_VALUE,                     // Max entries in the cache
//                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
//                Integer.MAX_VALUE,                     // Max length of eviction queue
//                Integer.MAX_VALUE);                    // Max cache entry size
//        configBuilder.setBitmapMemoryCacheParamsSupplier(
//                new Supplier<MemoryCacheParams>() {
//                    public MemoryCacheParams get() {
//                        return bitmapCacheParams;
//                    }
//                })
//                .setMainDiskCacheConfig(
//                        DiskCacheConfig.newBuilder(context)
//                                .setBaseDirectoryPath(StorageUtils.getCacheDirectory(context))
//                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
//                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)
//                                .build());
//    }
//
//    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
//        Set<RequestListener> requestListeners = new HashSet<>();
//        requestListeners.add(new RequestLoggingListener());
//        configBuilder.setRequestListeners(requestListeners);
//        if (BuildConfig.DEBUG) {
//            FLog.setMinimumLoggingLevel(FLog.getMinimumLoggingLevel());
//        }
//    }
//
//    private static void configureOptions(ImagePipelineConfig.Builder configBuilder) {
//        configBuilder.setDownsampleEnabled(true);
//    }
//}
