package com.sxdzsoft.easyresource.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;

/**
 * @Description TODO
 * @Author wujian
 * @Date 16:28 2022/4/25
 * @Params
 * @Return
 **/
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    public final static String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) {
        final Path filePath = (Path) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(filePath);
    }
}
