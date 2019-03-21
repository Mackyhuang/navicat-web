package vip.ifmm.navicatweb.service.impl;

import org.springframework.stereotype.Service;
import vip.ifmm.navicatweb.service.ConnectService;
import vip.ifmm.navicatweb.util.DataOperaterUtil;

import java.sql.SQLException;

/**
 * @author: MackyHuang
 * @eamil: 973151766@qq.com
 * @createTime: 2019/3/21 21:19
 */
@Service
public class ConnectServiceImpl implements ConnectService {

    @Override
    public DataOperaterUtil getConnection(String url, String username, String password) {
        DataOperaterUtil dataOperaterUtil = null;
        try {
            dataOperaterUtil = new DataOperaterUtil(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataOperaterUtil;
    }
}
