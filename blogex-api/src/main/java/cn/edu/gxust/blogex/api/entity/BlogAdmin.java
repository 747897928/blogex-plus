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

    private String[] permissionList;/*权限列表*/

    public String[] getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(String[] permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "BlogAdmin{" +
                "permissionList=" + Arrays.toString(permissionList) +
                '}';
    }
}
