package com.intern.app.qeema.model.entities;


import com.intern.app.qeema.model.DtoModels.DesiredTrack;
import com.intern.app.qeema.model.DtoModels.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Intern")
public class Intern implements UserDetails {
@Id
@SequenceGenerator(
        name = "INTERN_sequence",
        sequenceName ="intern_sequence",
        allocationSize = 1
)
@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "INTERN_sequence"
)
    private int id;
    @Column(name = "full_name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
//    @Email
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "gpa",nullable = false)
    private double gpa;

    @Column(name = "cv_url",nullable = false,columnDefinition = "TEXT")
    private String cvUrl;

    @Column(name = "university",nullable = false)
    private String university;

    @Column(name = "is_accepted",nullable = false, columnDefinition = "boolean default false")
    private boolean isAccepted ;

    @Column(name = "desiredTrack",nullable = false)
   private DesiredTrack desiredTrack;

    private Role role ;


    @OneToMany(fetch = FetchType.LAZY)
    private List<Projects> ProjectsInterns;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
