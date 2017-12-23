#!/bin/sh

PROJECT_DIR='/Users/gauravt/workspace/Practice/ChatService'
CHAT_MAIN="$PROJECT_DIR/ChatMain'
CHAT_AUDIT="$PROJECT_DIR/ChatAudit'

pushd $PROJECT_DIR

mvn clean install

pushd $CHAT_MAIN
./provision.sh

popd
popd