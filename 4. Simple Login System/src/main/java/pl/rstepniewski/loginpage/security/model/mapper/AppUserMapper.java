package pl.rstepniewski.loginpage.security.model.mapper;


import pl.rstepniewski.loginpage.security.model.AppUser;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;

public final class AppUserMapper {
    private AppUserMapper() {}
    public static AppUserDto map(final AppUser applicationUser) {
        return AppUserDto.builder()
                .username(applicationUser.getUsername())
                .email(applicationUser.getEmail())
                .password(applicationUser.getPassword())
                .build();
    }
}
