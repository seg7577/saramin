package com.example.saramin.entity.dto.JobPost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "채용 공고 조회를 위한 폼")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobsRequestForm {
    @Schema(description = "현재 페이지 번호", example = "1")
    Integer currentPage;

    @Schema(description = "정렬 기준: newest(최신순) 혹은 alphabetical(가나다순)", example = "newest")
    String sortBy;

    @Schema(description = "지역 필터(null일 경우 필터링하지 않음)", example = "서울전체")
    String workplace;

    @Schema(description = "경력 필터(null일 경우 필터링하지 않음) 예시의 경우 경력 5년 이하를 의미", example = "5")
    Integer career;

    @Schema(description = "기술 스택 필터(null일 경우 필터링하지 않음) 그누보드, 라즈베리파이 등", example = "라즈베리파이")
    String skillStack;

    @Schema(description = "검색 방식(null일 경우 검색하지 않음) title(제목) 혹은 companyName(회사명)", example = "title")
    String searchBy;

    @Schema(description = "검색에 사용할 키워드", example = "개발")
    String keyword;
}
