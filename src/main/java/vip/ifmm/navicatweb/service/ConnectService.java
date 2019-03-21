package vip.ifmm.navicatweb.service;

import vip.ifmm.navicatweb.util.DataOperaterUtil;


/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 21:08
 */

public interface ConnectService {

    DataOperaterUtil getConnection(String url, String username, String password);
}
