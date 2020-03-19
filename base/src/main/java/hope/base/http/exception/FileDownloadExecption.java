package hope.base.http.exception;

/**
 * 下载文件失败的异常
 * Created by zhangpeng on 16/5/30.
 */
public class FileDownloadExecption extends RuntimeException {

    public FileDownloadExecption(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
