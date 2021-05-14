package edu.unsj.fcefn.lcc.optimizacion.api.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "frames")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="price")
    private Float price;

    @Column(name ="category")
    private String category;

    @Column(name ="departure_datetime")
    private LocalTime departureDatetime;

    @Column(name ="arrival_datetime")
    private LocalTime arrivalDatetime;

    @OneToOne
    @JoinColumn(name = "id_transport_company")
    @NotFound(action = NotFoundAction.IGNORE)
    private TransportCompanyEntity transportCompany;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name ="id_stop_departure")
    private StopEntity departureStop;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name ="id_stop_arrival")
    private StopEntity arrivalStop;

}