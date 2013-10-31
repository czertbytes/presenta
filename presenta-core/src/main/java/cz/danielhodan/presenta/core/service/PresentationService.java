package cz.danielhodan.presenta.core.service;

import cz.danielhodan.presenta.core.domain.Presentation;

import java.util.List;

public interface PresentationService {

    Presentation insert(Presentation presentation);

    Presentation findById(Integer id);

    List<Presentation> findAll();

    Presentation update(Presentation message);

    boolean delete(Presentation message);
}
