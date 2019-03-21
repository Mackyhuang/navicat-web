package vip.ifmm.navicatweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vip.ifmm.navicatweb.entity.TableStructure;
import vip.ifmm.navicatweb.util.DataOperater;
import vip.ifmm.navicatweb.util.DataSourceUtil;

import java.sql.SQLException;
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
        DataOperater dataOperater = new DataOperater(url, username, password);
//        dataOperater.queryTables("test");
//        List<TableStructure> structureList = dataOperater.queryStructure("test", "province");
//        Iterator<TableStructure> iterator = structureList.iterator();
//        List<String> columnNameByTableName = dataOperater.getColumnNameByTableName("test", "province");
        List<Map<String, String>> list = dataOperater.list("test", "province");
        Iterator<Map<String, String>> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
