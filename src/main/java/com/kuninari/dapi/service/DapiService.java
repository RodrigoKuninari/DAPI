package com.kuninari.dapi.service;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.domain.State;
import com.kuninari.dapi.repository.DapiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for handling logic related to Device management.
 */
@Service
public class DapiService {

    /**
     * Repository for accessing data from the database.
     */
    @Autowired
    private DapiRepository dapiRepository;

    /**
     * Persists a new device in the database.
     *
     * @param device the device to be created
     * @return the persisted device
     */
    public Device createDevice(Device device) {
        return dapiRepository.save(device);
    }

    /**
     * Updates an existing device.
     * <ul>
     *     <li>If the device is not in use, updates name, brand, and state.</li>
     *     <li>If the device is in use, only updates state.</li>
     * </ul>
     *
     * @param id         the ID of the device to update
     * @param newDevice  the new device data to apply
     * @return an {@link Optional} containing the updated device if found, or empty if not found
     */
    @Transactional
    public Optional<Device> updateDevice(Long id, Device newDevice) {
        return dapiRepository.findById(id)
                .map(existingDevice -> {
                    if (!State.IN_USE.equals(existingDevice.getState())) {
                        existingDevice.setName(newDevice.getName());
                        existingDevice.setBrand(newDevice.getBrand());
                    }
                    existingDevice.setState(newDevice.getState());
                    return dapiRepository.save(existingDevice);
                });
    }

    /**
     * Retrieves a single device by ID.
     *
     * @param id the ID of the device
     * @return an {@link Optional} containing the device if found, or empty if not found
     */
    public Optional<Device> getDevice(Long id) {
        return dapiRepository.findById(id);
    }

    /**
     * Retrieves all devices.
     *
     * @return a list of all devices
     */
    public List<Device> getAllDevices() {
        return dapiRepository.findAll();
    }

    /**
     * Retrieves devices by brand.
     *
     * @param brand the brand name to filter by
     * @return a list of matching devices
     */
    public List<Device> getDevicesByBrand(String brand) {
        return dapiRepository.findByBrand(brand);
    }

    /**
     * Retrieves devices by state.
     *
     * @param state the state to filter by
     * @return a list of matching devices
     */
    public List<Device> getDevicesByState(State state) {
        return dapiRepository.findByState(state);
    }

    /**
     * Deletes a device if it exists and is not in use.
     *
     * @param id the ID of the device to delete
     * @return {@code true} if the device was deleted, {@code false} if not found or in use
     */
    @Transactional
    public boolean deleteDevice(Long id) {
        Optional<Device> device = dapiRepository.findById(id);
        if (device.isPresent()) {
            if (State.IN_USE.equals(device.get().getState()))  {
                return false;
            }
            dapiRepository.delete(device.get());
            return true;
        }
        return false;
    }
}
