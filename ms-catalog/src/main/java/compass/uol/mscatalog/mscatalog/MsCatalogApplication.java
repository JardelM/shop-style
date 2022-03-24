package compass.uol.mscatalog.mscatalog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class MsCatalogApplication {

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(MsCatalogApplication.class, args);
    }

}
