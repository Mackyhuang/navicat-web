package vip.ifmm.navicatweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 16:14
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/index", "/"})
    public String index(){
        return "shared_file";
    }
}
