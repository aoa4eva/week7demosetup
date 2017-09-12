package me.aoa4eva.week7demo.controllers;

import me.aoa4eva.week7demo.models.Jobs;
import me.aoa4eva.week7demo.models.Person;
import me.aoa4eva.week7demo.models.Skills;
import me.aoa4eva.week7demo.models.UserRole;
import me.aoa4eva.week7demo.repositories.*;
import me.aoa4eva.week7demo.sessions.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;

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

    @Autowired
    public CurrentUser currentUser;

    @GetMapping("/")
    public String showIndex(Model model, Principal p)
    {
        if(p!=null)
        {
            currentUser.setP(personRepository.findByUsername(p.getName()));
            String uname = currentUser.getP().getUsername();
            System.out.println(uname);
            model.addAttribute("username",uname);
            model.addAttribute("jobList",jobRepository.findAll());
           return "index";
        }
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome()
    {
        return "welcome";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("roleList",roleRepository.findAll());
        model.addAttribute("user", new Person());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") Person user,
            BindingResult result,
            Model model,HttpServletRequest request){

        model.addAttribute("user", user);

        if (result.hasErrors()) {
            return "registration";
        } else {
            //Find role by name
            //Add to user
            long selectedRole = new Long(request.getParameter("rolechoice"));
            System.out.println(selectedRole);
            UserRole r = roleRepository.findOne(selectedRole);
            user.addRole(r);
            personRepository.save(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "index";
    }

    @GetMapping("/addjobselection")
    public  String showSelector(Model model)
    {
        model.addAttribute("newjob",new Jobs());
        return "addjob";
    }

    @GetMapping("/job/update/{id}")
    public  String showSelector(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newjob",jobRepository.findOne(id));
        return "addjob";
    }
    @PostMapping("/")
    public String addJob(@Valid @ModelAttribute("newjob") Jobs job, BindingResult bindingResult, Model model, Principal p)
    {
        if(bindingResult.hasErrors())
           return "addjob";
        if(p!=null)

        jobRepository.save(job);
        model.addAttribute("message","Job successfully saved");
        model.addAttribute("jobList",jobRepository.findAll());
        return "index";
    }

    @GetMapping("/addskill/{id}")
    public String addSkillforJob(@PathVariable("id") long id, RedirectAttributes redirectAttributes,Model model)
    {
        //Checks to see if any skills have been set up
        if(skillRepository.count()<1)
        {
            //Makes sure data can still be transmitted after a redirect. Ordinary model attributes lose this data when you redirect.
            redirectAttributes.addFlashAttribute("message","Please set up skills before continuing");
            return "redirect:/";
        }
        Jobs j = jobRepository.findOne(id);
        HashSet container = new HashSet<Jobs>();
        container.add(j);
        //This looks for the jobs with skills attatched and excludes the skills that have already been used
        //Change this later
        //Iterable <Skills> list = skillRepository.findAllByJskillsNotIn(container);
        Iterable <Skills> list = skillRepository.findAll();
        System.out.println("Items in the skill list:");
        for(Skills item:list)
        {
            System.out.println(item.getSkill());
        }
        model.addAttribute("id", id);
        model.addAttribute("skillList", skillRepository.findAll());
        //return "addskill";
        model.addAttribute("returnRoute","addskill");
        return "testskills";
    }

    @PostMapping("/addskill")
    public String saveSkill(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        Skills aSkill = skillRepository.findOne(new Long(request.getParameter("skillchoice")));
        Jobs aJob = jobRepository.findOne(new Long(request.getParameter("id")));
        aJob.addSkill(aSkill);
        jobRepository.save(aJob);
        redirectAttributes.addAttribute("message","Your skill has been saved");
        return "redirect:/";
    }

    @GetMapping("/addskilltodb")
    public  String addSkilltoDB(Model model)
    {
        model.addAttribute("newSkill",new Skills());
        return "addskilltodb";
    }

    @PostMapping("/addskilltodb")
    public String postSkillToDB(@Valid @ModelAttribute("newSkill") Skills skill, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
            return "addskilltodb";

        skillRepository.save(skill);
        model.addAttribute("message","Skill successfully saved");
        model.addAttribute("jobList",jobRepository.findAll());
        return "index";
    }

    @GetMapping("/addpersonskill")
    public String addSkillforPerson(RedirectAttributes redirectAttributes,Model model, Principal p)
    {
        //Gets the user ID from the current user. Advantage: ID does not show in URL.
        long id = personRepository.findByUsername(p.getName()).getId();


        //Checks to see if any skills have been set up
        //The process for adding jobs and skills is the same, so thesame functions have been used.
        //This method is literally a copy of the
        if(skillRepository.count()<1)
        {
            //Makes sure data can still be transmitted after a redirect. Ordinary model attributes lose this data when you redirect.
            redirectAttributes.addFlashAttribute("message","Please set up skills before continuing");
            return "redirect:/";
        }

        //Include a method to find skills that the person already has and make them disappearfrom the full listlater.
        Iterable <Skills> list = skillRepository.findAll();
        System.out.println("Items in the skill list:");
        for(Skills item:list)
        {
            System.out.println(item.getSkill());
        }
        model.addAttribute("id", id);
        model.addAttribute("skillList", skillRepository.findAll());
        //return "addskill";
        model.addAttribute("returnRoute","addpersonskill");
        return "addpersonskill";
    }

    @PostMapping("/addpersonskill")
    public String savePersonSkill(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        //Same form to save person skills.
        Skills aSkill = skillRepository.findOne(new Long(request.getParameter("skillchoice")));
        Person aPerson = personRepository.findOne(new Long(request.getParameter("id")));
        aPerson.addSkills(aSkill);
        personRepository.save(aPerson);
        redirectAttributes.addAttribute("message","Your skill has been saved");
        return "redirect:/";
    }

    @GetMapping("/matchjobtopeople/{id}")
    public String matchJob(@PathVariable("id") long id, Model model)
    {
        Jobs j = jobRepository.findOne(id);
        Iterable <Person> targets = personRepository.findAllByMySkillsIn((j.getJobSkills()));
        for(Person p: targets)
        {
            System.out.println(p.getFirstName());
        }
        model.addAttribute("personList", targets);
        return "matchpeople";
    }

    @GetMapping("/matchpersontojob")
    public String matchJob(Principal p, Model model)
    {
        Person person = personRepository.findByUsername(p.getName());

        Iterable <Jobs> targets = jobRepository.findAllByJobSkillsIn(person.getMySkills());
        for(Jobs eachJob: targets)
        {
            System.out.println(eachJob.getTitle());
        }
        model.addAttribute("personList", targets);
        return "matchtojobs";
    }


}
