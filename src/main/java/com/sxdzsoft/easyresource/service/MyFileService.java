package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.MyFile;
import com.sxdzsoft.easyresource.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName MyFileService
 * @Description 我的文件服务接口
 * @Author wujian
 * @Date 2022/5/31 15:29
 * @Version 1.0
 **/
public interface MyFileService {
    /**
     * @Description 新增表单文件
     * @Author wujian
     * @Date 14:42 2022/6/1
     * @Params [fileType文件类型,fileSize文件大小,itemId归属目录明细ID,preReadStore预览文件名 ,store存储路径, orgname文件原始名, owner文件拥有者]
     * @Return
     **/
    public MyFile addFormFile(long fileSize,String store, String name,int isUse );
    /**
     * @Description 根据文件ID查询文件
     * @Author wujian
     * @Date 15:31 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    public MyFile queryFileById(int fileId);

    /**
     * @Description 删除表单文件
     * @Author wujian
     * @Date 17:36 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    public int delFormFile(int fileId);

    /**
     * @Description: 图片上传
     * @data:[multipartFile]
     * @return: com.sxdzsoft.easyresource.domain.MyFile
     * @Author: YangXiaoDong
     * @Date: 2023/2/22 16:15
     */
    public MyFile uploadImage(MultipartFile multipartFile);


    /**
     * @Description: 根据班级id查询班级风采
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 11:09
     */
    public DataTableModel<MyFile> queryByClazzId(Integer clazzId, Integer page, Integer pageSize);

    /**
     * @Description: 查询班级风采
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/7/14 17:40
     */
    public List<MyFile> queryByClazzId(Integer clazzId);

    /**
     * @Description: 更改照片状态以及从属班级
     * @data:[clazzId, fileId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 16:38
     */
    public int updateClazzAndStatu(Integer clazzId, Integer fileId);


    /**
     * @Description: 改变文件照片(班级风采)的状态
     * @data:[clazzId, fileId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 9:31
     */
    public int  updateClazzMienAndStatu(Integer clazzId,Integer fileId);

}
