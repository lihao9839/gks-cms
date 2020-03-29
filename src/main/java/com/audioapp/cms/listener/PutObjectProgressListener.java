package com.audioapp.cms.listener;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;


public class PutObjectProgressListener implements ProgressListener {

    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;
    private HttpSession session;
    private int percent = 0;

    //构造方法中加入session
    public PutObjectProgressListener() {
    }

    public PutObjectProgressListener(HttpSession mSession) {
        this.session = mSession;
        session.setAttribute("upload_percent", percent);
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                    //将进度percent放入session中
                    session.setAttribute("upload_percent", percent);
                    //System.err.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                } else {
                    //System.err.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                break;
            case TRANSFER_FAILED_EVENT:
                break;
            default:
                break;
        }
    }

    public boolean isSucceed() {
        return succeed;
    }

}
