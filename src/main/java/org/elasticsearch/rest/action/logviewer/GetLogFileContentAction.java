package org.elasticsearch.rest.action.logviewer;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import org.elasticsearch.threadpool.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.NOT_FOUND;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.action.support.RestXContentBuilder.restContentBuilder;

public class GetLogFileContentAction extends BaseRestHandler {

    private ThreadPool threadPool;

    @Inject
    public GetLogFileContentAction(Settings settings, Client client, RestController controller,
                                   ThreadPool threadPool) {
        super(settings, client);
        this.threadPool = threadPool;
        controller.registerHandler(GET, "/_logviewer/{name}", this);
    }

    public void handleRequest(final RestRequest request, final RestChannel channel) {

        threadPool.generic().execute(new Runnable() {
            @Override
            public void run() {
                String pathLogs = settings.get("path.logs");
                File logsDir = new File(pathLogs);
                String filePath = logsDir.getAbsolutePath() + "/" + request.param("name");
                File logFile = new File(filePath);

                try {
                    if (!logFile.exists()) {
                        channel.sendResponse(new StringRestResponse(NOT_FOUND, "Log file not found"));
                    } else {
                        long line = request.paramAsLong("line", 1);
                        String content = "";
                        String type = request.param("type", "tail");
                        if (type.equals("more")) {
                            content = readFirstLines(logFile, line);
                        } else if (type.equals("tail")) {
                            content = readLastLines(logFile, line);
                        }
                        XContentBuilder builder = restContentBuilder(request);
                        builder.startObject();
                        builder.field("name", logFile.getName());
                        builder.field("content", content);
                        builder.endObject();
                        channel.sendResponse(new XContentRestResponse(request, OK, builder));
                    }
                } catch (Exception e) {
                    try {
                        channel.sendResponse(new XContentThrowableRestResponse(request, e));
                    } catch (IOException e1) {
                        logger.error("Failed to send failure response", e1);
                    }
                }
            }
        });
    }

    // taken http://www.brilliantsheep.com/reading-the-last-line-of-a-file-in-java-through-random-access/
    // modified for x lines
    public String readLastLines(File file, long lineCount) throws IOException {

        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
        StringBuilder builder = new StringBuilder();
        try {
            long fileLength = file.length() - 1;
            Stack<String> lifo = new Stack<String>();
            long i = lineCount;
            while (i != 0) {
                StringBuilder sb = new StringBuilder();
                for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                    fileHandler.seek(filePointer);
                    int readByte = fileHandler.readByte();

                    if (readByte == 0xA) {
                        if (filePointer == fileLength) {
                            continue;
                        } else {
                            break;
                        }
                    } else if (readByte == 0xD) {
                        if (filePointer == fileLength - 1) {
                            continue;
                        } else {
                            break;
                        }
                    }
                    sb.append((char) readByte);
                }
                fileLength = fileLength - (sb.length() + 1);
                i--;
                lifo.push(sb.reverse().toString());
            }

            int lifoSize = lifo.size();
            String line = lifo.pop();
            builder.append(line);

            for (int begin = 1; begin < lifoSize; begin++) {
                builder.append(System.getProperty("line.separator"));
                line = lifo.pop();
                builder.append(line);
            }
            return builder.toString();
        } catch(IOException e){
            return builder.toString();
        } finally {
            fileHandler.close();
        }
    }

    public String readFirstLines(File file, long lineCount) throws IOException {
        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(fileHandler.readLine());

            for (int i = 1; i < lineCount; i++) {
                String line = fileHandler.readLine();
                if (line != null) {
                    builder.append(System.getProperty("line.separator"));
                    builder.append(line);
                } else break;
            }
            return builder.toString();
        } finally {
            fileHandler.close();
        }
    }
}

