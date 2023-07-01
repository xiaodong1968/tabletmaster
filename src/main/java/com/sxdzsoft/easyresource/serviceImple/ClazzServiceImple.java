package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.ClazzHonorVo;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.DeviceService;
import com.sxdzsoft.easyresource.util.ToggleCaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/17 14:45
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: ClazzServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class ClazzServiceImple implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private MyFileMapper myFileMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private CampusNewsClazzMapper campusNewsClazzMapper;

    @Autowired
    private CampusNewsMapper campusNewsMapper;


    private static final Pattern PATTERN = Pattern.compile("([一二三四五六七八九])(（(\\d+)）班)");


    @Override
    public Clazz queryClazzById(Integer clazzId) {
        return clazzMapper.getById(clazzId);
    }

    @Override
    public DataTableModel<Clazz> queryClazzForTable(Clazz clazz, DataTableModel<Clazz> table) {
        Page<Clazz> clazzes = this.clazzMapper.findAll(new ClazzSpecification(clazz), PageRequest.of(table.getStart() / table.getLength(), table.getLength(),JpaSort.by("sortNum")));
        DataTableModel<Clazz> result = new DataTableModel();
        result.setData(clazzes.getContent());
        result.setRecordsFiltered(Long.valueOf(clazzes.getTotalElements()).intValue());
        result.setRecordsTotal(clazzes.getNumberOfElements());
        return result;
    }

    @Override
    public Clazz queryById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public int addClazz(Clazz clazz) {
        Clazz singClazz = clazzMapper.queryByClazzNameAndIsUse(clazz.getClazzName(),1);
        //判断当前班级是否存在
        if (singClazz!=null){
            return HttpResponseRebackCode.SameName;
        }
        String clazzName = clazz.getClazzName();
        //使用正则表达式获取具体班级
        Matcher matcher = PATTERN.matcher(clazzName);
        if (matcher.find()) {
            String chineseNumber = matcher.group(1);
            int firstNum = ToggleCaseUtil.convertChineseToArabic(chineseNumber);
            String numberString = matcher.group(3);
            int number = Integer.parseInt(numberString);
            int resNum = Integer.parseInt(firstNum + String.format("%02d", number));
            clazz.setSortNum(resNum);
        }
        Clazz save = clazzMapper.save(clazz);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editClazz(Clazz clazz) {
        Clazz singClazz = clazzMapper.queryByClazzNameAndIsUse(clazz.getClazzName(),1);
        //判断当前班级是否存在
        if (singClazz!=null && clazz.getId().intValue() != singClazz.getId().intValue()){
            return HttpResponseRebackCode.SameName;
        }
        Clazz resClazz = clazzMapper.getById(clazz.getId());
        String clazzName = clazz.getClazzName();
        //使用正则表达式获取具体班级
        Matcher matcher = PATTERN.matcher(clazzName);
        if (matcher.find()) {
            String chineseNumber = matcher.group(1);
            int firstNum = ToggleCaseUtil.convertChineseToArabic(chineseNumber);
            String numberString = matcher.group(3);
            int number = Integer.parseInt(numberString);
            int resNum = Integer.parseInt(firstNum + String.format("%02d", number));
            resClazz.setSortNum(resNum);
        }
        resClazz.setClazzName(clazz.getClazzName());
        resClazz.setSlogan(clazz.getSlogan());
        resClazz.setClazzTeacher(clazz.getClazzTeacher());
        resClazz.setTel(clazz.getTel());
        resClazz.setJobTitle(clazz.getJobTitle());
        resClazz.setSubject(clazz.getSubject());
        resClazz.setIsUse(1);
        Clazz save = clazzMapper.save(resClazz);
        if (save!=null){
            List<Device> devices = deviceService.queryDeviceByClazzId(save.getId());
            for (Device device : devices) {
                device.setName(save.getClazzName());
                deviceMapper.save(device);
            }
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int clazzHonorUpdate(ClazzHonorVo clazzHonorVo) {
        Clazz clazz = clazzMapper.getById(clazzHonorVo.getClazzId());
        clazz.setImage1(clazzHonorVo.getPhotoIds().get(0));
        clazz.setImage2(clazzHonorVo.getPhotoIds().get(1));
        clazz.setImage3(clazzHonorVo.getPhotoIds().get(2));
        clazz.setImage4(clazzHonorVo.getPhotoIds().get(3));
        clazz.setImage5(clazzHonorVo.getPhotoIds().get(4));
        clazz.setImage6(clazzHonorVo.getPhotoIds().get(5));
        Clazz save = clazzMapper.save(clazz);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int clazzHonorDelete(ClazzHonorVo clazzHonorVo) {
        List<Integer> photoIds = clazzHonorVo.getPhotoIds();
        Clazz clazz = clazzMapper.getById(clazzHonorVo.getClazzId());
        Integer image1 = clazz.getImage1();
        Integer image2 = clazz.getImage2();
        Integer image3 = clazz.getImage3();
        Integer image4 = clazz.getImage4();
        Integer image5 = clazz.getImage5();
        Integer image6 = clazz.getImage6();
        for (Integer photoId : photoIds) {
            if (photoId.equals(image1) || photoId.equals(image2) || photoId.equals(image3) || photoId.equals(image4) || photoId.equals(image5) || photoId.equals(image6)){
                return HttpResponseRebackCode.Fail;
            }
            MyFile myFile = myFileMapper.getById(photoId);
            myFile.setIsUse(0);
            myFileMapper.save(myFile);
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public int clazzMienDelete(ClazzHonorVo clazzHonorVo) {
        List<Integer> photoIds = clazzHonorVo.getPhotoIds();
        Clazz clazz = clazzMapper.getById(clazzHonorVo.getClazzId());
        Integer mien1 = clazz.getMien1();
        Integer mien2 = clazz.getMien2();
        Integer mien3 = clazz.getMien3();
        Integer mien4 = clazz.getMien4();
        for (Integer photoId : photoIds) {
            if (photoId.equals(mien1) || photoId.equals(mien2) || photoId.equals(mien3) || photoId.equals(mien4)){
                return HttpResponseRebackCode.Fail;
            }
            MyFile myFile = myFileMapper.getById(photoId);
            myFile.setIsUse(0);
            myFileMapper.save(myFile);
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public int clazzMienUpdate(ClazzHonorVo clazzHonorVo) {
        Clazz clazz = clazzMapper.getById(clazzHonorVo.getClazzId());
        clazz.setMien1(clazzHonorVo.getPhotoIds().get(0));
        clazz.setMien2(clazzHonorVo.getPhotoIds().get(1));
        clazz.setMien3(clazzHonorVo.getPhotoIds().get(2));
        clazz.setMien4(clazzHonorVo.getPhotoIds().get(3));
        Clazz save = clazzMapper.save(clazz);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public List<Clazz> queryAllClazzAndStar() {
        List<Clazz> clazzes = clazzMapper.queryAllByIsUse(1);
        clazzes = clazzes.stream().sorted(Comparator.comparing(Clazz::getSortNum)).collect(Collectors.toList());
        return clazzes;
    }

    @Override
    public DataTableModel<CampusNews> queryClazzNews(CampusNews campusNews,Integer clazzId,DataTableModel<CampusNews> table) {
        Page<CampusNews> all = this.campusNewsMapper.findAll(new CampusNewSpecification(campusNews), PageRequest.of(table.getStart() / table.getLength(), table.getLength(), JpaSort.by("id").descending()));
        List<CampusNewsClazz> campusNewsClazzes = campusNewsClazzMapper.queryByClazzId(clazzId);
        List<CampusNews> campusNewsList = new ArrayList<>();
        List<CampusNews> content = all.getContent();
        for (CampusNewsClazz campusNewsClazz : campusNewsClazzes) {
            for (CampusNews news : content) {
                if (campusNewsClazz.getCampNewsId().equals(news.getId())){
                    news.setFixe(1);
                }
            }
        }
        DataTableModel<CampusNews> result = new DataTableModel();
        result.setData(content);
        result.setRecordsFiltered(Long.valueOf(all.getTotalElements()).intValue());
        result.setRecordsTotal(all.getNumberOfElements());
        return result;
    }
}
