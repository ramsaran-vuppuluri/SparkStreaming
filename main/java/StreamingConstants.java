public class StreamingConstants {
    public enum ReadStreamFormat {
        SOCKET("socket"),
        JSON("json");

        private String format;

        ReadStreamFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    public enum WriteStreamFormat {
        CONSOLE("console");

        private String format;

        WriteStreamFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }
}