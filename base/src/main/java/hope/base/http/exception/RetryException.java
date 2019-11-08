//package hope.base.http.exception;
//
///**
// * 需要重试的异常
// * Created by zhangpeng on 16/3/9.
// */
//public class RetryException extends Exception {
//
//    private RetryReason mRetryReason = RetryReason.NOREASON;
//
//    public RetryException(RetryReason retryReason) {
//        mRetryReason = retryReason;
//    }
//
//    public RetryException(String detailMessage, RetryReason retryReason) {
//        super(detailMessage);
//        mRetryReason = retryReason;
//    }
//}