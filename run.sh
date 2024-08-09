#!/bin/sh
set -x
# set JAVA_HOME to the first JRE we find in the eclipse plugins directory
ECLIPSE_PLUGINS=/BigOne/extdata/eclipse/plugins/
JAVA_HOME=`find $ECLIPSE_PLUGINS -type d -name jre`

cd /BigOne/extdata/git_repos/favorite_dailies/target
if [ ! -f java ]
then
	ln -s $JAVA_HOME/bin/java java
fi

nohup ./java -jar `ls -1 favorite_dailies*.jar` > ../log.txt 2>&1 &
#./java -jar `ls -1 favorite_dailies*.jar`
