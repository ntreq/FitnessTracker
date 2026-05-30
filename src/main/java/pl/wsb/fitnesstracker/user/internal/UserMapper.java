package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserRequestDto;

/**
 * Maps between the {@link User} entity and its various data transfer objects.
 * Kept package-private so that the mapping logic does not leak outside of the
 * {@code user.internal} package.
 */
@Component
class UserMapper {

    /**
     * Maps a {@link User} entity to a full {@link UserDto}.
     *
     * @param user the user entity to map
     * @return the corresponding {@link UserDto}
     */
    UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Maps a {@link User} entity to a {@link UserSimpleDto} exposing only
     * the identifier and the user's name.
     *
     * @param user the user entity to map
     * @return the corresponding {@link UserSimpleDto}
     */
    UserSimpleDto toSimpleUserDto(User user) {
        return new UserSimpleDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Maps a {@link User} entity to a {@link UserEmailDto} exposing only
     * the identifier and the e-mail address.
     *
     * @param user the user entity to map
     * @return the corresponding {@link UserEmailDto}
     */
    UserEmailDto toUserEmailDto(User user) {
        return new UserEmailDto(
                user.getId(),
                user.getEmail());
    }

    /**
     * Creates a new {@link User} entity from the provided {@link UserDto}.
     * The identifier from the DTO is intentionally ignored so that the
     * persistence layer can generate it.
     *
     * @param userDto the source data transfer object
     * @return a new, non-persisted {@link User} entity
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }
}