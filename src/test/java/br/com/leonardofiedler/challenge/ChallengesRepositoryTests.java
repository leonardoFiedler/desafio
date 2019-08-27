package br.com.leonardofiedler.challenge;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.repository.UrlRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChallengesRepositoryTests {

	@Autowired
	private UrlRepository urlRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createShouldPersistData() {
		Url url = new Url("twitter.com", 1234565454L, "localhost:8080/abc123");
		this.urlRepository.save(url);
		assertThat(url.getId()).isNotNull();
		assertThat(url.getExpiresAt()).isEqualTo(1234565454L);
		assertThat(url.getNewUrl()).isEqualTo("localhost:8080/abc123");
	}

	@Test
	public void deleteShouldRemoveData() {
		Url url = new Url("twitter.com", 1234565454L, "localhost:8080/abc123");
		this.urlRepository.save(url);
		this.urlRepository.delete(url);
		assertThat(urlRepository.findById(url.getId())).isEmpty();
	}
}
