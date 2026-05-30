package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<User> findAllUsers();

    /**
     * Retrieves all users whose e-mail address contains the given fragment,
     * ignoring character case.
     *
     * @param emailFragment the (partial) e-mail to search for, case-insensitive
     * @return a list of users whose e-mail matches the fragment; empty if none matched
     */
    List<User> findUsersByEmailContaining(String emailFragment);

    /**
     * Retrieves all users who are older than the given date, i.e. users whose
     * date of birth is strictly before {@code time}.
     *
     * @param time the boundary date; users born before this date are returned
     * @return a list of matching users; empty if none matched
     */
    List<User> findUsersOlderThan(LocalDate time);

}