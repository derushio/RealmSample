package jp.itnav.realmsample.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by derushio on 2017/05/13.
 */

public class UserRObjects extends RealmObject {

    private String name;
    private int age;

    @Ignore
    private int sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
