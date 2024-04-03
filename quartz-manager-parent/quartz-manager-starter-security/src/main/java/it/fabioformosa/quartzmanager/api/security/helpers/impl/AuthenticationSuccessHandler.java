package it.fabioformosa.quartzmanager.api.security.helpers.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

  public AuthenticationSuccessHandler(JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
    super();
    this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
  }

  public String cookieMustBeDeletedAtLogout() {
    return jwtAuthenticationSuccessHandler.cookieMustBeDeletedAtLogout();
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    clearAuthenticationAttributes(request);
    jwtAuthenticationSuccessHandler.onLoginSuccess(authentication, response);
  }

}
