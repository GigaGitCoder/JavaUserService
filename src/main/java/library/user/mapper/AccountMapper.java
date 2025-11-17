package library.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import library.user.dto.AccountRequestDTO;
import library.user.dto.AccountResponseDTO;
import library.user.entity.Account;

@Mapper
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    Account toEntity(AccountRequestDTO dto);

    AccountResponseDTO toDto(Account entity);
}
