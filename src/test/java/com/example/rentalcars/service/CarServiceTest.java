package com.example.rentalcars.service;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    private CarService carService;
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void testFindCarsByMarkWithNullFilter() {

        List<CarModel> expectedCars = createSampleCarList();

        when(carService.getCarList1()).thenReturn(expectedCars);

        List<CarModel> result = carService.findCarsByMark(null);

        assertEquals(expectedCars, result);
    }

    @Test
    void testFindCarsByMarkWithEmptyFilter() {

        List<CarModel> expectedCars = createSampleCarList();

        when(carService.getCarList1()).thenReturn(expectedCars);

        List<CarModel> result = carService.findCarsByMark("");

        assertEquals(expectedCars, result);
    }

    @Test
    void testFindCarsByMarkWithFilter() {
        List<CarModel> expectedCars = createSampleFilteredCarList();
        String filterText = "Toyota";

        when(carService.getCarsByMark(filterText)).thenReturn(expectedCars);

        List<CarModel> result = carService.findCarsByMark(filterText);

        assertEquals(expectedCars, result);
    }

    private List<CarModel> createSampleCarList() {
        List<CarModel> cars = new ArrayList<>();
        return cars;
    }

    private List<CarModel> createSampleFilteredCarList() {
        List<CarModel> filteredCars = new ArrayList<>();
        return filteredCars;
    }

    @Test
    public void testSetCarStatus() {
        CarRepository carRepository = mock(CarRepository.class);

        CarService carService = new CarService(carRepository);

        //given


        Long carId = 1L;
        CarStatus newCarStatus = CarStatus.AVAILABLE;

        CarModel carFromRepository = new CarModel();
        carFromRepository.setId(carId);
        carFromRepository.setAvailability(CarStatus.UNAVAILABLE); // Początkowy status
        when(carRepository.findById(carId)).thenReturn(Optional.of(carFromRepository));

        //when


        carService.setCarStatus(carId, newCarStatus);

//then

        verify(carRepository).findById(carId);

        ArgumentCaptor<CarModel> carArgumentCaptor = ArgumentCaptor.forClass(CarModel.class);
        verify(carRepository).save(carArgumentCaptor.capture());

        CarModel capturedCar = carArgumentCaptor.getValue();

        assertEquals(newCarStatus, capturedCar.getAvailability());
    }

    @Test
    void testGetAvailableCars(){

        //given

        CarModel car1  = new CarModel(1L, "Toyota", "Supra", BigDecimal.valueOf(1000), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.MANUAL,5,3, FuelType.PETROL,"trunk",CarStatus.AVAILABLE,"RED",4000,2015);
        CarModel car2  = new CarModel(2L, "Toyota", "Corolla", BigDecimal.valueOf(1000), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.MANUAL,5,3, FuelType.PETROL,"trunk",CarStatus.UNAVAILABLE,"RED",4000,2015);
        CarModel car3  = new CarModel(3L, "Toyota", "Rav4", BigDecimal.valueOf(1000), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.MANUAL,5,3, FuelType.PETROL,"trunk",CarStatus.AVAILABLE,"RED",4000,2015);

        List<CarModel> carList = new ArrayList<>();
        carList.add(car1);
        carList.add(car2);
        carList.add(car3);

        //when

        CarService carService = new CarService(carRepository);
        List<CarModel> availableCars = carService.getAvailableCars(carList);

        //then

        assertEquals(2, availableCars.size());
        assertTrue(availableCars.contains(car1));
        assertTrue(availableCars.contains(car3));
        assertFalse(availableCars.contains(car2));
    }

}