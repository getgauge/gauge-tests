#!/bin/sh

export PATH=$GAUGE_ROOT/bin:$PATH
twist2 --env ci specs/

