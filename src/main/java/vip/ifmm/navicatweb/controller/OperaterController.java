package vip.ifmm.navicatweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    /**
     * session 中
     * selected 已选择的数据库
     * tableName 已选择的表名
     * util 操作工具类
     */

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
        session.setAttribute("tableName", tableName);
        List<String> columnProperty = null;
        List<String> tableList = null;
        List<List<String>> detailTableInfo = null;
        List<String> primaryKeys = null;
        try {
            columnProperty = util.getColumnNameByTableName(selectedDatabase, tableName);
            tableList = util.queryTables(selectedDatabase);
            detailTableInfo = util.list(selectedDatabase, tableName);
            primaryKeys = util.listPrimaryKey(selectedDatabase, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("columnProperty", columnProperty);
        model.addAttribute("tableList", tableList);
        model.addAttribute("detailTableInfo", detailTableInfo);
        model.addAttribute("primaryKeys", primaryKeys);
        return "database";
    }

    @ResponseBody
    @RequestMapping(value = "/remove")
    public String remove(String index, HttpSession session, Model model){
        DataOperaterUtil util = (DataOperaterUtil)session.getAttribute("util");
        String selectedDatabase = (String)session.getAttribute("selected");
        String tableName = (String)session.getAttribute("tableName");
        Integer isRemove = null;
        try {
            isRemove = util.remove(selectedDatabase, tableName, index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(isRemove);
    }
}
