package vip.ifmm.navicatweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import vip.ifmm.navicatweb.service.ConnectService;
import vip.ifmm.navicatweb.util.DataOperaterUtil;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 21:20
 */
@Controller
public class ConnectController {

    @Autowired
    private ConnectService connectService;

    @PostMapping(value = "/getConnection")
    public String getConnection(String url, String username, String password, Model model, HttpSession session){
        DataOperaterUtil util = connectService.getConnection(url, username, password);
        List<String> databaseList = null;
        try {
            databaseList = util.queryDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("util", util);
        model.addAttribute("databaseList", databaseList);
        return "database";
    }

}
