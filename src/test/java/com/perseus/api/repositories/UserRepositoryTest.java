package com.perseus.api.repositories;

import com.perseus.api.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void testThatUserCanBeLookedUpWithName() {
        //Given
        final String firstName = "Emmanuel";
        final String lastName = "Tokun";

        User user = new User();
        user.setLastName(firstName);
        user.setLastName(lastName);
        underTest.save(user);

        //When
        User expectedUser = underTest.findByName(firstName);

        //Then
        assertThat(expectedUser).isSameAs(user);
    }
}