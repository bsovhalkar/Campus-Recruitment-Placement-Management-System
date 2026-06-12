package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.mapper.ShortlistingMapper;
import com.spring.placement_management_system.dto.response.ShortlistingDTO;
import com.spring.placement_management_system.entity.Application;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.exception.ShortlistingException;
import com.spring.placement_management_system.repository.ApplicationRepository;
import com.spring.placement_management_system.repository.JobRepository;
import com.spring.placement_management_system.service.ShortlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortlistingServiceImpl implements ShortlistService {

    /**
     * Example:
     * 10 vacancies -> 50 shortlisted
     */
    private static final int SHORTLIST_MULTIPLIER = 5;

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    private static final Comparator<Application> SHORTLIST_COMPARATOR =
            Comparator
                    .comparing(Application::getScore)
                    .reversed()
                    .thenComparing(
                            a -> a.getStudent().getCgpa(),
                            Comparator.reverseOrder()
                    )
                    .thenComparing(
                            a -> a.getStudent().getSkills() == null
                                    ? 0
                                    : a.getStudent().getSkills().size(),
                            Comparator.reverseOrder()
                    );

    @Override
    public List<ShortlistingDTO> generateShortlist(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(
                        () -> new ShortlistingException(
                                "Job not found"
                        )
                );

        List<Application> applications =
                applicationRepository
                        .findByJobJobIdAndStatus(
                                jobId,
                                StatusConstants.APPLIED
                        )
                        .orElseThrow(
                                () -> new ShortlistingException(
                                        "No applications found for this job"
                                )
                        );

        if (applications.isEmpty()) {
            throw new ShortlistingException(
                    "No applications found for this job"
            );
        }

        calculateScores(applications);

        applications.sort(SHORTLIST_COMPARATOR);

        int shortlistCount = Math.min(
                job.getVacancies() * SHORTLIST_MULTIPLIER,
                applications.size()
        );

        assignRanksAndStatuses(
                applications,
                shortlistCount
        );

        applicationRepository.saveAll(applications);

        return applications.stream()
                .filter(
                        application ->
                                application.getStatus()
                                        == StatusConstants.SHORTLISTED_FOR_TEST
                )
                .map(ShortlistingMapper::mapToDTO)
                .toList();
    }

    private void calculateScores(
            List<Application> applications
    ) {

        applications.forEach(
                application ->
                        application.setScore(
                                calculateFinalScore(
                                        application
                                )
                        )
        );
    }

    private void assignRanksAndStatuses(
            List<Application> applications,
            int shortlistCount
    ) {

        int rank = 1;

        for (int i = 0; i < applications.size(); i++) {

            Application application =
                    applications.get(i);

            application.setRank(rank++);
            application.setShortlistedAt(
                    LocalDateTime.now()
            );

            if (i < shortlistCount) {

                application.setStatus(
                        StatusConstants.SHORTLISTED_FOR_TEST
                );

            } else {

                /**
                 * Keep application under review.
                 * Do NOT auto reject.
                 */
                application.setStatus(
                        StatusConstants.UNDER_REVIEW
                );
            }
        }
    }

    private double calculateFinalScore(
            Application application
    ) {

        Student student =
                application.getStudent();

        double cgpaScore =
                student.getCgpa() * 10;

        int skillScore =
                student.getSkills() == null
                        ? 0
                        : student.getSkills().size() * 5;

        int resumeScore =
                student.getResume() != null
                        ? 20
                        : 0;

        return cgpaScore
                + skillScore
                + resumeScore;
    }

    @Override
    public List<ShortlistingDTO>
    getShortlistedStudents(Long jobId) {

        return applicationRepository
                .findByJobJobIdAndStatus(
                        jobId,
                        StatusConstants.SHORTLISTED_FOR_TEST
                )
                .orElseThrow(
                        () -> new ShortlistingException(
                                "No shortlisted students found"
                        )
                )
                .stream()
                .map(ShortlistingMapper::mapToDTO)
                .toList();
    }
}