package com.sxdzsoft.easyresource.form;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/12 16:44
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: WebsocketVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class WebsocketVo<T> {
    private String type;
    private T data;



    public static WebsocketVo sendType(String src){
        WebsocketVo websocketVo = new WebsocketVo();
        websocketVo.setType(src);
        return websocketVo;
    }

    public WebsocketVo sendAll(String type, T data) {
        WebsocketVo<Object> websocketVo = new WebsocketVo<>();
        websocketVo.type = type;
        websocketVo.data = data;
        return websocketVo;
    }
}
