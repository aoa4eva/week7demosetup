package me.aoa4eva.week7demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;

    private String password;



    @OneToMany(cascade = CascadeType.ALL)
    //Include this to join a column to the education table,otherwise an (unnecessary) join table will be created.
    @JoinColumn(name="person_id")
    private Set<Education> educationList;

    @ManyToMany(cascade=CascadeType.ALL)
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
}
