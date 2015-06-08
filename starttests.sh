#!/bin/sh
export PATH=$GAUGE_ROOT/bin:$PATH
gauge -p --env=ci-$1 specs/

