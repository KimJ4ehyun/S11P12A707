package com.ssafy.top.users.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.users.dto.request.UserUpdateRequest;
import com.ssafy.top.users.dto.response.UserResponse;
import com.ssafy.top.users.dto.response.UserUpdateResponse;
import com.ssafy.top.users.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ssafy.top.global.exception.ErrorCode.*;

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

    @Transactional(readOnly = true)
    public CommonResponseDto<UserResponse> getUser(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return new CommonResponseDto<>(UserResponse.toDto(user), "내 정보 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<UserUpdateResponse> updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        updateNickname(user, userUpdateRequest.getNickname());
        updateScreenShare(user, userUpdateRequest.getScreenShare());

        usersRepository.save(user);

        return new CommonResponseDto<>(UserUpdateResponse.toDto(user), "내 정보 수정에 성공했습니다.", 200);
    }

    public CommonResponseDto<Boolean> screenShare(Long userId, Boolean isShare) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        updateScreenShare(user, isShare);

        usersRepository.save(user);

        return new CommonResponseDto<>(isShare, "활성화 여부 수정에 성공했습니다.", 200);
    }

    private void updateNickname(Users user, String nickname) {
        if(nickname.contains(" ")) {
            throw new CustomException(WHITESPACE_NOT_ALLOWED);
        }

        Optional<Users> existingUser = usersRepository.findUserByNickname(nickname);
        if(existingUser.isPresent() && !user.getId().equals(existingUser.get().getId())) {
            throw new CustomException(NICKNAME_DUPLICATED);
        }

        user.updateNickname(nickname);
    }

    private void updateScreenShare(Users user, Boolean screenShare) {
        user.updateScreenShare(screenShare);
    }
}
