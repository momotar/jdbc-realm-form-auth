/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package momotar.jdbcrealm.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import momotar.jdbcrealm.DigestUtil.SHA256Encoder;
import momotar.jdbcrealm.entities.Groups;
import momotar.jdbcrealm.entities.GroupsPK;
import momotar.jdbcrealm.entities.Users;

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
