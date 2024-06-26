package com.microservices.security.springconfigwithdbandchainoffilters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdDate;
    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime lastModifiedDate;
}
