package hope.base.http.callback;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import hope.base.log.NHLog;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by Zhp on 2015/10/8.
 */
public class RangeFileResponseCallbackImpl extends FileResponseCallbackImpl {

    private long current = 0;
    private boolean append = true;
    private long mTotalLength;

    public RangeFileResponseCallbackImpl(File file) {
        super(file, false);
    }

    public void setTotalLength(long totalLength) {
        this.mTotalLength = totalLength;
    }

    /**
     * 请求成功
     *
     * @param baseData 已解析的数据
     */
    @Override
    public void onFileSuccess(File baseData) {

    }

    /**
     * 请求失败
     *
     * @param statusCode 0为基类已提示，具体逻辑中不用再提示。
     * @param failDate
     * @param error      具体的错误
     */
    @Override
    public boolean onFail(int statusCode, File failDate, Throwable error) {
        return false;
    }

    public Request updateRequestHeaders(Request request) {
        if (frontendFile.exists() && frontendFile.canWrite())
            current = frontendFile.length();
        if (current > 0) {
            append = true;
            return request.newBuilder().addHeader("Range", "bytes=" + current + "-").build();
        }
        return request;
    }

    /**
     * 解析返回数据
     *
     * @param response 返回数据
     * @return
     */
    @Override
    public File parseResponse(Response response) throws IOException {
        if (response == null) return null;
        if (response.isSuccessful()) {
            BufferedSink sink = Okio.buffer(Okio.sink(new FileOutputStream(frontendFile, append)));
            sink.writeAll(response.body().source());
            sink.close();
            return frontendFile;
        }
        if (response.code() == 416) {
            long totalLength = getTotalLength(response);
            if (current == totalLength) {
                NHLog.d("current %s == totalLength, so return success on frontendFile", current);
                return frontendFile;
            } else {
                boolean result = frontendFile.delete();
                NHLog.d("code: %s" +
                                "/\nrange %d is bigger than contentLength %d, " +
                                "so delete file result: %s",
                        response.code(), current, totalLength, result);
                return null;
            }
        }
        return null;
    }

    private long getTotalLength(Response response) throws IOException {
        if (response.code() != 206 && response.code() != 416) {//断点续传时，需要计算真实文件总长度，从Content-Range中获取
            return response.body().contentLength();
        } else if (response.code() == 206) {
            String contentRange = response.headers().get("Content-Range");
            if (!TextUtils.isEmpty(contentRange)) {
                int index = contentRange.indexOf("/");
                return Long.parseLong(contentRange.substring(index + 1));
            }
            return -1;
        }
        return mTotalLength;
    }
}
