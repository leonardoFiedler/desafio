package br.com.leonardofiedler.challenge.controller;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.repository.UrlRepository;
import br.com.leonardofiedler.challenge.response.UrlResponse;
import br.com.leonardofiedler.challenge.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping(path = "/tiny-url")
public class TinyUrlController {

    @Autowired
    private UrlRepository urlRepository;

    @GetMapping(path = "/shorten")
    public @ResponseBody UrlResponse shortenUrl(@RequestParam String link) {
        //If the link is empty, throws it.
        if (link.isEmpty()) {
            throw new IllegalArgumentException("O Parâmetro link não pode ser vazio");
        }

        String formattedLink = "";
        //If URL has http or https, removes it.
        if (link.contains("http") || link.contains("https")) {
            formattedLink = link.replaceFirst("http(s)?", "");
        }

        //Replace all non character, except the . character, letter and numbers.
        formattedLink = formattedLink.replaceAll("[^A-Za-z0-9.]", "");

        //Check if the limit is correct 5..36.
        if (formattedLink.length() < 5 || formattedLink.length() > 36) {
            throw  new UnsupportedOperationException("O Link deve possuir pelo menos 5 caracteres e no máximo 36");
        }

        Long expiresAt = DateUtils.getExpirationDate(new Date());
        String baseUrl = "localhost:8080/";

        //Generates UUID, split and get first sequence of chars.
        UUID uuid = UUID.randomUUID();
        String newUrl = uuid.toString().split("-")[0];

        //TODO: Find at database with the end url.


        Url url = new Url(formattedLink, expiresAt, newUrl);
        urlRepository.save(url);

        UrlResponse urlResponse = new UrlResponse(newUrl, expiresAt);
        return urlResponse;
    }


}
