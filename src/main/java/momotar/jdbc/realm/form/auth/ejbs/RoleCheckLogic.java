package momotar.jdbc.realm.form.auth.ejbs;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author Natsuki
 */
@Stateless
public class RoleCheckLogic {
    @RolesAllowed("admin")
    public String executableByAdmin() {
        return "管理者による実行が可能なロジック";
    }
    @RolesAllowed("user")
    public String executableByUser() {
        return "ユーザによる実行が可能なロジック";
    }
}
