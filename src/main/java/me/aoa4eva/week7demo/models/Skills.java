package me.aoa4eva.week7demo.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(mappedBy="mySkills")
    private Set<Person> peopleSkills;

    @ManyToMany(mappedBy="jobSkills")
    private Set <Jobs> jskills;

    private String skill;

    private String description;

    private int rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Skills()
    {

    }

    public Skills(String theSkill)
    {
        this.skill=theSkill;
    }
    public Set<Person> getPeopleSkills() {
        return peopleSkills;
    }

    public void setPeopleSkills(Set<Person> peopleSkills) {
        this.peopleSkills = peopleSkills;
    }

    public Set<Jobs> getJskills() {
        return jskills;
    }

    public void setJskills(Set<Jobs> jskills) {
        this.jskills = jskills;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
