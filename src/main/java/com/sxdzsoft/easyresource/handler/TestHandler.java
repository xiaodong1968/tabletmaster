package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.serviceImple.TestServiceUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestHandler
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/30 14:44
 * @Version 1.0
 **/
@RestController
public class TestHandler {
    @Autowired
    TestServiceUitl TestServiceUitl;
    @GetMapping(path="/testCreateJob")
    public void testCreateJob(int taskId){
        this.TestServiceUitl.createJob(taskId);
    }
}
