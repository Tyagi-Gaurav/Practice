package org.gt.chat.main.util;

import akka.http.javadsl.model.headers.ModeledCustomHeader;

public class StringBasedHeader extends ModeledCustomHeader {

        public StringBasedHeader(String name, String value) {
            super(name, value);
        }

        @Override
        public boolean renderInRequests() {
            return false;
        }

        @Override
        public boolean renderInResponses() {
            return false;
        }
    }