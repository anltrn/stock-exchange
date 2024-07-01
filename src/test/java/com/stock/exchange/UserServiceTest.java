package com.stock.exchange;

import com.stock.exchange.dao.UserRepository;
import com.stock.exchange.entity.User;
import com.stock.exchange.exception.CustomException;
import com.stock.exchange.security.jwt.JwtTokenProvider;
import com.stock.exchange.security.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should sign in successfully")
    public void testSignIn() {
        String username = "user";
        String password = "password";
        Authentication auth = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtTokenProvider.generateJwtToken(auth)).thenReturn("jwtToken");

        String result = userService.signIn(username, password);

        assertEquals("jwtToken", result);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtTokenProvider, times(1)).generateJwtToken(auth);
    }

    @Test
    @DisplayName("Should throw CustomException when sign in with invalid credentials")
    public void testSignIn_WithInvalidCredentialsThrowsException() {
        String username = "user";
        String password = "password";

        when(authenticationManager.authenticate(any())).thenThrow(new CustomException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED));

        assertThrows(CustomException.class, () -> userService.signIn(username, password));
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Should sign up successfully")
    public void testSignUp() {
        String username = "user";
        String email = "email";
        String password = "password";

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        userService.signUp(username, email, password);

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByUsername(username);
        verify(userRepository, times(1)).existsByEmail(email);
        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    @DisplayName("Should throw CustomException when sign up with existing username")
    public void testSignUp_WithExistingUsernameThrowsException() {
        String username = "user";
        String email = "email";
        String password = "password";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.signUp(username, email, password));
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    @DisplayName("Should throw CustomException when sign up with existing email")
    public void testSignUp_WithExistingEmailThrowsException() {
        String username = "user";
        String email = "email";
        String password = "password";

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.signUp(username, email, password));
        verify(userRepository, times(1)).existsByUsername(username);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("Should delete existing user")
    public void testDeleteExistingUser() {
        String username = "user";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.delete(username);

        verify(userRepository, times(1)).delete(user);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existing user")
    public void testDelete_NonExistingUserThrowsException() {
        String username = "user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(username));
        verify(userRepository, times(1)).findByUsername(username);
    }
}