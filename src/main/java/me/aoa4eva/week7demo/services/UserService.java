
import me.aoa4eva.week7demo.models.Person;
import me.aoa4eva.week7demo.repositories.PersonRepository;
import me.aoa4eva.week7demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service

public class UserService {



    @Autowired

    PersonRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PersonRepository userRepository) {

        this.userRepository = userRepository;

    }



    public Person findByUsername(String email) {

        return userRepository.findByUsername(email);

    }

    public Long countByUsername(String username) {

        return userRepository.countByUsername(username);

    }


    public void saveUser(Person user) {

        user.addRole(roleRepository.findByRoleName("USER"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }


    public void saveAdmin(Person user) {

        user.addRole(roleRepository.findByRoleName("ADMIN"));

        userRepository.save(user);

    }

}