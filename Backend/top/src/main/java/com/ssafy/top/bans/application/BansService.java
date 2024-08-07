package com.ssafy.top.bans.application;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.bans.domain.BansRepository;
import com.ssafy.top.bans.dto.request.BanAddRequest;
import com.ssafy.top.bans.dto.response.AppNameAndTimeResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BansService {
    private final UsersRepository usersRepository;
    private final BansRepository bansRepository;

    public CommonResponseDto<List<String>> addBan(Long userId, BanAddRequest banAddRequest) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 소문자로 변환 + 공백 삭제
        String name = banAddRequest.getName().toLowerCase().replace(" ", "");

        // URL or App
        if(name.contains(".")) { // URL
            if(!isValidDomain(name)) {
                throw new CustomException(INVALID_DOMAIN);
            }
        }

        // 이미 사용자가 등록한 경우
        Optional<Bans> ban = bansRepository.findByUserIdAndName(userId, name);
        if(ban.isPresent()) {
            throw new CustomException(BAN_ALREADY_ADDED);
        }

        // 등록
        bansRepository.save(banAddRequest.toEntity(user));

        List<String> result = bansRepository.findByUser(userId);

        return new CommonResponseDto<>(result, "금지 목록에 추가되었습니다.", 201);
    }

    public CommonResponseDto<?> findBanListByUserId(Long userId){
        List<Object[]> banList = bansRepository.findBanListByUserId(userId);
        AppNameAndTimeResponse[] appNameAndTimeResponses = banList.stream()
                .map(result -> new AppNameAndTimeResponse((String) result[0], ((Long) result[1])))
                .toArray(AppNameAndTimeResponse[]::new);
        return new CommonResponseDto<>(appNameAndTimeResponses, "금지 목록 프로그램 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> updateIsBan(Long userId, BanAddRequest banDeleteRequest){
        String name = banDeleteRequest.getName();

        Optional<Bans> optionalBan = bansRepository.findByUserIdAndName(userId, name);
        if(optionalBan.isEmpty()) {
            throw new CustomException(DATA_NOT_FOUND);
        }

        Bans ban = optionalBan.get();
        ban.updateIsBan(false);
        return new CommonResponseDto<>(bansRepository.save(ban).getName(), "금지 목록 프로그램 삭제에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> deleteBan(Long userId, BanAddRequest banDeleteRequest) {
        String name = banDeleteRequest.getName();

        Optional<Bans> ban = bansRepository.findByUserIdAndName(userId, name);
        if(ban.isEmpty()) {
            throw new CustomException(DATA_NOT_FOUND);
        }

        Bans existingBan = ban.get();
        bansRepository.delete(existingBan);

        return new CommonResponseDto<>("금지 목록 프로그램 삭제에 성공했습니다.", 204);
    }

    private boolean isValidDomain(String domain) {
        // 도메인 이름의 정규 표현식
        String DOMAIN_REGEX = "^(?!-)[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\\.[A-Za-z]{2,})+$";

        // 정규 표현식 패턴 컴파일
        Pattern DOMAIN_PATTERN = Pattern.compile(DOMAIN_REGEX);

        // 각 항목의 길이 체크
        String[] parts = domain.split("\\.");
        for (String part : parts) {
            if (part.length() < 2 || part.length() > 63) {
                return false;
            }
        }

        // 정규 표현식으로 도메인 체크
        return DOMAIN_PATTERN.matcher(domain).matches();
    }
}
