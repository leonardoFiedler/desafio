package br.com.leonardofiedler.challenge.repository;

import br.com.leonardofiedler.challenge.model.Url;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Leonardo Fiedler
 */
public interface UrlRepository extends CrudRepository<Url, Long> {

    public Url findByNewUrl(String newUrl);
    public Url findByNewUrlAndExpiresAtGreaterThan(String newUrl, Long expiresAt);

}
