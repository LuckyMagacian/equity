package com.lanxi.equity.report.api;

import org.dom4j.Element;

/**
 * @author yangyuanjian created in 2018/2/26 15:55
 */
public interface ReqProto {
    <T> T fromElement(Element element);
}
