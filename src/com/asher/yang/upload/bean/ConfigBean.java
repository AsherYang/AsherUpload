package com.asher.yang.upload.bean;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * ftp bean
 */
public class ConfigBean {

    /**
     * 远程主机名
     */
    private String host;

    /**
     * 远程主机ftp 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 远程下载地址
     */
    private String fromPath;

    /**
     * 远程上传地址
     */
    private String toPath;

    /**
     * 上传文件名称
     */
    private String fileName;

    /**
     * 是否只显示Release版本
     */
    private boolean isOnlyRelease;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromPath() {
        return fromPath;
    }

    public void setFromPath(String fromPath) {
        this.fromPath = fromPath;
    }

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isOnlyRelease() {
        return isOnlyRelease;
    }

    public void setOnlyRelease(boolean onlyRelease) {
        isOnlyRelease = onlyRelease;
    }
}
