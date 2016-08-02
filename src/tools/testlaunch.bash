#! /bin/bash
#this script will be replaced once the launcher is working
CURRDIR=$( pwd )
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Node installation is located at " $DIR
cd $DIR
java -jar -XstartOnFirstThread ./bin/engine.jar $DIR
cd $CURRDIR
