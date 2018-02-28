package com.lanxi.equity.assist;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/7 11:08
 */
public class MyClassLoader extends ClassLoader {


    private static final File readFile(String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("file:"+absolutePath+" not found !");
        }
        return file;
    }

    private static final byte[] fileToBytes(File file) {
        if (file == null) {
            throw new NullPointerException("file is null !");
        }
        byte[] classByte = new byte[(int) file.length()];
        if (classByte.length < file.length()) {
            throw new IllegalArgumentException("file: " + file.getAbsolutePath() + " is too big !");
        }
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            fin.read(classByte);
            fin.close();
            return classByte;
        } catch (IOException e) {
            throw new RuntimeException("exception occured when convert file :" + file + " to byte[] !");
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    throw new RuntimeException("exception occured when close fileInputStream :" + file + " !");
                }
            }
        }
    }

    private Class<?> findClass(File file) {
        if (file == null) {
            throw new NullPointerException("file is null !");
        }
        byte[] bytes = fileToBytes(file);
        return defineClass(null, bytes, 0, bytes.length);
    }

    @Override protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(readFile(name));
    }


    public Class<?> loadFromFile(File file) {
        if (file == null) {
            throw new NullPointerException("file is null !");
        }
        return findClass(file);
    }

    public Class<?> loadFromPath(String name, File path) {
        File file = path;
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("filepath:" + file.getAbsolutePath() + " is not a directory !");
        }
        List<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
        File classFile = files.stream()
                              .filter(f -> f.isFile())
                              .filter(f -> f.getName().endsWith(".class"))
                              .filter(f -> f.getName().equals(name.endsWith(".class") ? name : name + ".class"))
                              .findFirst().orElse(null);
        if (classFile != null) {
            return loadFromFile(classFile);
        }
        return files.stream()
                    .filter(f -> f.isDirectory())
                    .map(f -> loadFromPath(name, f.getAbsolutePath()))
                    .filter(c -> c != null)
                    .findFirst()
                    .orElse(null);
    }

    public Class<?> loadFromPath(String name, String filePath) {
        File file = readFile(filePath);
        return loadFromPath(name, file);
    }

    public List<Class<?>> loadFromPath(File path, final boolean includeSub) {
        List<Class<?>> classes = new ArrayList<>();
        File           file    = path;
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("path:" + path + " is not a directory !");
        }
        List<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
        files.stream()
             .filter(f -> f.isFile())
             .filter(f -> f.getName().endsWith(".class"))
             .filter(f -> f != null)
             .forEach(f -> {
                 if(f!=null)
                    classes.add(loadFromFile(f));
             });
        if (!includeSub) {
            return classes;
        }

        files.stream()
             .filter(f -> f.isDirectory())
             .forEach(f -> classes.addAll(loadFromPath(f.getAbsolutePath(), includeSub)));
        return classes;
    }

    public List<Class<?>> loadFromPath(String path, final boolean includeSub) {
        File file = readFile(path);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("path:" + path + " is not a directory !");
        }
        return loadFromPath(file, includeSub);
    }

    public Class<?> loadFromFile() {
        File         file    = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.showDialog(new JLabel(), "选择文件");
        file = chooser.getSelectedFile();
        if (!file.getName().endsWith(".class")) {
            throw new IllegalArgumentException("file : " + file.getAbsolutePath() + "which is selected is not a class file !");
        }
        return findClass(file);
    }

    public List<Class<?>> loadFromPath(boolean includeSub) {
        File         file    = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.showDialog(new JLabel(), "选择文件");
        file = chooser.getSelectedFile();
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("file : " + file.getAbsolutePath() + "which is selected is not a directory !");
        }
        return loadFromPath(file, includeSub);
    }
}
