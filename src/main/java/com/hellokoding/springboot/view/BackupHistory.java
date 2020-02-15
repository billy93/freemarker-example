package com.hellokoding.springboot.view;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "backup_history")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BackupHistory  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdOn;
 
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedOn;
}
