package org.efire.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdDateTime;
    @Column
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedDateTime;
    @Column
    @Version
    @JsonIgnore
    private Long version;

}
