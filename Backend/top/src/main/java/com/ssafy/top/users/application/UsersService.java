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
    public CommonResponseDto<List<UsersResponse>> getUsersByLoginId(String email, String nickname) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String trimmedNickname = nickname.replace(" ", "");

        if(trimmedNickname.isEmpty()) {
            throw new CustomException(WHITESPACE_NOT_ALLOWED);
        }

        List<UsersResponse> result = usersRepository.findUsersByNickname(user.getId(), trimmedNickname)
                .stream()
                .map(UsersResponse::new)
                .toList();

        return new CommonResponseDto<>(result, "유저 조회에 성공했습니다.", 200);
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<UserResponse> getUser(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return new CommonResponseDto<>(UserResponse.toDto(user), "내 정보 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<UserUpdateResponse> updateUser(String email, UserUpdateRequest userUpdateRequest) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        updateNickname(user, userUpdateRequest.getNickname());
        updateIsShare(user, userUpdateRequest.getIsShare());
        updateIsActive(user, userUpdateRequest.getIsActive());

        usersRepository.save(user);

        return new CommonResponseDto<>(UserUpdateResponse.toDto(user), "내 정보 수정에 성공했습니다.", 200);
    }

    public CommonResponseDto<Boolean> screenShare(String email, Boolean isShare) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        updateIsShare(user, isShare);

        usersRepository.save(user);

        return new CommonResponseDto<>(isShare, "활성화 여부 수정에 성공했습니다.", 200);
    }

    public CommonResponseDto<Boolean> extension(String email, Boolean isActive) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        updateIsActive(user, isActive);

        usersRepository.save(user);

        return new CommonResponseDto<>(isActive, "크롬 익스텐션 활성화 여부 수정에 성공했습니다.", 200);
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

    private void updateIsShare(Users user, Boolean isShare) {
        user.updateIsShare(isShare);
    }

    private void updateIsActive(Users user, Boolean isActive) {
        user.updateIsActive(isActive);
    }
}
