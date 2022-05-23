package cn.edu.gxust.blogex.api.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 *
 * @author zhaoyijie(AquariusGenius)
 * @since 2020/11/20 19:20
 */
@Component
@ConfigurationProperties(prefix = "blogex.admin")
public class BlogAdmin {

    private String role;/*博主角色名*/

    private String userName;

    private String passWord;

    private String[] permissionList;/*权限列表*/

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String[] getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(String[] permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "BlogAdmin{" +
                "role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", permissionList=" + Arrays.toString(permissionList) +
                '}';
    }
}
