package cz.danielhodan.presenta.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "MESSAGE")
@SequenceGenerator(name = "MESSAGE_ID_SEQ", sequenceName = "MESSAGE_ID_SEQ", initialValue = 1)
public class Message implements Serializable {

    private Integer id;
    private String type;
    private Presentation presentation;
    private String message;
    private Account source;
    private Account target;
    private Calendar created;
    private Calendar updated;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_ID_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "TYPE", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
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
