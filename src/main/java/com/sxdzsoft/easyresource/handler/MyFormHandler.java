package com.sxdzsoft.easyresource.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.MyFormService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName MyFormHandler
 * @Description 自定义表单控制类
 * @Author wujian
 * @Date 2022/5/24 17:07
 * @Version 1.0
 **/
@Controller
public class MyFormHandler {
    @Autowired
    private MyFormService myFormService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private MenuService menuService;
    /**
     * @Description 表单工厂跳转
     * @Author wujian
     * @Date 17:17 2022/5/24
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/formFactory")
    @MenuButton
    public String formFactory(Integer menuId, Model model){

        return "pages/formmanage/formfactory";
    }
    /**
     * @Description 表单工厂提交模板表单
     * @Author wujian
     * @Date 8:57 2022/5/25
     * @Params []
     * @Return
     **/
    @PostMapping(path="/createFormTemplate")
    @ResponseBody
    public int createFormTemplate(@RequestBody JSONObject myForm){
      User owner=(User)this.httpSession.getAttribute("userinfo");
      MyForm form= myForm.toJavaObject(MyForm.class);
      return this.myFormService.createFormTemplate(form.getItmes(),owner,form);

    }
    /**
     * @Description 我的表单
     * @Author wujian
     * @Date 14:30 2022/5/25
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/myForm")
    @MenuButton
    public String myForm(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/formmanage/myForm";
    }
    /**
     * @Description 我的表单分页表格
     * @Author wujian
     * @Date 14:31 2022/5/25
     * @Params [group, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryMyFormForTable")
    @ResponseBody
    public DataTableModel<MyForm> queryMyFormForTable(MyForm myForm, @RequestParam Integer menuId, DataTableModel<MyForm> table) {
        User owner=(User)this.httpSession.getAttribute("userinfo");
        myForm.setOwner(owner);
        DataTableModel<MyForm> result=this.myFormService.queryMyFormForTable(myForm,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 我的表单预览
     * @Author wujian
     * @Date 14:54 2022/5/25
     * @Params [formId, model]
     * @Return
     **/
    @GetMapping(path = "/showMyForm")
    public String showMyForm(int formId,Model model){
       MyForm form=this.myFormService.queryMyFormByFormId(formId,1);
       List<MyFormItem> items=form.getItmes();
        Collections.sort(items);
        form.setItmes(items);
       model.addAttribute("myForm",form) ;
       int rows_n=form.getRows();
       List<Integer> rows=new ArrayList<Integer>();
       for(int i=0;i<rows_n;i++){
           rows.add(i);
       }
        model.addAttribute("rows",rows) ;
       return "pages/formmanage/showMyForm";
    }
    /**
     * @Description 删除我的表单
     * @Author wujian
     * @Date 17:39 2022/5/25
     * @Params [formId, isUse]
     * @Return
     **/
    @PostMapping(path = "/delMyForm")
    @ResponseBody
    public int delMyForm(int formId,int isUse){
        return this.myFormService.delMyForm(formId,isUse);
    }
    /**
     * @Description 修改表格明细的值
     * @Author wujian
     * @Date 10:37 2022/6/9
     * @Params [itemId, value]
     * @Return
     **/
    @PostMapping(path = "/modifyItemValue")
    @ResponseBody
    public int modifyItemValue(int itemId,float value){
        User modify=(User)this.httpSession.getAttribute("userinfo");
        return this.myFormService.modifyItemValue(itemId,value,modify);
    }
    /**
     * @Description 查询指定表单指定明细的上一项或下一项
     * @Author wujian
     * @Date 12:06 2022/6/13
     * @Params [itemId, model]
     * @Return
     **/
    @GetMapping(path = "/queryNextOrPreItem")
    @ResponseBody
    public int queryNextOrPreItem(int itemId,int model){
       MyFormItem current=  this.myFormService.queryMyFormItemById(itemId);
       int row=current.getRow();
       if(model==0){
           row=row-1;
       }
       if(model==1){
           row=row+1;
       }
       MyFormItem result= this.myFormService.queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(1,current.getMyForm().getId(),row);
       if(result==null){
           return HttpResponseRebackCode.Fail;
       }
       else{
           return result.getId();
       }
    }
}
