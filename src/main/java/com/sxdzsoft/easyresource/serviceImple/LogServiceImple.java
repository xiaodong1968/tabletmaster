package com.sxdzsoft.easyresource.serviceImple;


import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.MethodResultCode;
import com.sxdzsoft.easyresource.echarts.EchartsForLineSmooth;
import com.sxdzsoft.easyresource.echarts.EchartsPieBorderRadius;
import com.sxdzsoft.easyresource.echarts.EchartsPieBorderRadiusInterface;
import com.sxdzsoft.easyresource.log.LoginLog;
import com.sxdzsoft.easyresource.log.OperationLog;
import com.sxdzsoft.easyresource.mapper.LoginLogMapper;
import com.sxdzsoft.easyresource.mapper.LoginLogSpecification;
import com.sxdzsoft.easyresource.mapper.OperationLogMapper;
import com.sxdzsoft.easyresource.mapper.OperationLogSpecification;
import com.sxdzsoft.easyresource.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName LogServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2023/5/31 19:56
 * @Version 1.0
 **/
@Service
public class LogServiceImple implements LogService {
    private static final Logger log = LoggerFactory.getLogger("operationLog");
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Override
    public long queryLogAmount(int logType) {
        //如果是查询登录日志
        if(logType==0){
            return this.loginLogMapper.countBy();
        }
        //如果是查询操作日志
        if(logType==1){
            return this.operationLogMapper.countBy();
        }
        return 0;
    }

    @Override
    public EchartsForLineSmooth queryLogAmountForYear(int logType) {
        try{
            EchartsForLineSmooth result=new EchartsForLineSmooth();
            Calendar calendar=Calendar.getInstance();
            int currentYear=calendar.get(Calendar.YEAR);
//            int currentYear=Calendar.YEAR;
            String[] titleData={"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
            int[] data=new int[12];
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i=1;i<13;i++){
                    String month="";
                    if(i<10){
                        month="0"+i;
                    }else{
                        month=String.valueOf(i);
                    }
                    String startTime=currentYear+"-"+month+"-01 00:00:00";
                    String endTime=currentYear+"-"+month+"-31 23:59:59";
                    //如果是查询登录日志
                    if(logType==0){
                        data[i-1]=(int)this.loginLogMapper.countByTimeBetween(format.parse(startTime),format.parse(endTime));
                    }
                    //如果是登录日志
                    else{
                        data[i-1]=(int)this.operationLogMapper.countByTimeBetween(format.parse(startTime),format.parse(endTime));
                        System.out.println("开始时间："+startTime+"，结束时间："+endTime);
                        System.out.println(data[i-1]);
                    }
            }
            result.setData(data);
            result.setTitleData(titleData);
            return result;
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public List<EchartsPieBorderRadius> queryLogAmountByLogLevel(int logType) {
        List<EchartsPieBorderRadius> backs=new ArrayList<EchartsPieBorderRadius>();
        List<EchartsPieBorderRadiusInterface> results;
        if(logType==0){
            results=this.loginLogMapper.countAmountByLogLevel();
        }else{
            results=this.operationLogMapper.countAmountByLogLevel();
        }
        for(EchartsPieBorderRadiusInterface o:results){
            EchartsPieBorderRadius temp=new EchartsPieBorderRadius();
            temp.setValue(o.getValue());
            temp.setName(o.getName());
            backs.add(temp);
        }
        return backs;
    }
    @Override
    public DataTableModel<OperationLog> queryOperationLogsForTable(OperationLog operationLog, DataTableModel<OperationLog> table) {
        Page<OperationLog> forms=null;
        forms=this.operationLogMapper.findAll(new OperationLogSpecification(operationLog), PageRequest.of(table.getStart()/table.getLength(), table.getLength(), JpaSort.by("time").descending()));
        DataTableModel<OperationLog> result=new DataTableModel<OperationLog>();
        result.setData(forms.getContent());
        result.setRecordsFiltered(Long.valueOf(forms.getTotalElements()).intValue());
        result.setRecordsTotal(forms.getNumberOfElements());
        return result;
    }
    @Override
    public DataTableModel<LoginLog> queryLoginLogsForTable(LoginLog loginLog, DataTableModel<LoginLog> table) {
        Page<LoginLog> forms=null;
        forms=this.loginLogMapper.findAll(new LoginLogSpecification(loginLog), PageRequest.of(table.getStart()/table.getLength(), table.getLength(),JpaSort.by("time").descending()));
        DataTableModel<LoginLog> result=new DataTableModel<LoginLog>();
        result.setData(forms.getContent());
        result.setRecordsFiltered(Long.valueOf(forms.getTotalElements()).intValue());
        result.setRecordsTotal(forms.getNumberOfElements());
        return result;
    }
    @Override
    public OperationLog queryOperationLogById(int id) {
        return this.operationLogMapper.getById(id);
    }

    @Override
    public LoginLog queryLoginLogById(int id) {
        return this.loginLogMapper.getById(id);
    }
    @Override
    @Transactional
    public int clearLog(int type,int logId) {
        String operation=SecurityContextHolder.getContext().getAuthentication().getName();
        if(type==0){
            this.operationLogMapper.deleteById(logId);
            log.info(operation+"清除了操作日志,ID："+logId);
        }
        if(type==1){
            this.loginLogMapper.deleteById(logId);
            log.info(operation+"清除了登录日志,ID："+logId);
        }
        return MethodResultCode.SUCCESS;
    }
    @Override
    @Transactional
    public int clearAllLog(int logType) {
        String operation=SecurityContextHolder.getContext().getAuthentication().getName();
        //如果是清除操作日志
        if(logType==0){
            List<OperationLog> logs=this.operationLogMapper.findAll();
            for(OperationLog log:logs){
                this.operationLogMapper.delete(log);
            }
            log.info(operation+"清除了所有的操作日志");
        }
        //如果是清除登录日志
        if(logType==1){
            List<LoginLog> logs=this.loginLogMapper.findAll();
            for(LoginLog log:logs){
                this.loginLogMapper.delete(log);
            }
            log.info(operation+"清除了所有的登录日志");
        }
        return MethodResultCode.SUCCESS;
    }

}
