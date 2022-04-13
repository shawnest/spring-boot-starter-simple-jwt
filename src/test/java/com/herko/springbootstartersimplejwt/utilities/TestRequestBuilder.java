package com.herko.springbootstartersimplejwt.utilities;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestRequestBuilder {
    /**
     * {
     *   "alg": "HS256",
     *   "typ": "JWT"
     * }
     * {
     *   "sub": "1234567890",
     *   "name": "John Doe",
     *   "iat": 1516239022,
     *   "exp": 1743244827
     * }
     * HMACSHA256(
     *   base64UrlEncode(header) + "." +
     *   base64UrlEncode(payload),
     *   (jwt secret key) _iVjXdyWVXV19m6cEvPkJ_PH_vouiyUgqSW5-1icIhLVoo1pORFBIaQLA0dIJJzSB2OX-ZTaTSxu_ge2EtkFGQToNIFw94oK72lBL_8oEZ8E6RNil837g1aU1M1S-3aPxeRNxziXy6oU-xw9Y0hy7ay-tINmAQ640gbht1v-bH0
     */
    public static final String VALID_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE3NDMyNDQ4Mjd9.T4YYk8ifLj3zz_jYABedIQukHUqYARtUKVF_gGNF8gc";

    public static MockHttpServletRequestBuilder buildGetJsonRequest(final String endpoint) {
        return MockMvcRequestBuilders.get(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
