package com.ssafy.top.bans.application;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.bans.domain.BansRepository;
import com.ssafy.top.bans.dto.request.BanRequest;
import com.ssafy.top.bans.dto.response.AppNameAndTimeResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BansService {
    private final UsersRepository usersRepository;
    private final BansRepository bansRepository;

    public CommonResponseDto<List<String>> addBan(String email, BanRequest banRequest) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String name = banRequest.getName();

        // 이미 사용자가 등록한 경우
        Optional<Bans> ban = bansRepository.findByUserIdAndName(user.getId(), name);
        if(ban.isPresent()) {
            throw new CustomException(BAN_ALREADY_ADDED);
        }

        // 등록
        bansRepository.save(banRequest.toEntity(user));

        return new CommonResponseDto<>("금지 목록에 추가되었습니다.", 201);
    }

    public CommonResponseDto<?> findBanListByUserId(String email){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<Object[]> banList = bansRepository.findBanListByUserIdAndDateData(user.getId(), now);

        AppNameAndTimeResponse[] appNameAndTimeResponses = banList.stream()
                .map(result -> new AppNameAndTimeResponse((String) result[0], ((Long) result[1])))
                .toArray(AppNameAndTimeResponse[]::new);

        return new CommonResponseDto<>(appNameAndTimeResponses, "금지 목록 프로그램 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> updateIsBan(String email, BanRequest banDeleteRequest){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String name = banDeleteRequest.getName();

        Optional<Bans> optionalBan = bansRepository.findByUserIdAndName(user.getId(), name);
        if(optionalBan.isEmpty()) {
            throw new CustomException(DATA_NOT_FOUND);
        }

        Bans ban = optionalBan.get();
        ban.updateIsBan(false);
        return new CommonResponseDto<>(bansRepository.save(ban).getName(), "금지 목록 프로그램 삭제에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> deleteBan(String email, BanRequest banDeleteRequest) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String name = banDeleteRequest.getName();

        Optional<Bans> ban = bansRepository.findByUserIdAndName(user.getId(), name);
        if(ban.isEmpty()) {
            throw new CustomException(DATA_NOT_FOUND);
        }

        Bans existingBan = ban.get();
        bansRepository.delete(existingBan);

        return new CommonResponseDto<>("금지 목록 프로그램 삭제에 성공했습니다.", 204);
    }
}
