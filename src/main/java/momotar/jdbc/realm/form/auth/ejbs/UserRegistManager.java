package momotar.jdbc.realm.form.auth.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import momotar.jdbc.realm.form.auth.DigestUtil.SHA256Encoder;
import momotar.jdbc.realm.form.auth.entities.Groups;
import momotar.jdbc.realm.form.auth.entities.GroupsPK;
import momotar.jdbc.realm.form.auth.entities.Users;

/**
 *
 * @author Natsuki
 */
@Stateless
public class UserRegistManager {

    @PersistenceContext
    EntityManager em;
    /*
    指定したユーザ、メールアドレス、パスワード、グループ名で
    DB へ登録
    */
    public void createUserAndGroup(String username,
    String email,
    String password,
    String groupname) {
        Users users = new Users();
        users.setUsername(username);
        users.setEmail(email);
        SHA256Encoder encoder = new SHA256Encoder();
        String encodedPassword = encoder.encodePassword(password);
        users.setPassword(encodedPassword);
        Groups groups = new Groups();
        groups.setGroupsPK(new GroupsPK(username, groupname));
        groups.setUsers(users);
        em.persist(users);
        em.persist(groups);
    }
    
    /*
    DB から指定したユーザの削除
    */
    public void removeUser(String username) {
        Users users = em.find(Users.class, username);
        em.remove(users);
    }
    
    /*
    DB から指定したユーザの検索
    */
    public Users findUser(String username){
        Users users = em.find(Users.class, username);
        return users;
    }
}
