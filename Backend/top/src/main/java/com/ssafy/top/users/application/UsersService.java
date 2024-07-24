package com.ssafy.top.users.application;

import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.users.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<UsersResponse> getUsersByLoginId(Long userId, String nickname) {
        String trimmedNickname = nickname.replace(" ", "");

        if(trimmedNickname.isEmpty()) {
            throw new RuntimeException("검색어를 입력하세요.");
        }

        return usersRepository.findUsersByNickname(userId, trimmedNickname)
                .stream()
                .map(UsersResponse::new)
                .collect(Collectors.toList());
    }
}
