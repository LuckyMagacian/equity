package com.lanxi.equity.report.api;

import com.lanxi.equity.config.ConstConfig;
import com.lanxi.util.utils.SignUtil;
import org.dom4j.Element;

import java.util.*;

/**
 * @author yangyuanjian created in 2018/2/26 15:37
 */
public interface ReportSign {
    static String sign(Element head, Element body){
        if(head==null){
            throw new NullPointerException("head both can't be null !");
        }
        Map<String,String> map=new HashMap<>();
        List<Element> headEles=head.elements();

        headEles.stream().filter(e->!e.getName().equals("sign")).forEach(e->map.put(e.getName(),e.asXML()));
        if(body!=null){
            List<Element> bodyEles=body.elements();
            bodyEles.stream().forEach(e->map.put(e.getName(),e.asXML()));
        }
        StringBuffer buffer=new StringBuffer();
        List<String> keys=new ArrayList<>(map.keySet());
        Collections.sort(keys);
        keys.stream().forEach(e->{
            buffer.append(e).append("=").append(map.get(e)).append("&");
        });
        buffer.append("key=").append(ConstConfig.TEST_SIGN_KEY);
        return SignUtil.md5LowerCase(buffer.toString(),"utf-8");
    }

    static String plainText(Element head,Element body){
        if(head==null){
            throw new NullPointerException("head both can't be null !");
        }
        Map<String,String> map=new HashMap<>();
        List<Element> headEles=head.elements();

        headEles.stream().filter(e->!e.getName().equals("sign")).forEach(e->map.put(e.getName(),e.asXML()));
        if(body!=null){
            List<Element> bodyEles=body.elements();
            bodyEles.stream().forEach(e->map.put(e.getName(),e.asXML()));
        }
        StringBuffer buffer=new StringBuffer();
        List<String> keys=new ArrayList<>(map.keySet());
        Collections.sort(keys);
        keys.stream().forEach(e->{
            buffer.append(e).append("=").append(map.get(e)).append("&");
        });
        buffer.append("key=").append("$yourKey");
        return buffer.toString();
    }
}
