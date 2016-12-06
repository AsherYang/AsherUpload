package com.asher.yang.upload.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class View {

    private String name;
    private String path;

    private final List<View> subViews = new LinkedList<View>();

    public View() {
    }

    public View(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<View> getSubViews() {
        return subViews;
    }

    public void addSubView(View subView) {
        this.subViews.add(subView);
    }

    public static View createView(String viewName, String viewUrl) {
        return new View(viewName, viewUrl);
    }
}
