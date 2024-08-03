package backend.multidbapi.multidbapi.services.mapperconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import backend.multidbapi.multidbapi.models.MediaResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(MediaResponse.class, new MediaResponseSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
