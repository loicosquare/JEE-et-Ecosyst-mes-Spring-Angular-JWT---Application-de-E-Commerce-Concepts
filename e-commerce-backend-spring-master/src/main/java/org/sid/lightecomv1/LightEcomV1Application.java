package org.sid.lightecomv1;

import net.bytebuddy.utility.RandomString;
import org.sid.lightecomv1.dao.CategoryRepository;
import org.sid.lightecomv1.dao.ProductRepository;
import org.sid.lightecomv1.entities.Category;
import org.sid.lightecomv1.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;
import java.util.Random;

@SpringBootApplication
public class LightEcomV1Application implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(LightEcomV1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repositoryRestConfiguration.exposeIdsFor(Product.class,Category.class);

        categoryRepository.save(new Category(null,"Computers",null,null,null));
        categoryRepository.save(new Category(null,"Printers",null,null,null));
        categoryRepository.save(new Category(null,"Smart phones",null,null,null));
        Random rnd=new Random();
        categoryRepository.findAll().forEach(c->{
            for (int i = 0; i <10 ; i++) {
                Product p=new Product();
                p.setName(RandomString.make(18));
                p.setCurrentPrice(100+rnd.nextInt(10000));
                p.setAvailable(rnd.nextBoolean());
                p.setPromotion(rnd.nextBoolean());
                p.setSelected(rnd.nextBoolean());
                p.setCategory(c);
                p.setPhotoName("unknown.png");
                productRepository.save(p);
            }
        });
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
