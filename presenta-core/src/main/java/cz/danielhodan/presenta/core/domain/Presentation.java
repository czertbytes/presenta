package cz.danielhodan.presenta.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PRESENTATION")
@SequenceGenerator(name = "PRESENTATION_ID_SEQ", sequenceName = "PRESENTATION_ID_SEQ", initialValue = 1)
public class Presentation implements Serializable {

    private Integer id;
    private Integer status;
    private String title;
    private String description;
    private Account moderator;
    private Set<Account> participants;
    private List<PresentationSlide> presentationSlides;
    private Calendar created;
    private Calendar updated;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESENTATION_ID_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "STATUS", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DESCRIPTION", unique = true, nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public Account getModerator() {
        return moderator;
    }

    public void setModerator(Account moderator) {
        this.moderator = moderator;
    }

    @ManyToMany()
    @JoinTable(name = "PRESENTATION_PARTICIPANTS", joinColumns = @JoinColumn(name = "PRESENTATION_ID"), inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID"))
    public Set<Account> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Account> participants) {
        this.participants = participants;
    }

    @OneToMany(mappedBy = "presentation")
    @OrderBy(value = "id")
    public List<PresentationSlide> getPresentationSlides() {
        return presentationSlides;
    }

    public void setPresentationSlides(List<PresentationSlide> presentationSlides) {
        this.presentationSlides = presentationSlides;
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
