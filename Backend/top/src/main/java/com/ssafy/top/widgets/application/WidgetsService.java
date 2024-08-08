package com.ssafy.top.widgets.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.widgets.domain.Widgets;
import com.ssafy.top.widgets.domain.WidgetsRepository;
import com.ssafy.top.widgets.dto.WidgetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.top.global.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class WidgetsService {
    private final UsersRepository usersRepository;
    private final WidgetsRepository widgetsRepository;

    public CommonResponseDto<?> saveWidgets(Long userId, List<WidgetsDto> widgets) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        widgetsRepository.deleteAllByUserId(userId);

        List<Widgets> saveWidgets = widgets
                .stream()
                .map(widgetDto -> widgetDto.toEntity(user))
                .collect(Collectors.toList());

        widgetsRepository.saveAll(saveWidgets);

        return new CommonResponseDto<>("위젯 저장에 성공했습니다.", 201);
    }
}
