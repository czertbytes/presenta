package cz.danielhodan.presenta.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
@SequenceGenerator(name = "ACCOUNT_ID_SEQ", sequenceName = "ACCOUNT_ID_SEQ", initialValue = 1)
public class Account implements Serializable {

    private static final long serialVersionUID = 5938094981245706696L;

    private Integer id;
    private String username;
    private String password;
    private Set<String> roles;
    private Calendar created;
    private Calendar updated;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ID_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "USERNAME", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ROLE")
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @JoinTable(name = "ACCOUNT_ROLES", joinColumns = @JoinColumn(name = "id"))
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @PrePersist
    protected void onCreate() {
        created = Calendar.getInstance();
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = Calendar.getInstance();
    }
}
