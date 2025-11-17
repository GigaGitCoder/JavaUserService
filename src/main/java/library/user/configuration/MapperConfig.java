package library.user.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import library.user.mapper.AccountMapper;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MapperConfig {

    @Bean
    public AccountMapper accountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }
}
