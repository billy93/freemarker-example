package com.hellokoding.springboot.view;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
 
public interface BackupHistoryRepository extends PagingAndSortingRepository<BackupHistory, Long>, 
        JpaSpecificationExecutor<Note> {
}