package momotar.jdbc.realm.form.auth.cdis;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import momotar.jdbc.realm.form.auth.ejbs.UserRegistManager;

/**
 *
 * @author Natsuki
 */
@Named(value = "removePage")
@RequestScoped
public class RemovePage {
    private String username;
    @EJB
    UserRegistManager userManager;
    
    /*
    DB からユーザ情報の削除
    */
    public String removeDB(){
        userManager.removeUser(getUsername());
        return "rem-success";
    }
    
    public RemovePage() {
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
}
