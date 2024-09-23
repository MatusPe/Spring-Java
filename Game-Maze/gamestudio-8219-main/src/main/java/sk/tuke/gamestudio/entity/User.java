package sk.tuke.gamestudio.entity;


import javax.persistence.*;

@Entity
@Table(name = "\"user\"")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long ident;
    private String username;
    private String password;
    private String Email;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public User(String Username, String password, String Email) {
        this.username = Username;
        this.password = password;
        this.Email = Email;
    }



    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public Long getIdent() {
        return ident;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
