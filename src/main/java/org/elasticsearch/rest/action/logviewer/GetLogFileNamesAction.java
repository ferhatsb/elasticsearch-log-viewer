package org.elasticsearch.rest.action.logviewer;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;

import java.io.File;
import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.action.support.RestXContentBuilder.restContentBuilder;

public class GetLogFileNamesAction extends BaseRestHandler {

    @Inject
    public GetLogFileNamesAction(Settings settings, Client client, RestController controller) {
        super(settings, client);

        controller.registerHandler(GET, "/_logviewer/logs", this);
    }

    public void handleRequest(final RestRequest request, final RestChannel channel) {
        String pathLogs = settings.get("path.logs");
        File[] logs = new File(pathLogs).listFiles();

        try {
            XContentBuilder builder = restContentBuilder(request);
            builder.startArray();
            for (File log : logs) {
                builder.startObject();
                builder.field("name", log.getName());
                builder.field("read", log.canRead());
                builder.endObject();
            }
            builder.endArray();
            channel.sendResponse(new XContentRestResponse(request, OK, builder));
        } catch (Exception e) {
            try {
                channel.sendResponse(new XContentThrowableRestResponse(request, e));
            } catch (IOException e1) {
                logger.error("Failed to send failure response", e1);
            }
        }
    }
}

