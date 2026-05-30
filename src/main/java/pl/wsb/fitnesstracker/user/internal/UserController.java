package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller exposing CRUD and search operations on {@link User} entities.
 * All endpoints are served under the {@code /v1/users} base path and exchange
 * data in JSON format over HTTP.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    private final UserProvider userProvider;

    private final UserMapper userMapper;

    /**
     * Returns the full details of all users in the system.
     *
     * @return a list of all users mapped to {@link UserDto}
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userProvider.findAllUsers().stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Returns basic information (id, first and last name) about all users.
     *
     * @return a list of all users mapped to {@link UserSimpleDto}
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userProvider.findAllUsers().stream()
                .map(userMapper::toSimpleUserDto)
                .toList();
    }

    /**
     * Returns the full details of a single user identified by their id.
     *
     * @param id the id of the user to retrieve
     * @return the user mapped to {@link UserDto}
     * @throws UserNotFoundException if no user with the given id exists
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userProvider.getUser(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Searches for users whose e-mail address contains the given fragment,
     * ignoring character case. Returns only the id and e-mail of each match.
     *
     * @param email the (partial) e-mail to search for
     * @return a list of matching users mapped to {@link UserEmailDto}
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUsersByEmail(@RequestParam String email) {
        return userProvider.findUsersByEmailContaining(email).stream()
                .map(userMapper::toUserEmailDto)
                .toList();
    }

    /**
     * Returns all users older than the given date, i.e. born before {@code time}.
     *
     * @param time the boundary date (ISO format, e.g. {@code 2024-08-10})
     * @return a list of matching users mapped to {@link UserDto}
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
        return userProvider.findUsersOlderThan(time).stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Creates a new user from the supplied data.
     *
     * @param userDto the data of the user to create
     * @return the created user mapped to {@link UserDto}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        User created = userService.createUser(userMapper.toEntity(userDto));
        return userMapper.toUserDto(created);
    }

    /**
     * Deletes the user identified by the given id.
     *
     * @param userId the id of the user to delete
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * Updates the user identified by the given id with the supplied data.
     *
     * @param userId  the id of the user to update
     * @param userDto the new values for the user
     * @return the updated user mapped to {@link UserDto}
     */
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User updated = userService.updateUser(userId, userMapper.toEntity(userDto));
        return userMapper.toUserDto(updated);
    }

}