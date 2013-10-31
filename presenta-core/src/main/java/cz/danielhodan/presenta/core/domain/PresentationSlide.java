package cz.danielhodan.presenta.core.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRESENTATION_SLIDE")
@SequenceGenerator(name = "PRESENTATION_SLIDE_ID_SEQ", sequenceName = "PRESENTATION_SLIDE_ID_SEQ", initialValue = 1)
public class PresentationSlide implements Serializable {

    private Integer id;
    private String name;
    private String path;
    private Presentation presentation;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESENTATION_SLIDE_ID_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne
    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }
}
