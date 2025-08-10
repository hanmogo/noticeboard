package hanmogo.noticeboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 구현체입니다.
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안 설정을 위한 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (Stateless API의 경우)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/**").permitAll() // 우선 모든 요청을 허용하도록 설정
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    //로그인, 로그아웃
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //csrf 보호 비활성화
                .csrf(csrf -> csrf.disable())

                //HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        //지정된 경로에 대해 모든 사용자의 접근을 허용
                        .requestMatchers("/", "/login", "/join", "/api/posts", "/css/**", "/js/**").permitAll()
                        //나머지 모든 요청은 인증된 사용자만 접근 허용
                        .anyRequest().authenticated() //
                )

                //로그아웃 기능 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}