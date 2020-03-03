package org.cimsbioko.forms.application;

import java.io.File;

class DefaultFileSystem implements FileSystem {

    @Override
    public String getRoot() {
        return FormsApp.getInstance().getExternalFilesDir(null) + "";
    }

    @Override
    public String getFormsPath() {
        return getRoot() + File.separator + "forms";
    }

    @Override
    public String getInstancesPath() {
        return getRoot() + File.separator + "instances";
    }

    @Override
    public String getMetadataPath() {
        return getRoot() + File.separator + "metadata";
    }

    @Override
    public String getCachePath() {
        return getRoot() + File.separator + ".cache";
    }

    @Override
    public String getTempFilePath() {
        return getCachePath() + File.separator + "tmp.jpg";
    }

    @Override
    public String getTempDrawFilePath() {
        return getCachePath() + File.separator + "tmpDraw.jpg";
    }

    @Override
    public String getOfflineLayers() {
        return getRoot() + File.separator + "layers";
    }

    @Override
    public String getSettings() {
        return getRoot() + File.separator + "settings";
    }
}
