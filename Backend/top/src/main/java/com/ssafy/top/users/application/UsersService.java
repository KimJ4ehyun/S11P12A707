package com.ssafy.top.users.application;

import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.global.exception.ErrorCode;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.users.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.top.global.exception.ErrorCode.WHITESPACE_NOT_ALLOWED;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<UsersResponse> getUsersByLoginId(Long userId, String nickname) {
        String trimmedNickname = nickname.replace(" ", "");

        if(trimmedNickname.isEmpty()) {
            throw new CustomException(WHITESPACE_NOT_ALLOWED);
        }

        return usersRepository.findUsersByNickname(userId, trimmedNickname)
                .stream()
                .map(UsersResponse::new)
                .collect(Collectors.toList());
    }
}
