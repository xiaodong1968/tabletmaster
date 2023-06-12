package com.sxdzsoft.easyresource.form;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/21 10:33
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: ResultVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class ResultVo<T>  {
    private Integer code;
    private String msg;
    private String src;
    private T data;

    public static ResultVo fail(String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg(msg);
        return resultVo;
    }

    public static ResultVo success(String src){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setSrc(src);
        return resultVo;
    }

    public ResultVo(Integer code, String msg, String src, T data) {
        this.code = code;
        this.msg = msg;
        this.src = src;
        this.data = data;
    }
}
