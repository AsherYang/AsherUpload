package com.asher.yang.upload.bean;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class Asher {

    private String name;
    private String serverUrl;

    private List<View> views;

    public Asher() {
        this(null, null);
    }

    public Asher(String name, String serverUrl) {
        this.name = name;
        this.serverUrl = serverUrl;
        this.views = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public View getViewByName(String lastSelectedViewName) {
        for (View view : views) {
            if (StringUtils.equals(lastSelectedViewName, view.getName())) {
                return view;
            }
        }
        return null;
    }

    public void update(Asher asher) {
        this.name = asher.getName();
        this.serverUrl = asher.getServerUrl();
        this.views.clear();
    }
}
