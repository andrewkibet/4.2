package com.example.a42;

import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

public class Appinfo {
    ApplicationInfo info;
    String label;
    List<String> permissions = new ArrayList<>();
    boolean hasCameraPermission;
    boolean hasAudioPermission;

    public ApplicationInfo getInfo() {
        return info;
    }

    public void setInfo(ApplicationInfo info) {
        this.info = info;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasCameraPermission() {
        return hasCameraPermission;
    }

    public void setHasCameraPermission(boolean hasCameraPermission) {
        this.hasCameraPermission = hasCameraPermission;
    }

    public boolean hasAudioPermission() {
        return hasAudioPermission;
    }

    public void setHasAudioPermission(boolean hasAudioPermission) {
        this.hasAudioPermission = hasAudioPermission;
    }
}
