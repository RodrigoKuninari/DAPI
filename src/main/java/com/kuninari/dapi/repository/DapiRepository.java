package com.kuninari.dapi.repository;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DapiRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(State state);
}
