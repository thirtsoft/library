package com.library.security;

import com.library.security.jwt.JwtAuthEntryPoint;
import com.library.security.jwt.JwtAuthTokenFilter;
import com.library.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/**/auth/signIn").permitAll()
                .antMatchers("/**/auth/signUp").permitAll()
                .antMatchers("/alAmine/utilisateurs/authorities").permitAll()
                .antMatchers("/alAmine/photoUser/{id}").permitAll()
                .antMatchers("/alAmine/uploadFilePdf/{id}").permitAll()
                .antMatchers("/**/contrats/downloadContratFile/**").permitAll()
                .antMatchers("/alAmine/uploadPdfFile/{id}").permitAll()
                .antMatchers("/**/versements/downloadVersementFile/**").permitAll()
                //.antMatchers("/**/categories/all").permitAll()
                //  .antMatchers("/**/scategories/all").permitAll()
                //    .antMatchers("/**/produits/all").permitAll()
                //      .antMatchers("/**/utilisateurs/allUtilisateurOrderDesc").permitAll()
                //      .antMatchers("/**/ventes/allVenteOf3LatestMonths").permitAll()
                //      .antMatchers("/**/creances/allCreanceOf3LatestMonths").permitAll()
                //      .antMatchers("/**/approvisionnements/allApprovisionnementOfLatest3Months").permitAll()
                //      .antMatchers("/**/commandes/allCommandeClientOf3LatestMonths").permitAll()


                .antMatchers("/**/utilisateurs/avatar/{id}").permitAll()
                //        .antMatchers("/**/utilisateurs/activatedUser/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //  .allowedOrigins("**")
                       //   .allowedOrigins("http://localhost:4200")
                       // .allowedOrigins("http://localhost:8080/E-Librairie")
                         .allowedOrigins("https://alamine.herokuapp.com")
                       // .allowedOrigins("https://librairiealamine.com")
                        //   .allowedMethods("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .maxAge(3600L)
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);


            }
        };
    }

}
