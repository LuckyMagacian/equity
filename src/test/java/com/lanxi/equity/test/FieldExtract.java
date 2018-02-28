package com.lanxi.equity.test;

import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangyuanjian created in 2018/2/2 14:59
 */
public class FieldExtract {

    static String range="( *(public)|(private)|(protected) *)?";
    static String staticAtt="( *static *)?";
    static String finalAtt="( *final *)?";
    static String classAtt="( *class *)";

    static String baseType="( *((byte)|(char)|(short)|(int)|(long)|(float)|(double)) *)?";
    static String classNameDef="( *[A-Z]+[a-zA-Z_0-9\\[\\]]* *)";
    static String className="( *("+baseType+"|"+classNameDef+") *)";
    static String fieldName="( *[a-zA-Z_0-9\\[\\]]+ *)";
    static String arrayType="( *("+baseType+"|"+classNameDef+") *\\[[0-9]*\\])";
    static String genericAtt="( *\\< *"+classNameDef+" *\\> *)?";




    static String classDefLine=range+staticAtt+finalAtt+classAtt+classNameDef+"\\{ *";
    static String fieldDefLine=range+staticAtt+finalAtt+className+genericAtt+fieldName+" *; *";

    @Test
    public void test1(){
        System.out.println(classDefLine);
        System.out.println(fieldDefLine);
        System.out.println("public class AccountDB {".matches(classDefLine));
        System.out.println("private List<DetailDB> detailDBList;".matches(fieldDefLine));
        System.out.println("private double pointsVal;".matches(fieldDefLine));
        System.out.println("int".matches(className));
    }

    public static void main (String[] args){
        //获取目录
        File dir = null;
        JFileChooser chooser=new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.showDialog(new JLabel(),"选择文件");
        dir= chooser.getSelectedFile();
        //获取java文件
        List<File> javaFiles =new ArrayList<File>(Arrays.asList(dir.listFiles()));
        javaFiles=javaFiles.stream().filter(f->f.getName().endsWith(".java")).collect(Collectors.toList());
        //处理流中的java文件
        javaFiles.stream().forEach(f->{
            try {
                String line=null;
                FileInputStream fin=new FileInputStream(f);
                InputStreamReader reader=new InputStreamReader(fin);
                BufferedReader buffer=new BufferedReader(reader);
                while((line=buffer.readLine())!=null){
                    line=line.trim();
                    if(line.matches(classDefLine)){
                        System.out.print("----------------------------------");
                        System.out.print(line.substring(line.indexOf("class")+5,line.indexOf('{')));
                        System.out.println("----------------------------------");
                    }
                    if(line.matches(fieldDefLine)){
                        if(line.startsWith("public")){
                            System.out.println(line.substring(line.indexOf("public")+6,line.indexOf(";")).trim());
                        }else if(line.startsWith("private")){
                            System.out.println(line.substring(line.indexOf("private")+7,line.indexOf(";")).trim());
                        }else if(line.startsWith("protected")){
                            System.out.println(line.substring(line.indexOf("protected")+9,line.indexOf(";")).trim());
                        }else{
                            System.out.println(line.substring(0,line.indexOf(";")).trim());
                        }
                    }
                }
                fin.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
