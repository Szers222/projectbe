package tdc.edu.vn.project_mobile_be.scheduledtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;

import java.time.LocalDate;

@Component
@Transactional
public class ScheduledTasks {

    @Autowired
    private CategoryRepository categoryRepository;

    @Scheduled(fixedRate = 86400000)

    public void deleteExpiredCategories() {
        LocalDate now = LocalDate.now();
        categoryRepository.deleteByStatusAndDeletionDate(2, now);
    }
}
