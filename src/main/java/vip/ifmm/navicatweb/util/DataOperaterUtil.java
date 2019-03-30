package vip.ifmm.navicatweb.util;

import net.sf.jsqlparser.statement.create.table.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import vip.ifmm.navicatweb.entity.TableStructure;

import java.sql.*;
import java.util.*;

/**
 * <strong>The main utility class for database operations</strong>
 * <p>In a web-based form, in order for each user's database operations
 * to not affect each other, this is required </p>
 *
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 13:26
 */
public class DataOperaterUtil {

    Logger logger = LoggerFactory.getLogger(DataOperaterUtil.class);

    Connection connection = null;

    /**
     * <strong>Constructor</strong>
     *
     * @param url      the url for the database connection
     * @param username the username for the database connection
     * @param password the password for the database connection
     * @throws SQLException
     */
    public DataOperaterUtil(String url, String username, String password) throws SQLException {
        connection = DataSourceUtil.getConnection(url, username, password);
    }

    /**
     * <p>Gets all the database names under this connection</p>
     *
     * @return all the database names
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
     * <p>Gets all the table names under the database based on the name of the database</p>
     *
     * @param databaseName the currently selected database
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
     * <p>Gets the structure of the table based on the table name</p>
     * <p>preparing for Design of Table</p>
     *
     * @param databaseName the currently selected database, Otherwise,
     *                     error data caused by different libraries of the same table will appear in the result set
     * @param tableName    the currently selected table
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
     * <p>Gets all the column names of table</p>
     * <p>Used to render the header</p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @return all the column names of table
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

    public List<String> getColumnTypeByTableName(String databaseName, String tableName) throws SQLException {
        List<String> columnTypeList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%");
        while (columns.next()) {
            String columnName = columns.getString("TYPE_NAME");
            columnTypeList.add(getColumnType(columnName));
        }
        return columnTypeList;
    }

    /**
     * <p>Gets the data in the table </p>
     * <p>Used to render the entire table </p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @return information for a whole table
     * @throws SQLException
     */
    public List<List<String>> list(String databaseName, String tableName) throws SQLException {
//        List<Map<String, String>> queryList = new ArrayList<>();
        List<List<String>> queryList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName.trim());
        PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql));
        logger.info(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> columnNames = getColumnNameByTableName(databaseName, tableName);
        while (resultSet.next()) {
//            Map<String, String> resultMap = new HashMap<>();
            List<String> resultList = new LinkedList<>();
            Iterator<String> iterator = columnNames.iterator();
            while (iterator.hasNext()) {
                //next is column name
                String next = iterator.next();
                String record = String.valueOf(resultSet.getObject(next));
                resultList.add(record);
            }
            queryList.add(resultList);
        }
        return queryList;
    }

    /**
     * <p>Gets the primary key of the database </p>
     * <p>Data preparation for other operations </p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @return A List for primary key of the database
     * @throws SQLException
     */
    public List<String> listPrimaryKey(String databaseName, String tableName) throws SQLException {
        //gets primary key info
        String pk_name = getPrimaryKeyColumnName(databaseName, tableName);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(pk_name + " ");
        sql.append("From ");
        sql.append(tableName.trim());
        PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql));
        logger.info(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> primaryList = new ArrayList<>();
        while (resultSet.next()) {
            String primary = String.valueOf(resultSet.getObject(1));
            primaryList.add(primary);
        }
        return primaryList;
    }

    /**
     * <p>Gets the column name of the primary key</p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @return The column name of the primary key
     * @throws SQLException
     */
    public String getPrimaryKeyColumnName(String databaseName, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(databaseName, null, tableName);
        String pk_name = null;
        if (primaryKeys.next()) {
            pk_name = String.valueOf(primaryKeys.getObject("COLUMN_NAME"));
        }
        return pk_name;
    }

    /**
     * <p>Removes a record from the database</p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @param index        position of the column to be deleted
     * @return number of rows affected
     * @throws SQLException
     */
    public Integer remove(String databaseName, String tableName, String index) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(databaseName, null, tableName);
        String pk_name = null;
        if (primaryKeys.next()) {
            pk_name = String.valueOf(primaryKeys.getObject("COLUMN_NAME"));
        }
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName.trim() + " ");
        sql.append("WHERE ");
        sql.append(pk_name + "=" + "?");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setObject(1, index);
        logger.info(preparedStatement.toString());
        int result = preparedStatement.executeUpdate();
        return result;
    }

    /**
     * <p>Updates a record from the database</p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @param data         every column value, if NULL OR "" will tobe ""
     * @param index        index position of the column to be updated
     * @return
     * @throws SQLException
     */
    public Integer update(String databaseName, String tableName, List<String> data, String index) throws SQLException {
        String pk_name = getPrimaryKeyColumnName(databaseName, tableName);
        List<String> columnNames = getColumnNameByTableName(databaseName, tableName);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName.trim() + " ");
        sql.append("SET ");
        for (int i = 0; i < columnNames.size(); i++) {
            sql.append(columnNames.get(i) + "=" + "?");
            if (i != columnNames.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(" WHERE ");
        sql.append(pk_name + "=" + index);
        PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql));
        for (int i = 0; i < columnNames.size(); i++) {
            preparedStatement.setObject(i + 1, data.get(i));
        }
        logger.info(preparedStatement.toString());
        int result = preparedStatement.executeUpdate();
        return result;
    }

    /**
     * <p>Converts the database type to a Java type</p>
     *
     * @param columnType database type
     * @return java type
     */
    public static String getColumnType(String columnType) {
        columnType = columnType.toLowerCase();
        if (columnType.equals("varchar")
                || columnType.equals("nvarchar")
                || columnType.equals("char")) {
            return "String";
        } else if (columnType.equals("tinyblob")
                || columnType.equals("blob")
                || columnType.equals("mediumblob")
                || columnType.equals("longblob")) {
            return "byte[]1111";
        } else if (columnType.equals("datetime")
                || columnType.equals("date")
                || columnType.equals("timestamp")
                || columnType.equals("time")
                || columnType.equals("year")) {
            return "Date";
        } else if (columnType.equals("bit")
                || columnType.equals("int")
                || columnType.equals("tinyint")
                || columnType.equals("smallint")
                ) {
            return "int";
        } else if (columnType.equals("float")) {
            return "Float";
        } else if (columnType.equals("double")) {
            return "Double";
        } else if (columnType.equals("decimal")) {
            return "Double";
        }
        return "ErrorType";
    }

    /**
     * <p>Insert a record into this table</p>
     *
     * @param databaseName the currently selected database
     * @param tableName    the currently selected table
     * @param data every column data , if NULL OR "" will tobe ""
     * @return
     * @throws SQLException
     */
    public Integer insert(String databaseName, String tableName, List<String> data) throws SQLException {
        List<String> columnNames = getColumnNameByTableName(databaseName, tableName);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName);
        sql.append("(");
        for (int i = 0; i < columnNames.size(); i++) {
            sql.append(columnNames.get(i));
            if (i != columnNames.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        sql.append(" VALUES ");
        sql.append("(");
        for (int i = 0; i < columnNames.size(); i++) {
            sql.append("?");
            if (i != columnNames.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql));
        for (int i = 0; i < columnNames.size(); i++) {
            preparedStatement.setObject(i + 1, data.get(i).trim());
        }
        logger.info(String.valueOf(preparedStatement.toString()));
        int result = preparedStatement.executeUpdate();
        return result;
    }
}
