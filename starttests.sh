#!/bin/sh
ant
export PATH=$GAUGE_ROOT/bin:$PATH
gauge --env ci-$1 --simple-console --sort --parallel specs/

