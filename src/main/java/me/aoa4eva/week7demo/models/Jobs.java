package me.aoa4eva.week7demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    //This will make job_id the second column in the join table, and thus the 'secondary' part of the many to many relationship
    //mappedBy is used primarily for searching - the CrudRepository knows to look for the appropriate relationship
    @ManyToMany(mappedBy="jobs")
    private Set<Person> people;

    @ManyToMany(cascade=CascadeType.ALL)
    private Set <Skills> jobSkills;

    public Jobs()
    {
        this.people=new HashSet<Person>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public void addPerson(Person p)
    {

        this.people.add(p);
    }
    public void addSkill(Skills s){
        this.jobSkills.add(s);
    }
}
