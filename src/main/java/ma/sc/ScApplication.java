package ma.sc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

@SpringBootApplication
@Transactional
public class ScApplication  implements CommandLineRunner {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger LOGGER = LoggerFactory.getLogger(ScApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ScApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		int max = 6;
		int min = 1;
		int range = max - min + 1;
		int rand = (int)(Math.random() * range) + min;

		InputStream inStream = new ClassPathResource("queries.xml").getInputStream();
		Properties props = new Properties();
		props.loadFromXML(inStream);
		String randomRequete = props.getProperty(String.valueOf(rand));

		try{
			LOGGER.info("Exécution du requête :"+randomRequete);
			jdbcTemplate.execute(randomRequete);
			LOGGER.info("Exécution terminé avec succès");
		}
		catch (Exception e){
			LOGGER.info("Exécution du requête :"+randomRequete+"a échoué");
			LOGGER.info("Message d'erreur  :"+e.getMessage());
			e.printStackTrace();
		}

	}
}
