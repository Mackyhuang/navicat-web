package vip.ifmm.navicatweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.ifmm.navicatweb.entity.TableStructure;
import vip.ifmm.navicatweb.util.DataOperaterUtil;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 21:54
 */
@Controller
public class OperaterController {

    @RequestMapping(value = "/getTables")
    public String getTables(String databaseName, HttpSession session, Model model){
        DataOperaterUtil util = (DataOperaterUtil)session.getAttribute("util");
        List<String> tableList = null;
        try {
            tableList = util.queryTables(databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("selected", databaseName);
        model.addAttribute("tableList", tableList);
        return "database";
    }

    @RequestMapping(value = "/getdatabaseNames")
    public String getTables(HttpSession session, Model model){
        DataOperaterUtil util = (DataOperaterUtil)session.getAttribute("util");
        List<String> databaseList = null;
        try {
            databaseList = util.queryDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.removeAttribute("selected");
        model.addAttribute("databaseList", databaseList);
        return "database";
    }

    @RequestMapping(value = "/getDetailTable")
    public String getDetailTable(String tableName, HttpSession session, Model model){
        DataOperaterUtil util = (DataOperaterUtil)session.getAttribute("util");
        String selectedDatabase = (String)session.getAttribute("selected");
        List<String> columnProperty = null;
        List<String> tableList = null;
        List<List<String>> detailTableInfo = null;
        try {
            columnProperty = util.getColumnNameByTableName(selectedDatabase, tableName);
            tableList = util.queryTables(selectedDatabase);
            detailTableInfo = util.list(selectedDatabase, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("columnProperty", columnProperty);
        model.addAttribute("tableList", tableList);
        model.addAttribute("detailTableInfo", detailTableInfo);
        System.out.println(detailTableInfo.size());
        return "database";
    }
}
