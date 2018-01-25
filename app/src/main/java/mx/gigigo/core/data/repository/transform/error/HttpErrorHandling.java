package mx.gigigo.core.data.repository.transform.error;

/**
 * @author Juan Godínez Vera - 5/9/2017.
 * @version 0.0.1
 * @since 0.0.1
 */
public enum HttpErrorHandling {
    HTTP_DEFAULT(0) {
        @Override
        public String toString() {
            return "The specific HTTP request has been interrupted";
        }
    },
    HTTP_BAD_REQUEST(400) {
        @Override
        public String toString() {
            return "Bad Request - The server could not understand the request due to invalid syntax.";
        }
    },
    HTTP_UNAUTHORIZED(401) {
        @Override
        public String toString() {
            return "Unauthorized - Authentication is needed to get requested response.";
        }
    },
    HTTP_FORBIDDEN(403) {
        @Override
        public String toString() {
            return "Forbidden - Client does not have access rights to the content so server is rejecting to give proper response.";
        }
    },
    HTTP_NOT_FOUND(404) {
        @Override
        public String toString() {
            return "Not Found - Server can not find requested resource.";
        }
    },
    HTTP_METHOD_NOT_ALLOWED(405) {
        @Override
        public String toString() {
            return "Method Not Allowed - The request method is known by the server but has been disabled and cannot be used.";
        }
    },
    HTTP_BAD_GATEWAY(502) {
        @Override
        public String toString() {
            return "Bad Gateway - The server was acting as a gateway or proxy and received an invalid response from the upstream server.";
        }
    };

    private final int value;

    HttpErrorHandling(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static HttpErrorHandling fromInt(int value) {
        for (HttpErrorHandling code : HttpErrorHandling.values()) {
            if (code.getValue() == value) {
                return code;
            }
        }

        return HttpErrorHandling.HTTP_DEFAULT;
    }
}
