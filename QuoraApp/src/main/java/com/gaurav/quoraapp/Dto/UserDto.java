package com.gaurav.quoraapp.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaurav.quoraapp.model.Expertise;
import com.gaurav.quoraapp.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;

    private String email;
    private List< Roles > roles;
    private String password;
    private List< Expertise > expertise;
    private Byte getABoolean;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dob;

}
