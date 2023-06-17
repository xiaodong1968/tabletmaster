package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.mapper.WhiteListMapper;
import com.sxdzsoft.easyresource.service.WhiteListService;
import net.bytebuddy.implementation.bytecode.ShiftLeft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 16:29
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: WhiteListServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class WhiteListServiceImple implements WhiteListService {

    @Autowired
    private WhiteListMapper whiteListMapper;

    @Override
    public int changewhiteList(WhiteList whiteList) {
        whiteListMapper.deleteAll();
        WhiteList save = whiteListMapper.save(whiteList);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public WhiteList queryWhite() {
        WhiteList whiteList = whiteListMapper.queryWhite();
        return whiteList;
    }
}
