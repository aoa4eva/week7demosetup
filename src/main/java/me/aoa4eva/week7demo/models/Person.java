package me.aoa4eva.week7demo.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    boolean isEnabled;



    @Email
    @NotEmpty
    private String email;



    @OneToMany(cascade = CascadeType.ALL)
    //Include this to join a column to the education table,otherwise an (unnecessary) join table will be created.
    @JoinColumn(name="person_id")
    private Set<Education> educationList;

    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserRole> roleList;

    @ManyToMany
    private Set <Jobs> jobs;

    //What is the difference in using the defaults for the annotations?
    @ManyToMany(cascade=CascadeType.ALL)
    private Set <Skills> mySkills;

    public Person()
    {
        //Instantiate new HashSets to avoid null pointer exceptions
        this.educationList = new HashSet();
        this.roleList =  new HashSet();
        this.jobs= new HashSet();
        this.mySkills= new HashSet();
    }

    public Person(String email,String password,String firstName,String lastName, boolean isEnabled,String username )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.email = email;

        //Instantiate new HashSets to avoid null pointer exceptions
        //This method will not call the default constructor, so if you have
        //functions to call again, you can make another method or copy them here
        this.educationList = new HashSet();
        this.roleList =  new HashSet();
        this.jobs= new HashSet();
        this.mySkills= new HashSet();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(Set<Education> educationList) {
        this.educationList = educationList;
    }

    public Set<UserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<UserRole> roleList) {
        this.roleList = roleList;
    }

    public Set<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Jobs> jobs) {
        this.jobs = jobs;
    }

    public Set<Skills> getMyskills() {
        return mySkills;
    }

    public void setMyskills(Set<Skills> myskills) {
        this.mySkills = myskills;
    }

    public void addEducation(Education e)
    {
        this.educationList.add(e);
    }

    public void addRole(UserRole r){this.roleList.add(r);}

    public void addJobs(Jobs j){
        this.jobs.add(j);
    }

    public void addSkills(Skills s)
    {
        this.mySkills.add(s);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skills> getMySkills() {
        return mySkills;
    }

    public void setMySkills(Set<Skills> mySkills) {
        this.mySkills = mySkills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
