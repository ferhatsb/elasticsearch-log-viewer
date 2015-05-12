This plugin aims to provide a rest interface and an ui for reading Elasticsearch logs.

## How To Install

bin/plugin -url https://github.com/ferhatsb/elasticsearch-log-viewer/releases/download/log-viewer-1.0/log-viewer-1.0.zip -install log-viewer

## Rest Interfaces

http://localhost:9200/_logviewer/logs => lists log files of current node

http://localhost:9200/_logviewer/x.log => gets last line of x

http://localhost:9200/_logviewer/x.log?line=10 => gets last 10 lines of x

http://localhost:9200/_logviewer/x.log?type=more => gets first line of x (default type is 'tail')

http://localhost:9200/_logviewer/x.log?type=more&line=10 => gets first 10 lines of x


## UI

http://localhost:9200/_plugin/log-viewer/

![sh](https://raw.github.com/ferhatsb/elasticsearch-log-viewer/master/sh.png)

