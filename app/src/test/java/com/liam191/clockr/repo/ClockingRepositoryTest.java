package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstantExecutorExtension.class)
public class ClockingRepositoryTest {

    // TODO: Refactor ClockingRepository to use a factory instead of singleton
    //          - Public factory and private constructor?
    //              - Other classes use factory (which calls constructor)
    //              - Tests use constructor
    // TODO: Decide on identity/primary key for Clockings/DAOs
    // TODO: Try using in-memory RoomDatabase for testing.
    // TODO: Try using both a collection-like interface *and* a MutableLiveData
    //          - See which way works better
    //          - For MutableLiveData Lists, because clockings are immutable there is no need for
    //            UPDATE operations. Everything is either INSERT or DELETE. We can either delete
    //            and add the entire list for a given day, or have a fancier List diff to only
    //            update what has changed.

    @Test
    public void testGetAllForGivenDate_withNoData(){
        MutableLiveData<List<Clocking>> clockings = new ClockingRepository()
                .getAllForDate(new Date(2020, 3, 3));

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testAddClocking(){
        Date testDay = new Date(2020, 3, 3);
        MutableLiveData<List<Clocking>> clockings = new ClockingRepository().getAllForDate(testDay);

        List<Clocking> clockingsBeforeUpdate = clockings.getValue();
        clockingsBeforeUpdate.add(new Clocking.Builder("Test", 34, testDay).build());
        clockings.postValue(clockingsBeforeUpdate);

        List<Clocking> clockingsAfterUpdate = clockings.getValue();
        assertEquals(clockingsBeforeUpdate, clockingsAfterUpdate);
    }

    @Test
    public void testRemoveClockings_withOneClocking(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay)
                .build();

        repository.add(clocking1);
        repository.remove(clocking1);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withThreeClockings(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();
        Clocking clocking2 = new Clocking.Builder("TestClocking2", 47, testDay).build();
        Clocking clocking3 = new Clocking.Builder("TestClocking3", 23, testDay).build();

        repository.add(clocking1);
        repository.add(clocking2);
        repository.add(clocking3);

        repository.remove(clocking1);
        assertEquals(2, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withNoClockings(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();

        repository.remove(clocking1);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withNull(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);

        repository.remove(null);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }


}