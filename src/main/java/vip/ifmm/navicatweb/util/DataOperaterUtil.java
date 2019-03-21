package vip.ifmm.navicatweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import vip.ifmm.navicatweb.entity.TableStructure;

import java.sql.*;
import java.util.*;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 13:26
 */
public class DataOperaterUtil {

    Logger logger = LoggerFactory.getLogger(DataOperaterUtil.class);

    Connection connection = null;

    public DataOperaterUtil(String url, String username, String password) throws SQLException {
        connection = DataSourceUtil.getConnection(url, username, password);
    }

    /**
     * 获取表中的数据
     * @param databaseName
     * @param tableName
     * @return 可以返回list list 也可以返回 list map类型的
     * @throws SQLException
     */
    public List<List<String>> list(String databaseName, String tableName) throws SQLException {
//        List<Map<String, String>> queryList = new ArrayList<>();
        List<List<String>> queryList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(StringUtils.trimAllWhitespace(tableName));
        PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql));
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> columnNames = getColumnNameByTableName(databaseName, tableName);
        while (resultSet.next()) {
//            Map<String, String> resultMap = new HashMap<>();
            List<String> resultList = new LinkedList<>();
            Iterator<String> iterator = columnNames.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                String record = String.valueOf(resultSet.getObject(next));
                resultList.add(record);
            }
            queryList.add(resultList);
        }
        return queryList;
    }

    /**
     * 获取当前数据库下面所有的数据库名字
     * @return
     * @throws SQLException
     */
    public List<String> queryDatabase() throws SQLException {
        List<String> databaseList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet catalogs = metaData.getCatalogs();
        while (catalogs.next()) {
            String databaseName = catalogs.getString("TABLE_CAT").toLowerCase();
            databaseList.add(databaseName);
            logger.info(databaseName);
        }
        return databaseList;
    }

    /**
     * 根据数据库的名字获取这个数据库下面的所有表名
     * @param databaseName
     * @throws SQLException
     */
    public List<String> queryTables(String databaseName) throws SQLException {
        List<String> tableList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(databaseName, null, null,
                new String[]{"TABLE"});
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME").toLowerCase();
            tableList.add(tableName);
            logger.info(tableName);
        }
        return tableList;
    }

    /**
     * 根据表名获取这个表的结构 用于 设计表 专用
     * @param databaseName 需要制定数据库名字，不然会导致同表不同库导致的错误数据出现再结果集
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<TableStructure> queryStructure(String databaseName, String tableName) throws SQLException {
        List<TableStructure> structureList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%");
        while (columns.next()) {
            TableStructure structure = new TableStructure();

            String columnName = columns.getString("COLUMN_NAME");
            structure.setColumnName(columnName);

            String typeName = columns.getString("TYPE_NAME");
            structure.setTypeName(typeName);

            String columnSize = columns.getString("COLUMN_SIZE");
            structure.setColumnSize(columnSize);

            String decimalDigits = columns.getString("DECIMAL_DIGITS");
            structure.setDecimalDigits(decimalDigits);

            String nullable = columns.getString("NULLABLE");
            structure.setNullable(nullable);

            String remarks = columns.getString("REMARKS");
            if (remarks == null || remarks.equals("")) {
                remarks = columnName;
            }
            structure.setRemark(remarks);

            structureList.add(structure);
        }
        return structureList;
    }

    /**
     * 获取表的表头名字 用于list情况下的表头渲染
     * @param databaseName
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<String> getColumnNameByTableName(String databaseName, String tableName) throws SQLException {
        List<String> columnNameList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%");
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            columnNameList.add(columnName);
        }
        return columnNameList;
    }
}
