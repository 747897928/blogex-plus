package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
public class PermissionsDTO {

    @ApiModelProperty(value = "id")
    private Integer id;

    private String role;

    private String resource;

    private String action;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "PermissionsDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", resource='" + resource + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}

