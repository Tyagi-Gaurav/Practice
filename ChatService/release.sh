#!/bin/sh

tag=`git rev-parse --short=5   HEAD`
git tag v1.0-$tag