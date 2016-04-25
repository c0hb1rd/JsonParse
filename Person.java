package com.hongbao.c0hb1rd.testsch;

/**
 * Created by c0hb1rd on 4/25/16.
 * @author c0hb1rd
 */
public class Person {
    private String name;
    private String sex;
    private String idCard;
    private String school;
    private String tech;
    String[] key = {"Name: ", "Sex: ", "IdCard: ", "School: ", "Tech: "};

    public String getName() {
        return name;
    }

    public String getTech() {
        return tech;
    }

    public String getSex() {
        return sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Person[name:" + name + ", sex:" + sex + ", idCard:" + idCard + ", school:" + school + ", tech:" + tech +"]";
    }

    public String getValue(String value) {
        switch(value) {
            case "Name: ":
                return name;
            case "Sex: ":
                return sex;
            case "Tech: ":
                return tech;
            case "School: ":
                return school;
            case "IdCard: ":
                return idCard;
            default:
                return "not this value";
        }
    }
}
