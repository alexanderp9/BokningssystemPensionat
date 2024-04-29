import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.pensionatdb.dtos.BokningDTO;
import com.example.pensionatdb.models.Bokning;
import com.example.pensionatdb.models.Kund;
import com.example.pensionatdb.models.Rum;
import com.example.pensionatdb.repos.bokningRepo;
import com.example.pensionatdb.repos.kundRepo;
import com.example.pensionatdb.repos.rumRepo;
import com.example.pensionatdb.services.BokningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BokningServiceTest {

    @Mock
    private bokningRepo bokningRepo;

    @Mock
    private kundRepo kundRepo;

    @Mock
    private rumRepo rumRepo;

    @InjectMocks
    private BokningService bokningService;


    @BeforeEach
    public void setUp() {
        bokningRepo = mock(bokningRepo.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBokningarByKund() {

        Kund testKund = new Kund("Testperson", "Testgatan 123");


        List<Bokning> expectedBokningar = new ArrayList<>();



        when(bokningRepo.findByKund(testKund)).thenReturn(expectedBokningar);


        List<Bokning> actualBokningar = bokningService.findBokningarByKund(testKund);


        assertEquals(expectedBokningar, actualBokningar);

    }

    @Test
    public void testFindBokningarByKund_NullKund() {



        when(bokningRepo.findByKund(null)).thenReturn(new ArrayList<>());


        List<Bokning> actualBokningar = bokningService.findBokningarByKund(null);


        assertEquals(0, actualBokningar.size());
    }

    @Test
    public void testFindBokningarByKund_Exception() {



        when(bokningRepo.findByKund(any())).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class, () -> bokningService.findBokningarByKund(new Kund()));
    }




    @Test
    public void testAddBokning() {

        Bokning mockBokning = new Bokning();
        mockBokning.setId(1L);
        mockBokning.setNätter(3);
        mockBokning.setStartSlutDatum("2024-04-29");

        Kund mockKund = mock(Kund.class);
        Rum mockRum = mock(Rum.class);
        mockBokning.setKund(mockKund);
        mockBokning.setRum(mockRum);
        mockBokning.setAvbokad(false);


        when(bokningRepo.save(any(Bokning.class))).thenReturn(mockBokning);


        BokningDTO result = bokningService.addBokning(mockBokning);


        assertNotNull(result);
        assertEquals(mockBokning.getId(), result.getId());
    }


    @Test
    public void testDeleteBokning() {

        Long bokningId = 1L;


        bokningService.deleteBokning(bokningId);


        verify(bokningRepo, times(1)).deleteById(bokningId);
    }


    @Test
    public void testAvbokaBokning() {

        Long bokningId = 1L;
        Bokning mockBokning = new Bokning();
        mockBokning.setId(bokningId);


        when(bokningRepo.findById(bokningId)).thenReturn(java.util.Optional.of(mockBokning));


        bokningService.avbokaBokning(bokningId);


        assertTrue(mockBokning.isAvbokad());

        verify(bokningRepo, times(1)).save(mockBokning);
    }

    @Test
    void testIsRoomAvailable() {

        Rum rum = new Rum();
        String startDate = "220101";
        String endDate = "220103";


        List<Bokning> existingBookings = new ArrayList<>();
        existingBookings.add(new Bokning(1L, 2, "220102-220103", new Kund(), rum, false));


        when(bokningRepo.findByRumAndStartSlutDatumBetween(rum, startDate, endDate)).thenReturn(existingBookings);


        boolean isAvailable = bokningService.isRoomAvailable(rum, startDate + "-" + endDate);
        assertFalse(isAvailable);
    }

    @Test
    void testSearchAvailableRooms() {



        List<Rum> allRooms = new ArrayList<>();
        allRooms.add(new Rum());


        when(rumRepo.findAll()).thenReturn(allRooms);

        String startDate = "220101";
        String endDate = "220103";


        List<Rum> availableRooms = bokningService.searchAvailableRooms(startDate, endDate, 2);
        assertFalse(availableRooms.isEmpty());
    }

    /*@Test
    public void testUpdateBokning() {

        Long bokningId = 1L;
        BokningDTO updatedBokningDTO = new BokningDTO();
        updatedBokningDTO.setId(bokningId);
        updatedBokningDTO.setNätter(5);
        updatedBokningDTO.setStartSlutDatum("220501-220506");

        Bokning existingBokning = new Bokning();
        existingBokning.setId(bokningId);
        existingBokning.setNätter(3);
        existingBokning.setStartSlutDatum("220401-220404");

        Bokning updatedBokning = new Bokning();
        updatedBokning.setId(bokningId);
        updatedBokning.setNätter(5);
        updatedBokning.setStartSlutDatum("220501-220506");


        when(bokningRepo.findById(bokningId)).thenReturn(Optional.of(existingBokning));


        when(bokningRepo.save(any(Bokning.class))).thenReturn(updatedBokning);


        BokningDTO result = bokningService.updateBokning(bokningId, updatedBokningDTO);


        assertNotNull(result);
        assertEquals(bokningId, result.getId());
        assertEquals(5, result.getNätter());
        assertEquals("220501-220506", result.getStartSlutDatum());
    }*/
}