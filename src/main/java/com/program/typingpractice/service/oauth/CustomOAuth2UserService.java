package com.program.typingpractice.service.oauth;

import com.program.typingpractice.domain.user.User;
import com.program.typingpractice.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                userRequest.getAdditionalParameters(),
                "sub"
        );

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        if(existingUser.isEmpty()){
            System.out.println("새 사용자 등록: " + email);
            user = User.builder()
                    .email(email)
                    .username(name)
                    .password(null)
                    .roles(Set.of("USER"))
                    .build();
            userRepository.save(user);
        } else {
            user = existingUser.get();
            System.out.println("기존 사용자 로그인: " + user.getEmail());
        }

        session.setAttribute("user", user);

        return oAuth2User;
    }
}
