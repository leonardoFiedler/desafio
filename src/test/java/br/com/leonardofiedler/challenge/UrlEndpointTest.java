package br.com.leonardofiedler.challenge;

import br.com.leonardofiedler.challenge.repository.UrlRepository;
import br.com.leonardofiedler.challenge.service.UrlService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlEndpointTest {

    @MockBean(UrlRepository.class)
    private UrlRepository urlRepository;

    @Autowired
    private UrlService urlService;

    @Test
	public void urlLengthShouldBeRight() {
		String link = "https://www.twitter.com";
        Assertions.assertThat(urlService.isValidLink(link)).isEqualTo(true);
	}

	@Test
	public void urlLengthShouldBeAvoided() {
		String link = "www.";
        Assertions.assertThat(urlService.isValidLink(link)).isEqualTo(false);
	}

	@Test
	public void urlLengthTooLong() {
		String link = "www.twitter.com/profileabc/photo/123/image.png";
        Assertions.assertThat(urlService.isValidLink(link)).isEqualTo(false);
	}

	@Test
	public void getLinkFormattedWithHTTP() {
		String link = "http://www.twitter.com";
        Assertions.assertThat(urlService.getFormattedLink(link)).isEqualTo("www.twitter.com");
	}

	@Test
	public void getLinkFormattedWithSpecialCharacters() {
		String link = "http://www.twitter.com/abc&c54*";
        Assertions.assertThat(urlService.getFormattedLink(link)).isEqualTo("www.twitter.comabcc54");
	}
}
