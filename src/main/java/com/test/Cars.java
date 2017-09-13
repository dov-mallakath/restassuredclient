package com.test;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-11
 */
@XmlRootElement(name = "cars")
public class Cars {

    private List<Car> car;

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }
}
