#!/bin/sh

export PATH=$GAUGE_ROOT/bin:$PATH
gauge --env ci specs/

