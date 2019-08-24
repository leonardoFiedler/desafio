package br.com.leonardofiedler.challenge.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Leonardo Fiedler
 *
 */
@Data
@Entity
public class Url {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    private String link;
    private Long expiresAt;
    private String newUrl;

    public Url() {

    }

    public Url(String link, Long expiresAt, String newUrl) {
        this.link = link;
        this.expiresAt = expiresAt;
        this.newUrl = newUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }
}
