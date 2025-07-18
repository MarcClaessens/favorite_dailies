#!/bin/sh
set -x
export JAVA_HOME=/home/marc/.sdkman/candidates/java/current
export PATH=${JAVA_HOME}/bin:$PATH
cd /BigOne/extdata/git_repos/favorite_dailies/target
nohup java -jar `ls -1 favorite_dailies*.jar` > ../log.txt 2>&1 &
