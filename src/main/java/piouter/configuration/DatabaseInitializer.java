package piouter.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PiouRepository piouRepository;

    @Bean
    public Integer databaseInitialization(){
        User devoxxFr = new User("info@devoxx.fr");
        userRepository.save(devoxxFr);

        User thebignet = new User("thebignet@gmail.com");
        thebignet.addFollowing(devoxxFr);
        userRepository.save(thebignet);

        User machin = new User("machin@gmail.com");
        machin.addFollowing(thebignet);
        userRepository.save(machin);

        piouRepository.save(new Piou(thebignet,"Ceci est mon premier piou"));
        piouRepository.save(new Piou(thebignet,"Ceci est mon deuxieme piou"));
        piouRepository.save(new Piou(devoxxFr,"Bienvenue à DevoxxFR"));
        return 0;
    }
}
