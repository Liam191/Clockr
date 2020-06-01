package com.liam191.clockr.repo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingEntity;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SmallTest
public class ClockingRepositoryTest {

    private ClockrDatabase testDb;
    private ClockingDao clockingTestDao;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Before
    public void createTestDb(){
        Context appContext = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(appContext, ClockrDatabase.class).build();
        clockingTestDao = testDb.clockingDao();
    }

    @After
    public void teardownTestDb(){
        testDb.close();
    }

    @Test
    public void testAdd(){
        ClockingRepository repository = new ClockingRepository(clockingTestDao);

        Clocking clocking = new Clocking.Builder("TestClocking1").build();
        repository.insert(clocking);

        assertTrue(clockingTestDao.getAll().size() == 1);
        assertTrue(clockingTestDao.getAll().contains(ClockingRepository.Mapper.map(clocking)));
    }

    @Test
    public void testAdd_WithNull(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Cannot insert null Clocking");

        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        repository.insert(null);
    }

    @Test
    public void testDelete(){
        ClockingRepository repository = new ClockingRepository(clockingTestDao);

        Clocking clocking = new Clocking.Builder("TestClocking1").build();
        repository.insert(clocking);
        assertTrue(clockingTestDao.getAll().size() == 1);

        repository.delete(clocking);
        assertTrue(clockingTestDao.getAll().size() == 0);
    }

    @Test
    public void testDelete_WithNull(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Cannot delete null Clocking");

        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        repository.delete(null);
    }


    @Test
    public void testRemove_WithMultipleRows(){
        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        Clocking clocking = new Clocking.Builder("TestClocking1").build();

        repository.insert(clocking);
        repository.insert(clocking);
        repository.insert(clocking);
        repository.insert(clocking);
        repository.insert(clocking);

        assertEquals(clockingTestDao.getAll().size(), 5);
        repository.delete(clocking);
        assertEquals(clockingTestDao.getAll().size(), 4);
    }

    @Test
    public void testReplace(){
        ClockingRepository repository = new ClockingRepository(clockingTestDao);

        Clocking clocking1 = new Clocking.Builder("TestClockingBeforeReplace").build();
        repository.insert(clocking1);
        assertTrue(clockingTestDao.getAll().contains(ClockingRepository.Mapper.map(clocking1)));
        assertEquals(clockingTestDao.getAll().size(), 1);

        Clocking clocking2 = new Clocking.Builder("TestClockingAfterReplace").build();
        repository.replace(clocking1, clocking2);
        assertTrue(clockingTestDao.getAll().contains(ClockingRepository.Mapper.map(clocking2)));
        assertEquals(clockingTestDao.getAll().size(), 1);
    }

    @Test
    public void testReplace_WithBothNulls(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("target or replacement cannot be null");

        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        repository.replace(null, null);
    }

    @Test
    public void testReplace_WithNullTarget(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("target or replacement cannot be null");

        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        Clocking clocking = new Clocking.Builder("TestClockingBeforeReplace").build();
        repository.replace(clocking, null);
    }

    @Test
    public void testReplace_WithNullReplacement(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("target or replacement cannot be null");

        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        Clocking clocking = new Clocking.Builder("TestClockingBeforeReplace").build();
        repository.replace(null, clocking);
    }

    @Test
    public void testReplace_WithMultipleRows(){
        ClockingRepository repository = new ClockingRepository(clockingTestDao);
        Clocking clocking = new Clocking.Builder("TestClocking1").build();
        Clocking clockingReplacement = new Clocking.Builder("ReplacementClocking").build();

        repository.insert(clocking);
        repository.insert(clocking);
        repository.insert(clocking);
        repository.insert(clocking);

        repository.replace(clocking, clockingReplacement);

        List<ClockingEntity> expectedList = new ArrayList<>();
        expectedList.add(ClockingRepository.Mapper.map(clockingReplacement));
        expectedList.add(ClockingRepository.Mapper.map(clocking));
        expectedList.add(ClockingRepository.Mapper.map(clocking));
        expectedList.add(ClockingRepository.Mapper.map(clocking));

        assertEquals(4, clockingTestDao.getAll().size());
        assertEquals(expectedList, clockingTestDao.getAll());
    }

}
