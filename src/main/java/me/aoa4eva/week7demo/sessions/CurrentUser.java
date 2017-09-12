package me.aoa4eva.week7demo.sessions;

import me.aoa4eva.week7demo.models.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS,value="session")
public class CurrentUser {
    private Person p;

    public CurrentUser()
    {
        p = new Person();
    }

    public Person getP() {
        return p;
    }

    public void setP(Person p) {
        this.p = p;
    }

}
