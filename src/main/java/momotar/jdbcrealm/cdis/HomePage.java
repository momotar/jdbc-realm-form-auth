package momotar.jdbcrealm.cdis;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import momotar.jdbcrealm.ejbs.RoleCheckLogic;

/**
 *
 * @author Natsuki
 */
@Named(value = "homePage")
@RequestScoped
public class HomePage {

    /**
     * Creates a new instance of HomePage
     */
    public HomePage() {
    }
    
    /*
        ログインしたユーザが、引数で指定した役割（ロール）を持つ
        ユーザか否かを検証
    */
    public boolean isUserInRole(String role) {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }
    
    /*
        EJB のメソッド単位での実行権限の検証
    */
    @EJB
    RoleCheckLogic roleCheckLogic;
    private String roleChekerString;
    
    public String getRoleChekerString() {
        if (isUserInRole("admin")) {
            String adminRoleString = roleCheckLogic.executableByAdmin();
            roleChekerString = adminRoleString;
        }
        if (isUserInRole("user")) {
            String userRoleString = roleCheckLogic.executableByUser();
            roleChekerString = userRoleString;
        }
    return roleChekerString;
    }
}
