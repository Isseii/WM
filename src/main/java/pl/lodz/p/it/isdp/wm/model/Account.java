package pl.lodz.p.it.isdp.wm.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LEVEL_OF_ACCESS", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(AccessLevel.AccessLevelKeys.ACCOUNT_KEY)
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = {
    @UniqueConstraint(name = "LOGIN_UNIQUE", columnNames ="LOGIN"), @UniqueConstraint(name="EMAIL_UNIQUE", columnNames = "EMAIL")
})
@TableGenerator(name = "WorkerAccountGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "WorkerAccountGen")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    ,
    @NamedQuery(name = "Account.findNewRegisteredAccount", query = "SELECT a FROM NewRegisteredAccount a")
    ,
    @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :lg")
    ,
    @NamedQuery(name = "Account.findAuthorizedAccount", query = "SELECT a FROM Account a WHERE a.authorizedBy IS NOT null")
})
public class Account extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "WorkerAccountGenerator")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "QUESTION", nullable = false)
    private String question;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ANSWER", nullable = false)
    private String answer;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LOGIN", nullable = false, updatable = false)
    private String login;

    @NotNull
    @Size(min = 64, max = 64)
    @Column(name = "PASSWORD", nullable = false, updatable = true)
    private String password;

    @NotNull
    @JoinColumn(name = "AUTHORIZED_BY", nullable = true, updatable = false)
    @OneToOne
    private AdministrationAccount authorizedBy;

    @NotNull
    @JoinColumn(name = "MODIFICATED_BY", nullable = true)
    @OneToOne
    private AdministrationAccount modificatedBy;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    public Account() {
    }

    public Account(Long id, String name, String surname, String email, String question, String answer, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                stringBuffer.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = stringBuffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AdministrationAccount getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(AdministrationAccount authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public AdministrationAccount getModificatedBy() {
        return modificatedBy;
    }

    public void setModificatedBy(AdministrationAccount modificatedBy) {
        this.modificatedBy = modificatedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
