package com.xin.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;

/**
 * @author xin
 * @since 2023/1/10 19:56
 */
public class TestVod03 {
    public static void main(String[] args) {
        String accessKeyId = "LTAI5t9z1eumge2T3UhWMQkA";
        String accessKeySecret = "7C0P36JJqNkwyq9bQrHCAlXRCPoFs3";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //�ϴ�֮���ļ�����
        String fileName = "D:/6 - What If I Want to Move Faster.mp4";  //�����ļ�·��������
        //�ϴ���Ƶ�ķ���
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* ��ָ����Ƭ�ϴ�ʱÿ����Ƭ�Ĵ�С��Ĭ��Ϊ2M�ֽ� */
        request.setPartSize(2 * 1024 * 1024L);
        /* ��ָ����Ƭ�ϴ�ʱ�Ĳ����߳�����Ĭ��Ϊ1��(ע�������û�ռ�÷�����CPU��Դ������ݷ��������ָ����*/
        request.setTaskNum(1);
        request.setApiRegionId("cn-shenzhen");

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* ������ûص�URL��Ч����Ӱ����Ƶ�ϴ������Է���VideoIdͬʱ�᷵�ش����롣��������ϴ�ʧ��ʱ��VideoIdΪ�գ���ʱ��Ҫ���ݷ��ش���������������ԭ�� */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
