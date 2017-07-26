#!/bin/sh
export PATH=$GAUGE_ROOT/bin:$PATH
mvn clean -q install -Denv=ci-$1 -Dtags="$TAGS"
