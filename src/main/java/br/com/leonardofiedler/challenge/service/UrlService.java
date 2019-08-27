package br.com.leonardofiedler.challenge.service;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.repository.UrlRepository;
import br.com.leonardofiedler.challenge.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * @author Leonardo Fiedler
 */
@Service
public class UrlService implements IUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public boolean isValidLink(String link) {
        return link.length() >= 5 && link.length() <= 36;
    }

    @Override
    public String getFormattedLink(String link) {
        String formattedLink = link;
        //If URL has http or https, removes it.
        if (link.contains("http") || link.contains("https")) {
            formattedLink = link.replaceFirst("http(s)?", "");
        }

        //Replace all non character, except the . character, letter and numbers.
        formattedLink = formattedLink.replaceAll("[^A-Za-z0-9.]", "");
        return formattedLink;
    }

    @Override
    public String getUniqueRouteId(Calendar startTime) {
        boolean foundUUID = false;
        UUID uuid = null;
        String newUrl = null;

        //Enter this while until the random generated UUID is not presented in database.
        while (!foundUUID) {
            uuid = UUID.randomUUID();
            newUrl = uuid.toString().split("-")[0];
            Url urlFind = urlRepository.findByNewUrlAndExpiresAtGreaterThan(newUrl, startTime.getTimeInMillis());

            if (urlFind == null)
                foundUUID = true;
        }
        return newUrl;
    }

    @Override
    public Url saveUrl(String link) {
        String formattedLink = this.getFormattedLink(link);
        Calendar currentDate = DateUtils.getCurrentTimeCalendar();
        String newUrl = this.getUniqueRouteId(currentDate);
        Long expiresAt = DateUtils.getExpirationDate(currentDate.getTime());
        Url url = new Url(formattedLink, expiresAt, newUrl);
        urlRepository.save(url);
        return url;
    }

    @Override
    public Url findByNewUrl(String path) {
        return urlRepository.findByNewUrl(path);
    }
}
