package momotar.jdbcrealm.cdis;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import momotar.jdbcrealm.ejbs.UserRegistManager;

/**
 *
 * @author Natsuki
 */
@Named(value = "registPage")
@RequestScoped
public class RegistPage implements Serializable {
    private String username;
    private String email;
    private String password;
    private String groupid;
    private String passStars;
    
    @EJB
    UserRegistManager userRegist;
    /*
    DB へユーザ情報・グループ情報の登録
    */
    public String registDB() throws IOException {
        userRegist.createUserAndGroup(
        getUsername(),
        getEmail(),
        getPassword(),
        getGroupid());
        return "reg-success";
    }
    
    /**
     * Creates a new instance of RegistPage
     */
    public RegistPage() {
        passStars = "";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the mailaddress
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
        int count = password.length();
        for (int i = 0; i < count; i++) {
            passStars = passStars + "*";
        }
    }
    
    public String getPassStars() {
        return passStars;
    }

    /**
     * @return the group
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * @param groupid the groupid to set
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
}
