package com.lanxi.equity.assist;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangyuanjian created in 2018/2/26 18:46
 */
public interface EasyToElement {
    default Element easyToElement(String nodeName){
        Class clazz=this.getClass();
        List<Field> fields=ExtractFields.getClassFields(clazz, true)
                                        .parallelStream()
                                        .peek(f->f.setAccessible(true))
                                        .collect(Collectors.toList());
        Element element= DocumentHelper.createElement(nodeName==null||nodeName.isEmpty()?"node":nodeName.trim());

        fields.parallelStream().forEach(e->{
            try {
                String name=e.getName();
                Class type=e.getType();
                Object value=e.get(this);
                if(value==null){
                    return;
                }
                if(CharSequence.class.isAssignableFrom(type)){
                    if(value.toString().isEmpty()){
                        return;
                    }
                    element.addElement(name).setText(value==null?"":value.toString());
                }else if(Number.class.isAssignableFrom(type)){
                    element.addElement(name).setText(value==null?"":value+"");
                }else{
                    return;
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });
        return element;
    }
}
