package br.com.leonardofiedler.challenge.repository;

import br.com.leonardofiedler.challenge.model.Url;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<Url, Long> {

    public Url findByLink(String link);
    public Url findByNewUrl(String newUrl);

}
