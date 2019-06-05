package org.cimsbioko.forms.externalintents;

import junit.framework.Assert;

import java.io.File;

import timber.log.Timber;

import static org.cimsbioko.forms.application.FormsApp.CACHE_PATH;
import static org.cimsbioko.forms.application.FormsApp.FORMS_PATH;
import static org.cimsbioko.forms.application.FormsApp.INSTANCES_PATH;
import static org.cimsbioko.forms.application.FormsApp.METADATA_PATH;
import static org.cimsbioko.forms.application.FormsApp.ODK_ROOT;
import static org.cimsbioko.forms.application.FormsApp.OFFLINE_LAYERS;

class ExportedActivitiesUtils {

    private static final String[] DIRS = new String[]{
            ODK_ROOT, FORMS_PATH, INSTANCES_PATH, CACHE_PATH, METADATA_PATH, OFFLINE_LAYERS
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
