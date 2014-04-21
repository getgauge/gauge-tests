#!/bin/sh

export PATH=$GAUGE_ROOT:$PATH
twist2 --env ci specs/

