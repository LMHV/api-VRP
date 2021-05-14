package edu.unsj.fcefn.lcc.optimizacion.api.algorithm;

import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.FrameDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.model.domain.StopDTO;
import edu.unsj.fcefn.lcc.optimizacion.api.services.AlgorithmService;
import edu.unsj.fcefn.lcc.optimizacion.api.services.FramesService;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.problem.AbstractProblem;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


public class RoutingProblem extends AbstractProblem {

    @Autowired
    AlgorithmService algorithmService;

    @Autowired
    FramesService framesService;

    public RoutingProblem(Integer numberOfVariables, Integer numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
    }

    @Override
    public void evaluate(Solution solution){
        solution.setObjective(0, totalPrice((Permutation) solution.getVariable(0)));
        solution.setObjective(1, totalTime((Permutation) solution.getVariable(0)));

    }

    private double totalPrice(Permutation permutation){

        List<StopDTO> stops = algorithmService.getStops();

        double totalPrice = 0;

        for (int i = 0; i < permutation.size() - 1; i++){
            StopDTO departureStop = stops.get(permutation.get(i));
            StopDTO arrivalStop = stops.get(permutation.get(i + 1));

            List<FrameDTO> frames = framesService.findByIdDepartureStopAndIdArrivalStop(departureStop.getId(), arrivalStop.getId());

            FrameDTO bestPriceFrame = frames
                    .stream()
                    .min(Comparator.comparing(FrameDTO::getPrice))
                    .orElse(null);


            if(Objects.isNull(bestPriceFrame)){
                return Double.MAX_VALUE;
            }

            totalPrice += bestPriceFrame.getPrice();
        }
        return totalPrice;
    }

    private double totalTime(Permutation permutation){

        List<StopDTO> stops = algorithmService.getStops();

        double totalTime = 0;
        double waitingTime= 0;

        for (int i = 0; i < permutation.size() - 1; i++){
            StopDTO departureStop = stops.get(permutation.get(i));
            StopDTO arrivalStop = stops.get(permutation.get(i + 1));

            List<FrameDTO> frames = framesService
                    .findByIdDepartureStopAndIdArrivalStop(departureStop.getId(), arrivalStop.getId());

            Map<Integer, Long> mapTime = getTimeMaps(frames);
            Map.Entry<Integer, Long> frameIdToArrival = mapTime
                    .entrySet()
                    .stream()
                    .min(Map.Entry.comparingByValue())
                    .orElse(null);


            if(Objects.isNull(frameIdToArrival)){
                return Double.MAX_VALUE;
            }

            FrameDTO frameDTO = frames
                    .stream()
                    .filter(frame -> frame.getId().equals(frameIdToArrival.getKey()))
                    .findFirst()
                    .orElse(null);

            if(Objects.isNull(frameDTO)){
                return Double.MAX_VALUE;
            }

            totalTime += frameIdToArrival.getValue();

            /*
            Desde este punto esta creado por mi
             */

            StopDTO departureStopNextTravel = stops.get(permutation.get(i + 1));
            StopDTO arrivalStopNextTravel = stops.get(permutation.get(i + 2));

            List<FrameDTO> secondTravelFrames = framesService.
                    findByIdDepartureStopAndIdArrivalStop(departureStopNextTravel.getId(), arrivalStopNextTravel.getId());

            Map<Integer, Long> secondMapTime = getTimeMaps(frames);
            Map.Entry<Integer, Long> secondFrameIdToArrival = secondMapTime
                    .entrySet()
                    .stream()
                    .min(Map.Entry.comparingByValue())
                    .orElse(null);

            FrameDTO secondFrameDTO = frames
                    .stream()
                    .filter(frame -> frame.getId().equals(secondFrameIdToArrival.getKey()))
                    .findFirst()
                    .orElse(null);


            // Sumar las 2 fechas
            // waitingTime += LocalTime.Sum(frameDTO.getArrivalDatetime() + secondFrameDTO.getDepartureDatetime());

            /*
            Hasta aca
             */
            totalTime += waitingTime;
        }
        return totalTime;
    }


    private Map<Integer, Long> getTimeMaps(List<FrameDTO> frames){

        Map<Integer, Long> mapTime = new HashMap<>();
        for(FrameDTO frame : frames){
            if(frame.getDepartureDatetime().isBefore(frame.getArrivalDatetime())){
                Long timeToArrival = Duration.between(frame.getDepartureDatetime(), frame.getArrivalDatetime()).getSeconds();
                mapTime.put(frame.getId(), timeToArrival);
            }else{
                Long timeToArrivalRange1 = Duration.between(frame.getDepartureDatetime(), LocalTime.MIDNIGHT).getSeconds();
                Long timeToArrivalRange2 = Duration.between(LocalTime.MIDNIGHT, frame.getDepartureDatetime()).getSeconds();
                Long timeToArrival = timeToArrivalRange1 + timeToArrivalRange2;

                mapTime.put(frame.getId(), timeToArrival);
            }
        }

        return mapTime;
    }

    @Override
    public Solution newSolution(){
        Solution solution = new Solution(1,2);
        solution.setVariable(0, EncodingUtils.newPermutation(20));
        return solution;
    }
}
