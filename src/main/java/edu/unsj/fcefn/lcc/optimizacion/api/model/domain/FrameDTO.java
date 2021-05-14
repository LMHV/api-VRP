package edu.unsj.fcefn.lcc.optimizacion.api.model.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrameDTO {

    private Integer id;
    private Float price;
    private String category;
    private LocalTime departureDatetime;
    private LocalTime arrivalDatetime;
    private TransportCompanyDTO transportCompany;
    private StopDTO departureStop;
    private StopDTO arrivalStop;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalTime getDepartureDatetime() { return departureDatetime; }
    public void setDepartureDatetime(LocalTime departureDatetime) { this.departureDatetime = departureDatetime; }

    public LocalTime getArrivalDatetime() { return arrivalDatetime; }
    public void setArrivalDatetime(LocalTime arrivalDatetime) { this.arrivalDatetime = arrivalDatetime; }

    public TransportCompanyDTO getTransportCompany() { return transportCompany; }

    public void setTransportCompany(TransportCompanyDTO transportCompany) { this.transportCompany = transportCompany; }

    public StopDTO getDepartureStop() { return departureStop; }

    public void setDepartureStop(StopDTO departureStop) { this.departureStop = departureStop; }

    public StopDTO getArrivalStop() { return arrivalStop; }

    public void setArrivalStop(StopDTO arrivalStop) { this.arrivalStop = arrivalStop; }
}
