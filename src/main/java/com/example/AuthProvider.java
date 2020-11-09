package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import io.reactivex.Maybe;
import org.reactivestreams.Publisher;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class AuthProvider implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Maybe.<AuthenticationResponse>create(emitter -> {
            // TODO: Check user credentials
            if (authenticationRequest.getIdentity().equals("salah") &&
                    authenticationRequest.getSecret().equals("123")) {
                emitter.onSuccess(new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }).toFlowable();
    }

    @Singleton
    static class InMemoryRefreshTokenPersistence implements RefreshTokenPersistence {

        private final Map<String, UserDetails> store = new ConcurrentHashMap<>();

        @Override
        public void persistToken(RefreshTokenGeneratedEvent event) {
            store.put(event.getRefreshToken(), event.getUserDetails());
        }

        @Override
        public Publisher<UserDetails> getUserDetails(String refreshToken) {
            return Maybe.<UserDetails>create(emitter -> {
                // TODO: Check if the token is revoked
                UserDetails userDetails = store.get(refreshToken);
                if (userDetails != null) {
                    emitter.onSuccess(userDetails);
                } else {
                    emitter.onError(new AuthenticationException("refresh token not found or revoked"));
                }
            }).toFlowable();
        }
    }
}
