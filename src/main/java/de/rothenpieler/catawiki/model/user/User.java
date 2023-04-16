package de.rothenpieler.catawiki.model.user;

import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a user that can configure search requests
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 16.04.23
 */
@Getter
public class User {

    private String email;

    private String password;

    private String firstname;

    private Date lastLoginAt;

    private Date registeredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(firstname, user.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstname);
    }

    @Override
    public String toString() {

        return "User{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
