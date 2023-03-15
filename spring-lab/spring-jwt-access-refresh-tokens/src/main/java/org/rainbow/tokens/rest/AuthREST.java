package org.rainbow.tokens.rest;

import org.rainbow.tokens.document.RefreshToken;
import org.rainbow.tokens.document.User;
import org.rainbow.tokens.dto.LoginDTO;
import org.rainbow.tokens.dto.SignupDTO;
import org.rainbow.tokens.dto.TokenDTO;
import org.rainbow.tokens.jwt.JwtHelper;
import org.rainbow.tokens.repository.RefreshTokenRepository;
import org.rainbow.tokens.repository.UserRepository;
import org.rainbow.tokens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * @author K
 */
@RestController
@RequestMapping("/api/auth")
public class AuthREST {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  RefreshTokenRepository refreshTokenRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  JwtHelper jwtHelper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserService userService;


  @PostMapping("signup")
  @Transactional
  public ResponseEntity<?> signup(@Valid @RequestBody SignupDTO dto) {
    User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
    userRepository.save(user);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setOwner(user);
    refreshTokenRepository.save(refreshToken);

    String accessToken = jwtHelper.generateAccessToken(user);
    String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);
    return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString));
  }

  @PostMapping("login")
  @Transactional
  public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
    Authentication authenticate = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    User user = (User) authenticate.getPrincipal();

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setOwner(user);
    refreshTokenRepository.save(refreshToken);

    String accessToken = jwtHelper.generateAccessToken(user);
    String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);
    return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString));
  }

  @PostMapping("logout")
  public ResponseEntity<?> logout(@RequestBody TokenDTO dto) {
    String refreshTokenString = dto.getRefreshToken();
    if (jwtHelper.validateRefreshToken(refreshTokenString)
        && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
      refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
      return ResponseEntity.ok().build();
    }
    throw new BadCredentialsException("invalid token");
  }

  @PostMapping("logout-all")
  public ResponseEntity<?> logoutAll(@RequestBody TokenDTO dto) {
    String refreshTokenString = dto.getRefreshToken();
    if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
      // valid and exists in db

      refreshTokenRepository.deleteByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
      return ResponseEntity.ok().build();
    }

    throw new BadCredentialsException("invalid token");
  }

  @PostMapping("access-token")
  public ResponseEntity<?> accessToken(@RequestBody TokenDTO dto) {
    String refreshTokenString = dto.getRefreshToken();
    if (jwtHelper.validateRefreshToken(refreshTokenString)
        && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {

      User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
      String accessToken = jwtHelper.generateAccessToken(user);

      return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString));
    }
    throw new BadCredentialsException("invalid token");
  }

  @PostMapping("refresh-token")
  public ResponseEntity<?> refreshToken(@RequestBody TokenDTO dto) {
    String refreshTokenString = dto.getRefreshToken();
    if (jwtHelper.validateRefreshToken(refreshTokenString)
        && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {

      refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
      User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));

      RefreshToken refreshToken = new RefreshToken();
      refreshToken.setOwner(user);
      refreshTokenRepository.save(refreshToken);

      String accessToken = jwtHelper.generateAccessToken(user);
      String newRefreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

      return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, newRefreshTokenString));
    }
    throw new BadCredentialsException("invalid token");
  }
}
