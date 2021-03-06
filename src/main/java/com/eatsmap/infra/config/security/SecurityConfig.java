package com.eatsmap.infra.config.security;

import com.eatsmap.infra.config.security.filter.JwtAuthFilter;
import com.eatsmap.infra.config.security.provider.JwtAuthProvider;
import com.eatsmap.infra.config.security.setting.NoRedirectStrategy;
import com.eatsmap.infra.jwt.JwtUtil;
import com.eatsmap.module.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthProvider jwtAuthProvider;
    private final JwtUtil jwtUtil;

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;


    //    Permit ALL URL
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            PathRequest.toStaticResources().atCommonLocations(),
            new AntPathRequestMatcher("/api/v1/account/login"),
            new AntPathRequestMatcher("/api/v1/account/sign-up"),
            new AntPathRequestMatcher("/api/v1/account/verify-email")
    );


//    Need Authentication URL
    private static final RequestMatcher ROLE_USER_REQUIRED = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/v1/account/profile"),
            new AntPathRequestMatcher("/api/v1/review/create"),
            new AntPathRequestMatcher("/api/v1/review/update"),
            new AntPathRequestMatcher("/api/v1/review/delete"),
            new AntPathRequestMatcher("/api/v1/review/find/allReviews"),
            new AntPathRequestMatcher("/api/v1/review/timeline"),
            new AntPathRequestMatcher("/api/v1/account/member"),
            new AntPathRequestMatcher("/api/v1/account/logout"),
            new AntPathRequestMatcher("/api/v1/group/create"),
            new AntPathRequestMatcher("/api/v1/group/all"),
            new AntPathRequestMatcher("/api/v1/group/groups"),
            new AntPathRequestMatcher("/api/v1/group/my/groups"),
            new AntPathRequestMatcher("/api/v1/group/join/{groupId}"),
            new AntPathRequestMatcher("/api/v1/calendar/schedule/create"),
            new AntPathRequestMatcher("/api/v1/follow/{toMemberId}"),
            new AntPathRequestMatcher("/api/v1/follow/cancel/{toMemberId}"),
            new AntPathRequestMatcher("/api/v1/follow/followings"),
            new AntPathRequestMatcher("/api/v1/follow/followers"),
            new AntPathRequestMatcher("/api/v1/calendar/schedule/create"),
            new AntPathRequestMatcher("/api/v1/calendar/schedule/modify"),
            new AntPathRequestMatcher("/api/v1/calendar/schedule/delete/{idx}"),
            new AntPathRequestMatcher("/api/v1/calendar/")

    );

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            ROLE_USER_REQUIRED
    );

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  // ????????? ??? ??????????????? ??????
                .csrf().disable()       // rest ?????? ????????? ???????????? ??????
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // http - https ?????? ?????? ????????? ??????????????? ??????.

        // ?????? : ????????? ?????? TokenAuthFilter?????? ??????
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http
                .authorizeRequests()
                .requestMatchers(ROLE_USER_REQUIRED).hasAnyAuthority(MemberRole.USER.name())
                .anyRequest().permitAll();

        http
                .authenticationProvider(jwtAuthProvider)
                .addFilterBefore(restJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    JwtAuthFilter restJwtAuthFilter() throws Exception {
        final JwtAuthFilter filter = new JwtAuthFilter(PROTECTED_URLS, jwtUtil);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(authSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler authSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    public FilterRegistrationBean disableAutoFilterRegister(final JwtAuthFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
