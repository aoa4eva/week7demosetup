package me.aoa4eva.week7demo.controllers;

import me.aoa4eva.week7demo.models.*;
import me.aoa4eva.week7demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EducationRepository educationRepository;


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    SkillRepository skillRepository;

    @RequestMapping("/")
    public @ResponseBody String showIndex()
    {
        return "This is the welcome page";
    }

    @GetMapping("/addstudent")
    public @ResponseBody String addPersonAndEducation()
    {
        //This method shows how to add BOTH student and education at the same time. This relationship only works one way since there is no need for the 'many' side to exist
        //without the 'one'

        //Create a new instance of the person (usually done during registration)
        //You can also get an instance of person from the person ID
        Person p = new Person();

        //Setter methods are automatically called by forms when they are posted
        p.setUsername("myusername");
        p.setPassword("password");

        //You can add an instance of education that does not already exist and save it. Since this instance will only belong
        //to the person you are creating, there is no need for it to exist on its own, even though you can create one
        //and add it to a person later.
        Education e = new Education();
        e.setInstitution("An institution");
        p.addEducation(e);
        personRepository.save(p);

        return "A new item has been added";

    }

    @GetMapping("/addeducation")
    public @ResponseBody String addEducation()
    {

        //To create a new education element and attach it to a person, follow the steps below.

        //Create the education object and set its values (remember, this can also be done using a form)
        Education e = new Education();
        e.setInstitution("Another institution");

        //Draw down the person associated with the education object. This means the person object has to exist first.
        //If you are creating an education object that will not be associated with a person till later, you can save that education object using the
        //EducationRepository class.

        Person p = personRepository.findOne(new Long(1));

        //Add the education object to the person
        p.addEducation(e);

        //Save the person object
        personRepository.save(p);

        return "Education item added";
    }


    @GetMapping("/addrole/{id}")
    public @ResponseBody String simpleAdd(@PathVariable("id") long id)
    {
        //Create a new role (do not assign it to a person yet)
        UserRole r = new UserRole();
        r.setRoleName("ROLE_USER");//Append 'ROLE' to the permission if you want to be able to use "hasRole", either in Thymeleaf or the Security Configuration. Otherwise, use "hasAuthority"

        //Check to see if the user exists and return an error message if not
        Person p = personRepository.findOne(new Long(id));
        if(p==null)
            return "Unable to find person";

        //If the person exists, add a role that already exists
        p.addRole(r);

        //Add a role that does not already exist and cascade the changes
        UserRole newRole = new UserRole();
        newRole.setRoleName("ROLE_ADMIN");
        p.addRole(newRole);

        //Save the person and associate him/her with BOTH users
        personRepository.save(p);

        return "Role successfully added";
    }

    @GetMapping("/showadmins")
    public @ResponseBody String showAdmins()
    {
        //Declare all your variables at the top!

        UserRole r;
        /**
         * This method shows a list of users with a particular role. This Many To Many relationship is a unidiretcional one,
         * so deatils about the relationship can only be retrieved from User. To see a bidrectional relationship, see Jobs and
         * Skills*/


        //EXAMPLE 1: SEARCHING FOR ALL ADMIN USERS
        //Find the role you want to search for. This can be multiple roles if you wish. See the second example in this method.
        r = roleRepository.findByRoleName("ROLE_ADMIN");

        //Iterate through Person objects with that role
        for(Person person:personRepository.findAllByRoleListContains(r))
        {
            //The roles will be printed out in the console
            System.out.println("Administrators");
            System.out.println(person.getUsername()+" with an ID of "+person.getId());
        }

        //EXAMPLE TWO:
        //Show a list of users with one of several permissions
        //This list contains ROLE_ADMIN and ROLE_USER. You can create your own HashSet of items and passing it to method that iterates through the list

        System.out.println("Administrators and ordinary Users");
        Set<UserRole> roleList = new HashSet<UserRole>();
        roleList.add(roleRepository.findByRoleName("ROLE_ADMIN"));
        roleList.add(roleRepository.findByRoleName("ROLE_USER"));
        //Iterate through Person objects with that role

        //This only shows own user per role, instead of showing the same user as many times as the roles that are listed
        HashSet <Person> listed = (HashSet) personRepository.findAllByRoleListIn(roleList);

        for(Person p :listed)
        {
            System.out.println(p.getUsername()+" with an ID of "+p.getId());
        }

        return "Check the console";
    }

    @GetMapping("/addjob")
    public @ResponseBody String addJob()
    {
        //A person is created
        Person newPerson = new Person();
        newPerson.setUsername("anotherusername");
        newPerson.setPassword("anotherpassword");
        personRepository.save(newPerson);

        //A job is created and saved.
        Jobs job = new Jobs();
        job.setTitle("Java Programmer (Entry Level)");
        job.addPerson(personRepository.findOne(new Long(1)));
        jobRepository.save(job);

        //Find the person
        newPerson = personRepository.findOne(new Long(1));
        newPerson.addJobs(job);
        personRepository.save(newPerson);

        return "Job added to Person 1";
    }

    @GetMapping("/addskilltojob")
    public  @ResponseBody  String addSkillToJob()
    {
        Jobs job = jobRepository.findOne(new Long(1));
        job.addSkill(skillRepository.findOne(new Long(1)));
        jobRepository.save(job);
        return "Skill added to Job";
    }

    @GetMapping("/addskilltoperson")
    public  @ResponseBody String addSkillToPerson()
    {
        //Create a skill to add
        Skills s= new Skills();
        s.setSkill("Type 50 WPM");
        s.setDescription("Type at least 50 WPM");

        Person p = personRepository.findOne(new Long(1));
        p.addSkills(s);
        personRepository.save(p);

        //Draw down the person and then add a skill
        return "A new skill has been added to "+p.getId()+"("+p.getUsername()+ ")";
    }

    @GetMapping("/findjobswithmyskills")
    public @ResponseBody String findJobsWithSkills()
    {
        //Select the person for whom you want to find a skill list
        Person p = personRepository.findOne(new Long(1));

        System.out.println("The list of jobs matching your skills");
        for(Jobs jobitem:jobRepository.findAllBySkillsIn(p.getMyskills()))
        {
            System.out.println(jobitem.getTitle());
        }
        return "Check your console";
    }
}
