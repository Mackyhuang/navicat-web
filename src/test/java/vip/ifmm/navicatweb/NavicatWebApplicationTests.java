package vip.ifmm.navicatweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vip.ifmm.navicatweb.entity.TableStructure;
import vip.ifmm.navicatweb.util.DataOperaterUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavicatWebApplicationTests {

    @Test
    public void contextLoads() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "8023";
        DataOperaterUtil dataOperaterUtil = new DataOperaterUtil(url, username, password);

        String[] strings = new String[]{"101", "", "i"};
//        dataOperaterUtil.queryTables("test");
//        List<TableStructure> structureList = dataOperaterUtil.queryStructure("test", "province");
//        Iterator<TableStructure> iterator = structureList.iterator();
        List<String> primaryKeyColumnName = dataOperaterUtil.getPrimaryKeyColumnName("test", "province");
        Iterator<String> iterator = primaryKeyColumnName.iterator();
//        List<String> columnNameByTableName = dataOperaterUtil.getColumnNameByTableName("test", "province");
//        List<Map<String, String>> list = dataOperaterUtil.list("test", "province");
//        Iterator<Map<String, String>> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
//        List<String> strings = dataOperaterUtil.listPrimaryKey("test", "province");
//        Iterator<String> iterator = strings.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

//        Integer remove = dataOperaterUtil.remove("test", "province", "1");
//        System.out.println(remove);


//        Integer update = dataOperaterUtil.update("test", "province", Arrays.asList(strings), "1");
//        System.out.println(update);

//        List<String> columnTypeByTableName = dataOperaterUtil.getColumnTypeByTableName("test", "province");
//        Iterator<String> iterator = columnTypeByTableName.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//        System.out.println(dataOperaterUtil.insert("test", "province", Arrays.asList(strings)));
    }

}
