package gt.practice.linesInDirectory;

public class Attributes {
        private int comments;
        private int blankLines;
        private int importLine;
        private int codeLine;

        public Attributes(int comments, int blankLines, int importLine, int codeLine) {
            this.comments = comments;
            this.blankLines = blankLines;
            this.importLine = importLine;
            this.codeLine = codeLine;
        }

        public int getCodeLine() {
            return codeLine;
        }

        public int getComments() {
            return comments;
        }

        public int getBlankLines() {
            return blankLines;
        }

        public int getImportLine() {
            return importLine;
        }

        @Override
        public String toString() {
            return "Attributes{" +
                    "comments=" + comments +
                    ", blankLines=" + blankLines +
                    ", importLine=" + importLine +
                    ", codeLine=" + codeLine +
                    '}';
        }
    }