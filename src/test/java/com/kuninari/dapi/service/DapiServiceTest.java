package com.kuninari.dapi.service;

import com.kuninari.dapi.domain.Device;
import com.kuninari.dapi.domain.State;
import com.kuninari.dapi.repository.DapiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DapiServiceTest {

    @Mock
    private DapiRepository repository;

    @InjectMocks
    private DapiService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDevice() {
        Device device = new Device();
        device.setName("Pixel 9");
        when(repository.save(device)).thenReturn(device);
        Device result = service.createDevice(device);
        assertEquals("Pixel 9", result.getName());
        verify(repository, times(1)).save(device);
    }

    @Test
    void testUpdateDevice_allFieldsUpdatable() {
        Device existing = new Device(1L, "iPhone 16", "Apple", State.AVAILABLE, null);
        Device input = new Device(1L, "Pixel 9", "Google", State.IN_USE, null);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);
        Optional<Device> updated = service.updateDevice(1L, input);
        assertTrue(updated.isPresent());
        assertEquals("Pixel 9", updated.get().getName());
        assertEquals("Google", updated.get().getBrand());
        assertEquals(State.IN_USE, updated.get().getState());
    }

    @Test
    void testUpdateDevice_inUseOnlyUpdatesState() {
        Device existing = new Device(2L, "Pixel 9", "Google", State.IN_USE, null);
        Device input = new Device(2L, "iPhone 16", "Apple", State.AVAILABLE, null);
        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);
        Optional<Device> updated = service.updateDevice(2L, input);
        assertTrue(updated.isPresent());
        assertEquals("Pixel 9", updated.get().getName());
        assertEquals("Google", updated.get().getBrand());
        assertEquals(State.AVAILABLE, updated.get().getState());
    }

    @Test
    void testUpdateDevice_notFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        Optional<Device> result = service.updateDevice(999L, new Device());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteDevice_success() {
        Device device = new Device(3L, "iPhone 16", "Apple", State.AVAILABLE, null);
        when(repository.findById(3L)).thenReturn(Optional.of(device));
        boolean result = service.deleteDevice(3L);
        assertTrue(result);
        verify(repository).delete(device);
    }

    @Test
    void testDeleteDevice_inUse() {
        Device device = new Device(4L, "Pixel 9", "Google", State.IN_USE, null);
        when(repository.findById(4L)).thenReturn(Optional.of(device));
        boolean result = service.deleteDevice(4L);
        assertFalse(result);
        verify(repository, never()).delete(any());
    }

    @Test
    void testDeleteDevice_notFound() {
        when(repository.findById(404L)).thenReturn(Optional.empty());
        boolean result = service.deleteDevice(404L);
        assertFalse(result);
    }
}
