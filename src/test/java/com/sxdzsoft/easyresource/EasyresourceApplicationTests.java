package com.sxdzsoft.easyresource;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.UserService;
import com.sxdzsoft.easyresource.temp.TempUser;
import com.sxdzsoft.easyresource.temp.TempUserMapper;
import com.sxdzsoft.easyresource.temp.TempUserService;
import com.sxdzsoft.easyresource.util.MD5Utils;
import com.sxdzsoft.easyresource.util.NameUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class EasyresourceApplicationTests {
    @Autowired
    private TempUserService tempUserService;
    /*
     * @Description TODO
     * @Author wujian
     * @Date 15:03 2022/4/20
     * @Params []
     * @Return
     **/
//    @Test
//    void contextLoads() {
//    }
    /**
     * @Description 批量导入用户
     * @Author wujian
     * @Date 13:34 2022/6/9
     * @Params []
     * @Return
     **/
    @Test
    public void loadUser() {
    }
}