package com.lanxi.equity.servlet;

import com.lanxi.equity.config.ConstConfig;
import com.lanxi.util.utils.LoggerUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
//        if(ConstConfig.DEVELOP){
//            LoggerUtil.setLogLevel(LoggerUtil.LogLevel.DEBUG);
//        }else
            {
            LoggerUtil.setLogLevel(LoggerUtil.LogLevel.INFO);
        }
        LoggerUtil.init();
    }
}
