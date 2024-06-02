package com.example.backend.entity;

import com.example.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "app_user", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "contact_info", nullable = true)
    private String contactInfo;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "details_configured", nullable = false, columnDefinition = "boolean default false")
    private Boolean detailsConfigured;

    @Column(name = "picture_url", nullable = true)
    private String pictureUrl;

    //it will be coded
    @Column(name = "bank_account", nullable = true)
    private String bankAccount;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Payment> myPayments = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Payment> receivedPayments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Space> spaces = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Message> messageSent = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Message> messageReceived = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
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
