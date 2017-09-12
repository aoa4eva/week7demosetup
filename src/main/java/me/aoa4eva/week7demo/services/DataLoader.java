package me.aoa4eva.week7demo.services;

import me.aoa4eva.week7demo.models.Person;
import me.aoa4eva.week7demo.models.Skills;
import me.aoa4eva.week7demo.models.UserRole;
import me.aoa4eva.week7demo.repositories.PersonRepository;
import me.aoa4eva.week7demo.repositories.RoleRepository;
import me.aoa4eva.week7demo.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    PersonRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SkillRepository skillRepository;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data . . .");

        roleRepository.save(new UserRole("SEEKER"));
        roleRepository.save(new UserRole("RECRUITER"));
        roleRepository.save(new UserRole("ADMIN"));

        UserRole seekerRole = roleRepository.findByRoleName("SEEKER");
        UserRole recruiterRole = roleRepository.findByRoleName("RECRUITER");
        UserRole adminRole = roleRepository.findByRoleName("ADMIN");

        Person user = new Person("bob@bob.com","bob","Bob","Bobberson", true, "bob");
        userRepository.save(user);
        user.addRole(seekerRole);
        userRepository.save(user);

        user = new Person("jim@jim.com","jim","Jim","Jimmerson", true, "jim");
        userRepository.save(user);
        user.addRole(recruiterRole);
        userRepository.save(user);

        user = new Person("admin@secure.com","password","Admin","User", true, "admin");
        userRepository.save(user);
        user.addRole(adminRole);
        userRepository.save(user);

        user = new Person("sam@every.com","password","Sam","Everyman", true, "everyman");
        userRepository.save(user);
        user.addRole(adminRole);
        user.addRole(recruiterRole);
        userRepository.save(user);

        skillRepository.save(new Skills("Typing - 50 WPM+"));
        skillRepository.save(new Skills("Team work"));
        skillRepository.save(new Skills("Leadership"));
    }
}