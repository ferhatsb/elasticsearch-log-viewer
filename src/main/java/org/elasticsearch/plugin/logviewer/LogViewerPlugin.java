package org.elasticsearch.plugin.logviewer;

import org.elasticsearch.rest.action.logviewer.GetLogFileContentAction;
import org.elasticsearch.rest.action.logviewer.GetLogFileNamesAction;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

/**
 * @author ferhat
 */
public class LogViewerPlugin  extends AbstractPlugin {

    public String name() {
        return "log-viewer";
    }

    public String description() {
        return "Log viewer plugin to tail ElasticSearch logs";
    }

    @Override public void processModule(Module module) {
        if (module instanceof RestModule) {
            ((RestModule) module).addRestAction(GetLogFileNamesAction.class);
            ((RestModule) module).addRestAction(GetLogFileContentAction.class);
        }
    }
}