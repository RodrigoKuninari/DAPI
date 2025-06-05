package com.kuninari.dapi.repository;

import com.kuninari.dapi.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DeviceRepository extends JpaRepository<Device, Long> {
}
