package momotar.jdbcrealm.cdis;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Natsuki
 */
@Named(value = "indexPage")
@RequestScoped
public class IndexPage {
    private String username;
    private String password;
    
    public IndexPage() {
    }
    
    /*
    ログインボタンが押下された際の処理
    */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        try {
            request.login(getUsername(), getPassword());
        } catch (ServletException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "ログインに失敗しました。",
                "ユーザ名、パスワードを正しく入力してください。"));
            return "";
        }
        return "home.xhtml?faces-redirect=true";
    }
    
    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(IndexPage.class.getName()).log(Level.SEVERE,"ログアウト失敗", ex);
        }
        return "index.xhtml?faces-redirect=true ";
    }
    
    /*
        既にログイン済みだった場合、ログイン後のページ(home.xhtml)へリダイレクトし
        login.xhtml を非表示
     */
    public void onPageLoad() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            try {
                StringBuilder redirectURL = new
                StringBuilder(request.getContextPath());
                redirectURL.append("/faces/login/home.xhtml");
                FacesContext.getCurrentInstance().getExternalContext().
                redirect(redirectURL.toString());
            } catch (IOException ex) {
                request.logout();
            }
        }
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
    } 
}
