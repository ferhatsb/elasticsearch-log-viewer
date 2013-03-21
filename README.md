This plugin aims to provide a rest interface and an ui for reading ElasticSearch logs.

## How To Install

bin/plugin -url https://bitbucket.org/ferhat/elasticsearch-log-viewer/downloads/log-viewer-1.0.zip -install log-viewer

However that not works as expected, you will have result '/plugins/log-viewer/log-viewer-1.0'.
You should move contents of log-viewer-1.0 to upper folder log-viewer.

## Rest Interfaces

http://localhost:9200/_logviewer/logs => lists log files of current node

http://localhost:9200/_logviewer/x.log => gets last line of x

http://localhost:9200/_logviewer/x.log?line=10 => gets last 10 lines of x

http://localhost:9200/_logviewer/x.log?type=more => gets first line of x (default type is 'tail')

http://localhost:9200/_logviewer/x.log?type=more&line=10 => gets first 10 lines of x


## UI

http://localhost:9200/_plugin/log-viewer/

![sh](https://github.com/ferhatsb/elasticsearch-log-viewer/blob/master/sh.png)

