package momotar.jdbc.realm.form.auth.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Natsuki
 */
@Entity
@Table(name = "GROUPS")
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.findByUsername", query = "SELECT g FROM Groups g WHERE g.groupsPK.username = :username"),
    @NamedQuery(name = "Groups.findByGroupid", query = "SELECT g FROM Groups g WHERE g.groupsPK.groupid = :groupid")})
public class Groups implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsPK groupsPK;
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Groups() {
    }

    public Groups(GroupsPK groupsPK) {
        this.groupsPK = groupsPK;
    }

    public Groups(String username, String groupid) {
        this.groupsPK = new GroupsPK(username, groupid);
    }

    public GroupsPK getGroupsPK() {
        return groupsPK;
    }

    public void setGroupsPK(GroupsPK groupsPK) {
        this.groupsPK = groupsPK;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsPK != null ? groupsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.groupsPK == null && other.groupsPK != null) || (this.groupsPK != null && !this.groupsPK.equals(other.groupsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "momotar.jdbc.realm.form.auth.Groups[ groupsPK=" + groupsPK + " ]";
    }
    
}
