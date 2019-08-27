package br.com.leonardofiedler.challenge.controller;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.response.UrlResponse;
import br.com.leonardofiedler.challenge.service.IUrlService;
import br.com.leonardofiedler.challenge.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/tiny-url")
public class TinyUrlController {

    @Autowired
    private UrlService urlService;

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private int port;

    @GetMapping(path = "/shorten")
    public @ResponseBody
    UrlResponse shortenUrl(@RequestParam String link) {
        //If the link is empty, throws it.
        if (!urlService.isValidLink(link)) {
            throw new IllegalArgumentException("O Link deve possuir pelo menos 5 caracteres e no máximo 36");
        }

        Url url = urlService.saveUrl(link);
        String urlStr = String.format("%s:%s/%s",address, port, url.getNewUrl());
        return new UrlResponse(urlStr, url.getExpiresAt());
    }
}
