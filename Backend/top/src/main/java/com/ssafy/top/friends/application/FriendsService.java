package com.ssafy.top.friends.application;

import com.ssafy.top.friends.domain.FriendsRepository;
import com.ssafy.top.friends.dto.response.FriendsResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendsService {
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public CommonResponseDto<List<FriendsResponse>> getFriends(Long userId) {
        List<FriendsResponse> result = usersRepository.findUsersByUserId(userId)
                .stream()
                .map(FriendsResponse::toDto)
                .toList();

        return new CommonResponseDto<>(result, "친구 목록 조회에 성공했습니다.", 200);
    }
}
