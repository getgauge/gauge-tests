#!/bin/sh
export PATH=$GAUGE_ROOT/bin:$PATH
mvn clean install -Denv=ci-$1 -Dtags="$TAGS"