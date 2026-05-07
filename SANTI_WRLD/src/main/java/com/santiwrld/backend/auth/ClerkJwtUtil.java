package com.santiwrld.backend.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.crypto.RSASSAVerifier;

import java.net.URL;
import java.security.interfaces.RSAKey;
import java.util.Date;

@Component
public class ClerkJwtUtil {

    @Value("${clerk.jwks-url}")
    private String jwkUrl;

    @Value("${clerk.issuer}")
    private String issuer;

    private JwKSet jwKSet;

    @PostConstruct
    public void loadkey() throws Exception {
        jwKSet = JwkSet.load(new URL(jwkUrl));
    }

    public JWTClaimsSet validateToken(String token) {
        try {
            // Parse the token — doesn't verify yet, just reads the structure
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Read the key ID from the token header —
            // Clerk uses this to tell you which public key to verify with
            String kid = signedJWT.getHeader().getKeyID();

            // Look up the matching public key from the cached JWKS
            RSAKey rsaKey = (RSAKey) jwkSet.getKeyByKeyId(kid);

            if (rsaKey == null) {
                throw new RuntimeException("No matching public key found for kid: " + kid);
            }

            // Build the verifier using the RSA public key
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());

            // Verify the signature — returns false if tampered with
            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("JWT signature verification failed");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Check the token hasn't expired
            if (claims.getExpirationTime().before(new Date())) {
                throw new RuntimeException("JWT token has expired");
            }

            // Check the token came from Clerk and not somewhere else
            if (!issuer.equals(claims.getIssuer())) {
                throw new RuntimeException("Invalid issuer: " + claims.getIssuer());
            }

            return claims;

        } catch (RuntimeException e) {
            // Re-throw runtime exceptions as-is
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate Clerk token: " + e.getMessage());
        }

    }



}
