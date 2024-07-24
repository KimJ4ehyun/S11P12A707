package com.ssafy.top.users.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.users.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.top.global.exception.ErrorCode.WHITESPACE_NOT_ALLOWED;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public CommonResponseDto<List<UsersResponse>> getUsersByLoginId(Long userId, String nickname) {
        String trimmedNickname = nickname.replace(" ", "");

        if(trimmedNickname.isEmpty()) {
            throw new CustomException(WHITESPACE_NOT_ALLOWED);
        }

        List<UsersResponse> result = usersRepository.findUsersByNickname(userId, trimmedNickname)
                .stream()
                .map(UsersResponse::new)
                .toList();

        return new CommonResponseDto<>(result, "유저 조회에 성공했습니다.", 200);
    }
}
