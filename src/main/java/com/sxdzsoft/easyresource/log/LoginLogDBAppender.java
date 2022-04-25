package com.sxdzsoft.easyresource.log;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 
 * Author:WuJian
 * Description:自定义LogBack数据库操作
 * MyDBAppender.java
 * LastModify:2020年3月7日
 */
public class LoginLogDBAppender extends DBAppenderBase<ILoggingEvent> {
	    protected String insertSQL;
	    protected static final Method GET_GENERATED_KEYS_METHOD;
	    static final int TIMESTMP_INDEX = 1;
	    static final int FORMATTED_MESSAGE_INDEX = 2;
	    static final int LEVEL_STRING_INDEX = 3;
	    static final int LOGGER_NAME_INDEX = 4;	 
	    static final int THREAD_NAME_INDEX = 5;
	    static final int CALLER_FILENAME_INDEX = 6;
	    static final int CALLER_CLASS_INDEX = 7;
	    static final int CALLER_METHOD_INDEX = 8;
	    static final int CALLER_LINE_INDEX = 9;

	    static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

	    static {
	        // PreparedStatement.getGeneratedKeys() method was added in JDK 1.4
	        Method getGeneratedKeysMethod;
	        try {
	            // the
	            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
	        } catch (Exception ex) {
	            getGeneratedKeysMethod = null;
	        }
	        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
	    }
	    
	    @Override
	    public void start() {
	        insertSQL = buildInsertSQL();
	        super.start();
	    }
	 
	    private static String buildInsertSQL() {
	        return "INSERT INTO t_loginlog_db " +
	                "(time,message,level_string,logger_name,thread_name,"
	                + "caller_filename,caller_class,caller_method,caller_line)"
	                + " VALUES ( ? ,?, ?, ?, ?, ?, ?,?,?)";
	    }


	    void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
	        stmt.setTimestamp(TIMESTMP_INDEX, new Timestamp(event.getTimeStamp()));
	        stmt.setString(FORMATTED_MESSAGE_INDEX, event.getFormattedMessage());
	        stmt.setString(LOGGER_NAME_INDEX, event.getLoggerName());
	        stmt.setString(LEVEL_STRING_INDEX, event.getLevel().toString());
	        stmt.setString(THREAD_NAME_INDEX, event.getThreadName());
	    }

	    void bindCallerDataWithPreparedStatement(PreparedStatement stmt, StackTraceElement[] callerDataArray) throws SQLException {

	        StackTraceElement caller = extractFirstCaller(callerDataArray);

	        stmt.setString(CALLER_FILENAME_INDEX, caller.getFileName());
	        stmt.setString(CALLER_CLASS_INDEX, caller.getClassName());
	        stmt.setString(CALLER_METHOD_INDEX, caller.getMethodName());
	        stmt.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
	    }
	    @Override
	    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement) throws Throwable {

	        bindLoggingEventWithInsertStatement(insertStatement, event);
	        // This is expensive... should we do it every time?
	        bindCallerDataWithPreparedStatement(insertStatement, event.getCallerData());

	        int updateCount = insertStatement.executeUpdate();
	        if (updateCount != 1) {
	            addWarn("Failed to insert loggingEvent");
	        }
	    }

	    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {

	    }
	    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
	        StackTraceElement caller = EMPTY_CALLER_DATA;
	        if (hasAtLeastOneNonNullElement(callerDataArray))
	            caller = callerDataArray[0];
	        return caller;
	    }

	    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
	        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
	    }

	

	    @Override
	    protected Method getGeneratedKeysMethod() {
	        return GET_GENERATED_KEYS_METHOD;
	    }

	    @Override
	    protected String getInsertSQL() {
	        return insertSQL;
	    }







}
