package com.example.accessingdatajpa.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GPSTest {

    @Data
    static class GPS {
        String header;
        List<Car> cars;
    }
    @Data
    static class Car{
        String vin;
        List<Position> positions;
    }
    @Data
    static class Position{
        double longitude;
        double latitude;
        double timestamp;
    }
    static double distance(Position p1, Position p2){
        return Math.sqrt(Math.pow(p1.getLatitude()-p2.getLatitude(),2)+Math.pow(p1.getLongitude()-p2.getLongitude(),2));
    }
    @Test
    void of() {
        GPS gps;
        try {
            String json = """
                    {
                        "header":"hello",
                        "cars":[{
                            "vin":"123",
                            "positions":[{
                                "longitude":20,
                                "latitude":10,
                                "timestamp":1
                            },{
                                "longitude":30,
                                "latitude":10,
                                "timestamp":2
                            },{
                                "longitude":30,
                                "latitude":20,
                                "timestamp":3
                            }]
                        },{
                            "vin":"124",
                            "positions":[{
                                "longitude":30,
                                "latitude":10,
                                "timestamp":3
                            },{
                                "longitude":30,
                                "latitude":10,
                                "timestamp":5
                            },{
                                "longitude":30,
                                "latitude":50,
                                "timestamp":4
                            }]
                        }]
                    }
                    """;
            gps = new ObjectMapper().readValue(json, GPS.class);
            System.out.println(gps.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, Double> collect = gps.cars.stream()
                .collect(Collectors.toMap(Car::getVin, car->{
                    List<Position> positions = car.getPositions();
                    // Double sum = Double.valueOf(0);
                    // positions.stream().sorted(Comparator.comparing(Position::getTimestamp)).reduce(sum,TitleLevelTest::distance,(a,b)->{return a;});
                    return IntStream.range(0, positions.size() - 1)
                            .mapToDouble(i -> distance(positions.get(i), positions.get(i + 1)))
                            .sum();
                }));
        System.out.println(collect);
    }
}