#! /bin/bash
#this script will be replaced once the launcher is working
CURRDIR=$( pwd )
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Node installation is located at " $DIR
cd $DIR
#
# The following argument won't be invoked sice doing so prevents SWT from loading.
# A different solution needs to be found.
# -splash:./rsc/Splashscreen.png
#
java -jar -Xmx2G -XstartOnFirstThread ./bin/engine.jar $DIR "$@"
cd $CURRDIR
