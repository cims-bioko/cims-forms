package org.cimsbioko.forms.application;

public interface FileSystem {
    String getRoot();
    String getFormsPath();
    String getInstancesPath();
    String getMetadataPath();
    String getCachePath();
    String getTempFilePath();
    String getTempDrawFilePath();
    String getOfflineLayers();
    String getSettings();
}
