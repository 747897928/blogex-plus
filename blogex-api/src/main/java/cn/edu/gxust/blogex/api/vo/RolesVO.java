package cn.edu.gxust.blogex.api.vo;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
public class RolesVO {

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
        return "RolesVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

