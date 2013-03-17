package org.elasticsearch.rest.action.logviewer;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.rest.RestController;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * @author ferhat
 */
public class GetLogFileContentActionTest {

    private GetLogFileContentAction action;

    @Before
    public void before() {
        action = new GetLogFileContentAction(ImmutableSettings.Builder.EMPTY_SETTINGS, null,
                new RestController(ImmutableSettings.Builder.EMPTY_SETTINGS), null);
    }

    @Test
    public void testTail() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("test.log");
        File file = new File(url.getFile());
        String content = action.readLastLines(file, 1);
        assertEquals("[2013-03-15 12:12:58,117][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: closed", content);
    }

    @Test
    public void testTailWithN() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("test.log");
        File file = new File(url.getFile());
        String content = action.readLastLines(file, 6);
        String expected = "[2013-03-15 12:11:33,783][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: started\n" +
                "[2013-03-15 12:11:34,801][INFO ][gateway                  ] [Stewart, Stanley] recovered [5] indices into cluster_state\n" +
                "[2013-03-15 12:12:57,917][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: stopping ...\n" +
                "[2013-03-15 12:12:58,108][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: stopped\n" +
                "[2013-03-15 12:12:58,108][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: closing ...\n" +
                "[2013-03-15 12:12:58,117][INFO ][node                     ] [Stewart, Stanley] {0.20.1}[8638]: closed";
        assertEquals(expected, content);
    }

    @Test
    public void testMore() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("test.log");
        File file = new File(url.getFile());
        String content = action.readFirstLines(file, 1);
        assertEquals("[2013-03-15 12:05:28,509][INFO ][node                     ] [Magik] {0.20.1}[7976]: initializing ...", content);
    }

    @Test
    public void testMoreWithN() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("test.log");
        File file = new File(url.getFile());
        String content = action.readFirstLines(file, 3);
        String expected = "[2013-03-15 12:05:28,509][INFO ][node                     ] [Magik] {0.20.1}[7976]: initializing ...\n" +
                "[2013-03-15 12:05:28,533][INFO ][plugins                  ] [Magik] loaded [log-viewer], sites [head, es]\n" +
                "[2013-03-15 12:05:31,849][INFO ][node                     ] [Magik] {0.20.1}[7976]: initialized";
        assertEquals(expected, content);
    }
}
