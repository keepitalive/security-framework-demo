package tdh.tnt.security.demo.framework.model;

import java.io.Serializable;

/**
 * Created by eric on 2017/10/10.
 */
public class Subject implements Serializable{

    private String userId;

    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
