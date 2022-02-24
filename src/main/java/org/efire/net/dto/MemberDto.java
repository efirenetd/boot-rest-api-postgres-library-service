package org.efire.net.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.efire.net.domain.MemberStatus;

@Data
public class MemberDto {
    private String firstName;
    private String lastName;
    @JsonIgnore
    private MemberStatus status;
}
