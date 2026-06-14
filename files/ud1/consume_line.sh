#!/bin/bash

FILENAME=$1
i=0
DELAY=1

if [ $# -gt 1 ]; then
    DELAY=$2
fi

cat $FILENAME | while read line
do
    echo "Process $$ - Line $i: $line"
    ((i++))
    sleep $DELAY
done