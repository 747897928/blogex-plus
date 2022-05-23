package cn.edu.gxust.blogex.api.entity;

/**
 * @author zhaoyijie
 * @since 2022/3/22 15:15
 */
public class BlogUser {

    /**
     * username
     */
    private String username;

    /**
     * 角色，逗号分隔
     */
    private String roles;

    /**
     * token
     */
    private String token;

    public BlogUser() {
    }

    private BlogUser(Builder builder) {
        setUsername(builder.username);
        setRoles(builder.roles);
        setToken(builder.token);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(BlogUser copy) {
        Builder builder = new Builder();
        builder.username = copy.getUsername();
        builder.roles = copy.getRoles();
        builder.token = copy.getToken();
        return builder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BlogUser{" +
                "username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                ", token='" + token + '\'' +
                '}';
    }


    public static final class Builder {
        private String username;
        private String roles;
        private String token;

        private Builder() {
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder roles(String val) {
            roles = val;
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public BlogUser build() {
            return new BlogUser(this);
        }
    }
}
