#!/bin/sh

PROJECT_DIR='/Users/gauravt/workspace/Practice/ChatService'
CHAT_MAIN="$PROJECT_DIR/ChatMain"
CHAT_AUDIT="$PROJECT_DIR/ChatAudit"

$PROJECT_DIR/scripts/shutDownApp.sh

pushd $PROJECT_DIR

mvn clean install

provision() {
  DIRECTORY=$1
  pushd $DIRECTORY
  ./provisionApp.sh
  popd
}

provision $CHAT_MAIN
provision $CHAT_AUDIT

popd
