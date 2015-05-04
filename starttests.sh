#!/bin/sh
export PATH=$GAUGE_ROOT/bin:$PATH
mvn test -X -Denv=ci-$1

