package kr.co.common.interceptor;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);

    @SuppressWarnings("unused")
    private Properties properties;

    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;

        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String xmlResource = mappedStatement.getResource();
        Object returnValue = null;
        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = (end - start);

        if (time > 1) {
            String sql = getSql(configuration, boundSql, sqlId, time, xmlResource);
            logger.debug("=====================================================================");
            // logger.debug("sql : {}", sql);
            logger.debug("sql : \n" + sql);
            logger.debug("=====================================================================");
        }
        return returnValue;
    }

    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId,
                                long time, String xmlResource) {

        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId).append(":" + "\n");
        str.append(sql + "\n");
        str.append("query time:").append(time).append("ms");
        if(time > (60 * 1000)) str.append("\n" + "query time - check over 60 second - kjs: ").append(time).append("ms, sqlId : ").append(sqlId).append("\n"); // 60초 이상 체크
        if(time > (60 * 1000)) {

            // 현재 세션 정보 확인
            String userId = AuthenticationInterceptor.UserContext.getUserId();
            String vUserId = AuthenticationInterceptor.UserContext.getVUserId();
            String ip = AuthenticationInterceptor.UserContext.getIp();

            // tomcat root directory
            String catalinaBase = System.getProperty("catalina.base");
            String catalinaHome = System.getProperty("catalina.home");
            logger.debug("catalinaBase : " + catalinaBase);
            logger.debug("catalinaHome : " + catalinaHome);

            // 오늘 날짜
            Date date = new Date();
            String nowDate = new SimpleDateFormat("yyyyMMdd").format(date);
            String nowDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

            // 생성 파일 경로
            //String fileName = "D:\\prod_img\\QUERYTIME_" + nowDate + ".OUT"; // TEST
            String fileName = catalinaBase + "/logs/QUERYTIME_" + nowDate + ".OUT";

            // 로그 데이터
            StringBuilder str2 = new StringBuilder(100);
            str2.append("================================================ START [" + nowDateTime + "] [" + time + "ms] =================================================" + "\n");
            str2.append("query time:").append(time).append("ms" + "\n");
            str2.append("user_Id:" + userId + "\n");
            str2.append("vUser_Id:" + vUserId + "\n");
            str2.append("ip:" + ip + "\n");
            str2.append(xmlResource).append("\n");
            str2.append(sqlId).append("\n");
            str2.append(sql + "\n");
            str2.append("========================================================== END ===========================================================" + "\n");

            try {

                // 파일 객체 생성
                File file = new File(fileName);

                // true 지정시 파일의 기존 내용에 이어서 작성
                FileWriter fw = new FileWriter(file, true);

                // 파일안에 문자열 쓰기
                fw.write(str2.toString());
                fw.flush();

                // 객체 닫기
                fw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return str.toString();
    }

    private static String getParameterValue(Object obj) {
        String value = null;

        if (obj instanceof Integer || obj instanceof Long || obj instanceof Float
                || obj instanceof Double) {
            value = Matcher.quoteReplacement(obj.toString());
        } else if (obj instanceof String) {
            value = "'" + Matcher.quoteReplacement((String) obj) + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.KOREA);
            value = "'" + formatter.format(new Date()) + "'";
        } else if (obj instanceof Enum) {
            try {
                Method m = obj.getClass().getDeclaredMethod("getCode", null);
                value = "'" + (String) m.invoke(obj, null) + "'";
            } catch (Exception e) {
                value = Matcher.quoteReplacement(obj.toString());
            }
        } else {
            if (obj != null) {
                value = Matcher.quoteReplacement(obj.toString());
            } else {
                value = "null";
            }
        }

        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        // 줄삭제하고(한줄로) 표시할때
        // String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        // 쿼리 정렬 (공백라인 제거)
        String sql = boundSql.getSql().replaceAll("(?m)^[ \\t]*\\r?\\n", "");

        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();

                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);

                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));

                    }
                }
            }
        }
        return sql;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties0) {
        this.properties = properties0;
    }
}
