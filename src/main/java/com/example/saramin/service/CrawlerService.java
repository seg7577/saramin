package com.example.saramin.service;

import com.example.saramin.entity.model.Company;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.repository.CompanyRepository;
import com.example.saramin.repository.JobPostRepository;
import com.example.saramin.util.CareerConverter;
import com.example.saramin.util.CrawlerValidator;
import com.example.saramin.util.DateParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerService {
    private static final String BASE_URL = "https://www.saramin.co.kr/zf_user/jobs/list/job-category?cat_kewd=%d&panel_type=&search_optional_item=n&search_done=y&panel_count=y&preview=y";
    private static final int MAX_JOBS_PER_KEY = 5;  //
    private static final int TOTAL_MAX_JOBS = 5;    //제출 전 수정 100개 이상
    private final CompanyRepository companyRepository;
    private final JobPostRepository jobPostRepository;
    private final CrawlerValidator crawlerValidator;

    @PostConstruct
    public void init() {
        log.info("크롤링 시작");
        try {
            crawlJobPosts();
        } catch (Exception e) {
            log.error("크롤링 중 오류 발생: {}", e.getMessage(), e);
        }
        log.info("크롤링 종료");
    }

    public void crawlJobPosts() {
        int totalJobCount = 0;

        for (int key = 185; key <= 318; key++) {
            if (totalJobCount >= TOTAL_MAX_JOBS) break;
            log.info("카테고리 키 처리 중: {}", key);

            String url = String.format(BASE_URL, key);
            addRandomDelay();

            try {
                Document doc = Jsoup.connect(url).get();

                String skillStack = doc.select(".wrap_title_recruit .value").text();
                log.info("기술 스택: {}", skillStack);

                Elements jobItems = doc.select(".list_recruiting .list_body .list_item");
                int jobCountForKey = 0;

                for (Element jobItem : jobItems) {
                    if (jobCountForKey >= MAX_JOBS_PER_KEY || totalJobCount >= TOTAL_MAX_JOBS) break;

                    String companyName = jobItem.select(".company_nm .str_tit").text();
                    log.info("회사 이름: {}", companyName);

                    String title = jobItem.select(".notification_info .job_tit .str_tit").text();
                    log.info("채용 공고 제목: {}", title);

                    String deadlineText = jobItem.select(".support_detail > .date").text();
                    Date deadlineDate = DateParser.parseDeadlineDate(deadlineText);
                    log.info("채용 공고 마감일: {}", deadlineDate);

                    String postDateText = jobItem.select(".support_detail > .deadlines").text();
                    Date postDate = DateParser.parsePostDate(postDateText);
                    log.info("채용 공고 게시일: {}", postDate);

                    String workPlace = jobItem.select(".recruit_info > ul > li p.work_place").text();
                    String career = jobItem.select(".recruit_info > ul > li p.career").text();
                    String education = jobItem.select(".recruit_info > ul > li p.education").text();

                    log.info("근무 장소: {}", workPlace);
                    log.info("경력: {}", career);
                    log.info("학력: {}", education);

                    if (crawlerValidator.isAnyFieldNull(companyName, title, workPlace, career, education, deadlineDate, postDate)) {
                        log.warn("null 값이 있어 건너 뜀");
                        continue;
                    }

                    Optional<Company> existingCompany = companyRepository.findByCompanyName(companyName);
                    Company company;
                    if (existingCompany.isPresent()) {
                        company = existingCompany.get();
                        log.info("중복 회사: {}", company.getCompanyName());
                    } else {
                        company = Company.builder()
                                .companyName(companyName)
                                .build();
                        companyRepository.save(company);
                        log.info("회사 저장: {}", company.getCompanyName());
                    }

                    if (!crawlerValidator.isJobPostValid(title, company) || !crawlerValidator.isJobPostUnique(title, company)) {
                        log.info("채용 공고가 중복되거나 유효하지 않습니다");
                        continue;
                    }

                    Integer careerMin = CareerConverter.extractMinCareer(career);
                    Integer careerMax = CareerConverter.extractMaxCareer(career);

                    JobPost jobPost = JobPost.builder()
                            .title(title)
                            .skillStack(skillStack)
                            .company(company)
                            .workPlace(workPlace)
                            .career(career)
                            .education(education)
                            .postDate(postDate)
                            .deadline(deadlineDate)
                            .careerMin(careerMin)
                            .careerMax(careerMax)
                            .build();

                    jobPostRepository.save(jobPost);
                    log.info("채용 공고 저장 완료: {}", jobPost.getTitle());

                    jobCountForKey++;
                    totalJobCount++;
                }
            } catch (IOException e) {
                log.error("키 {}에 대한 크롤링 중 오류 발생: {}", key, e.getMessage());
            }
        }
    }

    private void addRandomDelay() {
        try {
            int delay = ThreadLocalRandom.current().nextInt(1000, 3001);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("지연 중 오류 발생", e);
        }
    }
}
