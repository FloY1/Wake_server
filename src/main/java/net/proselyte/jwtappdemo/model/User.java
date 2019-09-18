package net.proselyte.jwtappdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import net.proselyte.jwtappdemo.model.enums.Status;

import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "users")
@Data
@ToString
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String userName;
    private String password;
    private Long seasonTicket;


    @Enumerated(EnumType.STRING)
    private Status status;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<TicketStory> ticketStories ;




}
