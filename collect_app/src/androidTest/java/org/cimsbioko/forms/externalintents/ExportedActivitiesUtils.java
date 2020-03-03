package org.cimsbioko.forms.externalintents;

import junit.framework.Assert;

import java.io.File;

import org.cimsbioko.forms.application.FormsApp;
import timber.log.Timber;

class ExportedActivitiesUtils {

    private static final String[] DIRS = new String[]{
            FormsApp.getFileSystem().getRoot(),
            FormsApp.getFileSystem().getFormsPath(),
            FormsApp.getFileSystem().getInstancesPath(),
            FormsApp.getFileSystem().getCachePath(),
            FormsApp.getFileSystem().getMetadataPath(),
            FormsApp.getFileSystem().getOfflineLayers()
    };

    private ExportedActivitiesUtils() {

    }

    static void clearDirectories() {
        for (String dirName : DIRS) {
            File dir = new File(dirName);
            if (dir.exists()) {
                if (dir.delete()) {
                    Timber.i("Directory was not deleted");
                }
            }
        }

    }

    static void testDirectories() {
        for (String dirName : DIRS) {
            File dir = new File(dirName);
            Assert.assertTrue("File " + dirName + "does not exist", dir.exists());
            Assert.assertTrue("File" + dirName + "does not exist", dir.isDirectory());
        }
    }

}
