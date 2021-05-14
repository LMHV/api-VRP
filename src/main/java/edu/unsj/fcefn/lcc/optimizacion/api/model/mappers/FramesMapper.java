package edu.unsj.fcefn.lcc.optimizacion.api.model.mappers;

import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.FrameDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.model.entities.FrameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FramesMapper {

    @Autowired
    TransportCompaniesMapper transportCompanyMapper;

    @Autowired
    StopsMapper stopsMapper;

    public FrameDTO entityToDTO(FrameEntity frameEntity){
        FrameDTO frameDTO = new FrameDTO();

        frameDTO.setId(frameEntity.getId());
        frameDTO.setTransportCompany(transportCompanyMapper.entityToDTO(frameEntity.getTransportCompany()));
        frameDTO.setDepartureStop(stopsMapper.entityToDTO(frameEntity.getDepartureStop()));
        frameDTO.setArrivalStop(stopsMapper.entityToDTO(frameEntity.getArrivalStop()));
        frameDTO.setPrice(frameEntity.getPrice());
        frameDTO.setCategory(frameEntity.getCategory());
        frameDTO.setDepartureDatetime(frameEntity.getDepartureDatetime());
        frameDTO.setArrivalDatetime(frameEntity.getArrivalDatetime());

        return frameDTO;
    }

    public FrameEntity dtoToEntity(FrameDTO frameDTO){
        FrameEntity frameEntity = new FrameEntity();

        frameEntity.setId(frameDTO.getId());
        frameEntity.setTransportCompany(transportCompanyMapper.dtoToEntity(frameDTO.getTransportCompany()));
        frameEntity.setDepartureStop(stopsMapper.dtoToEntity(frameDTO.getDepartureStop()));
        frameEntity.setArrivalStop(stopsMapper.dtoToEntity(frameDTO.getArrivalStop()));
        frameEntity.setPrice(frameDTO.getPrice());
        frameEntity.setCategory(frameDTO.getCategory());
        frameEntity.setDepartureDatetime(frameDTO.getDepartureDatetime());
        frameEntity.setArrivalDatetime(frameDTO.getArrivalDatetime());

        return frameEntity;
    }
}

