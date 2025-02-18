package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  // 声明为Spring Bean，使得该类可以被注入到其他Bean中，如@Autowired
@ConfigurationProperties(prefix = "sky.jwt")  // 配置文件中jwt的前缀,如sky.jwt.admin-secret-key=123456
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
