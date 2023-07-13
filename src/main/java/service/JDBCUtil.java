package service;

import dto.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import utils.StringUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class JDBCUtil {
    private static final Logger logger = LoggerFactory.getLogger(JDBCUtil.class);
    private static final int FETCH_SIZE = 1000;
    private final JdbcTemplate jdbcTemplate;

    protected JDBCUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static List<? extends Object> query(String sql) {
        return new ArrayList<>();
    }

    public void doSelect(String sql, int timeoutSeconds, List<Field> selectFields,
                         List<Field> paramFields, List<Object> paramList, BiConsumer consumer) {
        int sqlExecCostTime = 0;
        long sqlExecStartTime = System.currentTimeMillis();
        DataSource dataSource = jdbcTemplate.getDataSource();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setFetchSize(FETCH_SIZE);
            ps.setQueryTimeout(timeoutSeconds);
//            ctx.registerCancelTask(getCancelPreparedStatementRunnable(ps));
            if (paramList != null) {
                int paramIndex = 1;
                for (int i = 0; i < paramList.size(); i++) {
                    Field field = paramFields.get(i);
                    Object param = paramList.get(i);
                    if (param instanceof List) {
                        List<Object> listParam = (List<Object>) param;
                        for (int j = 0; j < listParam.size(); j++) {
                            Object item = listParam.get(j);

                            Object preparedValue = field.setStatementParameter(ps, item, paramIndex++);
                            ps.setObject(paramIndex++, preparedValue, field.getFieldType().getSqlType());
                            listParam.set(j, preparedValue);
                        }
                    } else {
                        Object preparedValue = field.setStatementParameter(ps, param, paramIndex++);
                        paramList.set(i, preparedValue);
                    }
                }
            }
            rs = ps.executeQuery();
            sqlExecCostTime = (int) (System.currentTimeMillis() - sqlExecStartTime);
            int check = 0;
            long cursor = 0;
            for (; rs.next(); ) {
                if (++check == 100) {
                    check = 0;
//                    ctx.checkContextStatus();
                }
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < selectFields.size(); i++) {
                    Field selectField = selectFields.get(i);
                    String alias = selectField.getName();
//                    Object value = getValue(rs, selectField, i);

                    Object value = rs.getObject(i+1, selectField.getFieldType().getTypeClass());

                    map.put(alias, value);
                }
                consumer.accept(cursor++, map);
            }
            handleWarnings(ps);
        } catch (SQLException e) {
//            ctx.setTransactionBroken(e);
//            throw ctx.newServerInternalException(e);

        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            DataSourceUtils.releaseConnection(connection, dataSource);
            printDebugSql(sql, paramList, sqlExecCostTime);
        }
    }

    private Object getValue(ResultSet rs, Field field, int index) {
        try {
            return field.getValue(rs, index + 1);
        } catch (SQLException e) {
            //Field field = selectField.getField();
            String typeName = field.getFieldType().getName();
            String msg = StringUtil.join("Get [", field.getName(), "(", typeName, ")] value failed");
            throw new IllegalArgumentException(msg, e);
        }
    }

    private void printDebugSql(String sql, List<Object> paramList, int sqlExecCostTime) {
        logger.debug("Execute sql costs {} ms: \n{} \nparams: {}", sqlExecCostTime, sql, paramList);
    }


    protected void handleWarnings(Statement stmt) throws SQLException {
        SQLWarning warnings = stmt.getWarnings();
        if (warnings != null) {
            if (jdbcTemplate.isIgnoreWarnings()) {
                if (logger.isDebugEnabled()) {
                    SQLWarning warningToLog = warnings;
                    while (warningToLog != null) {
                        String message =  String.join("",
                                "SQLWarning ignored: SQL state '", warningToLog.getSQLState(),
                                "', error code '", String.valueOf(warningToLog.getErrorCode()),
                                "', message [", warningToLog.getMessage(), "]"
                        );
                        logger.debug(message);
                        warningToLog = warningToLog.getNextWarning();
                    }
                }
            } else {
                throw new SQLWarningException("Warning not ignored", warnings);
            }
        }
    }
}
