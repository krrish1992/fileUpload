package com.fm.fileprocessor.exception;

    public class InvalidFileException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = 5407848424326359236L;

        public InvalidFileException(String message) {
            super(message);
        }

        public InvalidFileException(String message, Throwable cause) {
            super(message, cause);
        }
}
