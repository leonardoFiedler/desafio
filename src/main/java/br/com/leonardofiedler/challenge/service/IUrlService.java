package br.com.leonardofiedler.challenge.service;

import br.com.leonardofiedler.challenge.model.Url;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author Leonardo Fiedler
 */
@Service
public interface IUrlService {

    public boolean isValidLink(String link);

    public String getFormattedLink(String link);

    public String getUniqueRouteId(Calendar startTime);

    public Url saveUrl(String link);

    public Url findByNewUrl(String path);
}
