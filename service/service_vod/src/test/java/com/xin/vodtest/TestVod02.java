package com.xin.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

/**
 * @author xin
 * @since 2023/1/10 18:15
 */
public class TestVod02 {

    /*��ȡ����ƾ֤����*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("754cd0d090cc71edad0e7630a6ac0102");
        return client.getAcsResponse(request);
    }

    /*����Ϊ����ʾ��*/
    public static void main(String[] argv) {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t9z1eumge2T3UhWMQkA", "7C0P36JJqNkwyq9bQrHCAlXRCPoFs3");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //����ƾ֤
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta��Ϣ
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
