package edu.unsj.fcefn.lcc.optimizacion.api.controllers;

import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.StopDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.services.StopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopsController {

    @Autowired
    StopsService stopsService;

    @GetMapping(value = "")
    public List<StopDTO> findAll(){
        return stopsService.findAll();
    }

    @GetMapping(value = "{id}")
    public StopDTO find(@PathVariable("id") Integer id){
        return stopsService.find(id);
    }

    @PostMapping(value = "")
    public StopDTO add(@RequestBody StopDTO transportCompanyDTO){
        return stopsService.add(transportCompanyDTO);
    }

    @PutMapping(value = "")
    public StopDTO edit(@RequestBody StopDTO transportCompanyDTO){
        return stopsService.edit(transportCompanyDTO);
    }

    @DeleteMapping(value = "{id}")
    public StopDTO delete(@PathVariable("id") Integer id) throws Exception {
        return stopsService.delete(id);
    }
}