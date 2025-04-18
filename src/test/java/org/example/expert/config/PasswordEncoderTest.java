package org.example.expert.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @Test
    void matches_메서드가_정상적으로_동작한다() {
        // given 준비
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // when 실행
        // 기존 코드 : boolean matches = passwordEncoder.matches(encodedPassword, rawPassword);
        // 실행시 Expected :true, Actual :false => matches 매개변수의 순서를 잘못 넣었기 때문 !
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword); // BUILD SUCCESSFUL in 3s

        // then 검증
        assertTrue(matches);
    }
}
