package com.tec;

import com.tec.dto.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class DataRepositoryTest {
    @Test
    void itCallsOnlySaveFacilityWhenDataIsSavedInTheDatabase() {
        Location location = mock(Location.class);
        DataRepository repository = mock(DataRepository.class);
        ArrayList<Location> locations = new ArrayList<>(){{ add(location); }};

        doCallRealMethod().when(repository).saveAllLocations(any());
        when(repository.saveFacility(any())).thenReturn(true);
        when(repository.saveLocation(any())).thenReturn(true);
        doNothing().when(repository).saveZone(any());

        repository.saveAllLocations(locations);

        verify(repository, times(1)).saveFacility(any());
        verify(repository, times(0)).saveLocation(any());
        verify(repository, times(0)).saveZone(any());
    }

    @Test
    void itCallsSaveLocationWhenFacilityIsNotSavedInTheDatabase() {
        Location location = mock(Location.class);
        DataRepository repository = mock(DataRepository.class);
        ArrayList<Location> locations = new ArrayList<>(){{ add(location); }};

        doCallRealMethod().when(repository).saveAllLocations(any());
        when(repository.saveFacility(any())).thenReturn(false);
        when(repository.saveLocation(any())).thenReturn(true);
        doNothing().when(repository).saveZone(any());

        repository.saveAllLocations(locations);

        verify(repository, times(2)).saveFacility(any());
        verify(repository, times(1)).saveLocation(any());
        verify(repository, times(0)).saveZone(any());
    }

    @Test
    void itCallsSaveZonesWhenLocationIsNotSavedInTheDatabase() {
        Location location = mock(Location.class);
        DataRepository repository = mock(DataRepository.class);
        ArrayList<Location> locations = new ArrayList<>(){{ add(location); }};

        doCallRealMethod().when(repository).saveAllLocations(any());
        when(repository.saveFacility(any())).thenReturn(false);
        when(repository.saveLocation(any())).thenReturn(false);
        doNothing().when(repository).saveZone(any());

        repository.saveAllLocations(locations);

        verify(repository, times(2)).saveFacility(any());
        verify(repository, times(2)).saveLocation(any());
        verify(repository, times(1)).saveZone(any());
    }
}
