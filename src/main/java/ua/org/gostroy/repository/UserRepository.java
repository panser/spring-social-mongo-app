package ua.org.gostroy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.org.gostroy.model.entity.User;

/**
 * Created by Sergey on 6/1/2016.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
