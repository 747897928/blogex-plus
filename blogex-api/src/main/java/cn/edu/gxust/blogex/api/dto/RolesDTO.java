package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
public class RolesDTO {

    @ApiModelProperty(value = "id")
    private Integer id;

    private String username;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RolesDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

