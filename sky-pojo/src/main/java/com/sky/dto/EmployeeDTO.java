package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;

//    @NotBlank(message = "用户名不能为空")
//    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,16}$", message = "用户名必须是5-16位的字母、数字、下划线、减号组成")
    private String username;

//    @NotBlank(message = "姓名不能为空")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,4}$", message = "姓名必须是2-4位中文")
    private String name;

//    @NotBlank(message = "手机号不能为空")
//    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号格式不正确")
    private String phone;

//    @NotBlank(message = "性别不能为空")
//    @Pattern(regexp = "^(male|female)$", message = "性别格式不正确")
    private String sex;

//    @NotBlank(message = "身份证号不能为空")
//    @Pattern(regexp = "^\\d{17}[\\d|xX]$", message = "身份证号格式随意")
    private String idNumber;  // 身份证号

}
