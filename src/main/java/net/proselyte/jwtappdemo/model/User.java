package net.proselyte.jwtappdemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


/**
 * Simple domain object that represents application user.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

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

    @JsonIgnore
    public List<Booking> getSortedBookingHistory () {

        for (int i = 0; i < bookingList.size(); i++) {
            for (int j = i + 1; j < bookingList.size(); j++) {
                if (bookingList.get(i).getBookingDate().compareTo(bookingList.get(j).getBookingDate()) > 0 || (bookingList.get(i).getBookingDate().compareTo(bookingList.get(j).getBookingDate()) == 0)) {

                }

            }
        }
        return bookingList;
    }


}
