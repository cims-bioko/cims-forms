package org.cimsbioko.forms.http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.cimsbioko.forms.http.stub.StubOpenRosaHttpInterface;
import org.cimsbioko.forms.http.stub.StubOpenRosaHttpInterfaceError;
import org.cimsbioko.forms.utilities.DocumentFetchResult;
import org.cimsbioko.forms.utilities.WebCredentialsUtils;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class FormsAppServerClientTest {

    static final String URL_STRING = "http://testurl";

    CollectServerClient collectServerClient;
    CollectServerClient collectServerClientError;

    @Before
    public void setup() {
        collectServerClient = new CollectServerClient(new StubOpenRosaHttpInterface(), new WebCredentialsUtils());
        collectServerClientError = new CollectServerClient(new StubOpenRosaHttpInterfaceError(), new WebCredentialsUtils());
    }

    @Test
    public void testGetXMLDocumentErrorResponse() {
        DocumentFetchResult fetchResult = collectServerClientError.getXmlDocument(URL_STRING);
        assertEquals(fetchResult.errorMessage, "Parsing failed with null while accessing " + URL_STRING);
    }

    @Test
    public void testGetXMLDocument() {
        DocumentFetchResult fetchResult = collectServerClient.getXmlDocument(URL_STRING);
        assertNull(fetchResult.errorMessage);
        assertEquals(fetchResult.responseCode, 0);
        assertTrue(fetchResult.isOpenRosaResponse);
    }

    @Test
    public void testGetPlainTextMimeType() {
        assertEquals(CollectServerClient.getPlainTextMimeType(), "text/plain");
    }
}


