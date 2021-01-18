package pl.lodz.p.it.isdp.wm.dto;

import java.util.Objects;
import pl.lodz.p.it.isdp.wm.model.AccessLevel;

public class AccountDTO implements Comparable<AccountDTO> {

    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String question;
    private String answer;
    private boolean active;
    private String oldPassword;
    private AccessLevel accessLevel;
    private AccessLevel oldAccessLevel;
    

    public AccountDTO() {
    }

    public AccountDTO(String login, String name, String surname, String email, boolean active) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.active = active;
    }

    public AccountDTO(String name, String surname, String email, String login, String password, String question, String answer, boolean active) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.active = active;
    }

    public AccountDTO(String login, String name, String surname, String email) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccessLevel getOldAccessLevel() {
        return oldAccessLevel;
    }

    public void setOldAccessLevel(AccessLevel oldAccessLevel) {
        this.oldAccessLevel = oldAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.surname);
        hash = 43 * hash + Objects.hashCode(this.email);
        hash = 43 * hash + Objects.hashCode(this.login);
        hash = 43 * hash + Objects.hashCode(this.password);
        hash = 43 * hash + Objects.hashCode(this.question);
        hash = 43 * hash + Objects.hashCode(this.answer);
        hash = 43 * hash + Objects.hashCode(this.oldPassword);
        hash = 43 * hash + Objects.hashCode(this.accessLevel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountDTO other = (AccountDTO) obj;
        return true;
    }

    @Override
    public String toString() {
        return "AccountDTO: " + login;
    }

    @Override
    public int compareTo(AccountDTO o) {
        return this.surname.compareTo(o.surname);
    }
}
