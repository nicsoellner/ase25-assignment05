//package de.unibayreuth.se.taskboard.business.ports;
//
//import de.unibayreuth.se.taskboard.business.domain.User;
//import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
//import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
//import org.springframework.context.annotation.Bean;
//import org.springframework.lang.NonNull;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface UserService {
//    @NonNull
//    User create(@NonNull User user) throws MalformedRequestException;
//
//    @NonNull
//    List<User> getAll();
//
//    @NonNull
//    User getById(@NonNull UUID id) throws UserNotFoundException;
//
//    void clear();
//}
//

package de.unibayreuth.se.taskboard.business.ports;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface UserService{
    @NonNull
    User create(@NonNull User user) throws MalformedRequestException;

    @NonNull
    List<User> getAll();

    @NonNull
    User getById(@NonNull UUID id) throws UserNotFoundException;

    void clear();

}
