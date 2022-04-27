# spring-boot-starter-simple-jwt


spring-boot-starter-simple-jwt is a sample project for showcasing Spring Security capabilities. It's using spring-boot-starter-oauth2-resource-server for checking the bearer token in the requests and spring-security-oauth2-jose for issuing tokens after login.

## Usage

Only thing you can configure is the JWT related settings.

```properties
spring.security.oauth2.resourceserver.jwt.secret-key=_iVjXdyWVXV19m6cEvPkJ_PH_vouiyUgqSW5-1icIhLVoo1pORFBIaQLA0dIJJzSB2OX-ZTaTSxu_ge2EtkFGQToNIFw94oK72lBL_8oEZ8E6RNil837g1aU1M1S-3aPxeRNxziXy6oU-xw9Y0hy7ay-tINmAQ640gbht1v-bH0
spring.security.oauth2.resourceserver.jwt.algorithm=HMACSHA256

spring.security.oauth2.authorizationserver.jwt.access-token.expiration-in-minutes=30
spring.security.oauth2.authorizationserver.jwt.refresh-token.expiration-in-minutes=43200
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
