package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.BlackList;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.BlackListMapper;
import com.sxdzsoft.easyresource.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 16:29
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: WhiteListServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class BlackListServiceImple implements BlackListService {

    @Autowired
    private BlackListMapper blackListMapper;

    @Override
    public int changeBlackList(BlackList blackList) {
        blackListMapper.deleteAll();
        BlackList save = blackListMapper.save(blackList);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public BlackList queryBlack() {
        BlackList blackList = blackListMapper.queryBlack();
        return blackList;
    }
}
