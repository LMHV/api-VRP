package edu.unsj.fcefn.lcc.optimizacion.api.services;

import edu.unsj.fcefn.lcc.optimizacion.api.algorithm.RoutingProblem;
import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.FrameDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.StopDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.model.mappers.AlgorithmMapper;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.Permutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlgorithmService {

    @Autowired
    StopsService stopsService;

    @Autowired
    AlgorithmMapper algorithmMapper;

    List<StopDTO> stops;

    @PostConstruct
    private void init(){
        stops = stopsService
                .findAll()
                .stream()
                .sorted(Comparator.comparing(StopDTO::getRanking).reversed())
                .collect(Collectors.toList())
                .subList(0, 20);
    }

    public List<StopDTO> getStops(){
        return stops;
    }

    public List<FrameDTO> execute(){
        NondominatedPopulation population = new Executor()
                .withAlgorithm("NSGAII")
                .withProblemClass(RoutingProblem.class, 1, 2)
                .withMaxEvaluations(10000)
                .run();

        return StreamSupport
                .stream(population.spliterator(), false)
                .map(solution -> (Permutation)solution.getVariable(0))
                .map(permutation -> algorithmMapper.permutationToDTO(permutation, stops))
                .findFirst()
                .orElse(new ArrayList<>());
    }
}
