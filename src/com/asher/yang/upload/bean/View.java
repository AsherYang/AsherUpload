package com.asher.yang.upload.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class View {

    private String name;
    private String url;

    private final List<View> subViews = new LinkedList<View>();

    public View() {
    }

    public View(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<View> getSubViews() {
        return subViews;
    }

    public void addSubView(View subView) {
        this.subViews.add(subView);
    }
}
