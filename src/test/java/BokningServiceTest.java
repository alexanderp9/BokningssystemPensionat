import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BokningServiceTest {

    @Mock
    private bokningRepo mockBokningRepo;

    @Mock
    private kundRepo mockKundRepo;

    @Mock
    private rumRepo mockRumRepo;

    @InjectMocks
    private BokningService bokningService;


    public BokningServiceTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date parsedStartDate = dateFormat.parse("2024-05-01");
    Date startDate = new Date(parsedStartDate.getTime());
    java.util.Date parsedEndDate = dateFormat.parse("2024-05-04");
    Date endDate = new Date(parsedEndDate.getTime());
    @Test
    public void testConvertToEntity() {

        BokningDTO bokningDTO = new BokningDTO();
        bokningDTO.setId(1L);
        bokningDTO.setNätter(3);
        bokningDTO.setStart(startDate);
        bokningDTO.setEnd(endDate);
        bokningDTO.setKundId(1L);
        bokningDTO.setRumId(1L);
        bokningDTO.setAvbokad(false);


        Kund mockKund = new Kund();
        mockKund.setId(1L);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(mockKund));

        Rum mockRum = new Rum();
        mockRum.setId(1L);
        when(mockRumRepo.findById(1L)).thenReturn(Optional.of(mockRum));


        Bokning result = bokningService.convertToEntity(bokningDTO);


        assertEquals(bokningDTO.getId(), result.getId());
        assertEquals(bokningDTO.getNätter(), result.getNätter());
        assertEquals(bokningDTO.getStart(), result.getStart());
        assertEquals(bokningDTO.getEnd(), result.getEnd());
        assertEquals(mockKund, result.getKund());
        assertEquals(mockRum, result.getRum());
        assertEquals(bokningDTO.isAvbokad(), result.isAvbokad());
    }

    @Test
    public void testGetAllBokningDTOs() {

        List<Bokning> bokningar = new ArrayList<>();


        when(mockBokningRepo.findAll()).thenReturn(bokningar);


        List<BokningDTO> result = bokningService.getAllBokningDTOs();


        assertEquals(bokningar.size(), result.size());

    }

    @Test
    public void testFindBokningDTOById_ExistingId_ReturnsDTO() {

        Long id = 1L;
        Bokning mockBokning = mock(Bokning.class);
        when(mockBokningRepo.findById(id)).thenReturn(Optional.of(mockBokning));


        BokningDTO result = bokningService.findBokningDTOById(id);


        assertNotNull(result);

    }

    @Test
    public void testAddBokning_ValidDTO_ReturnsDTO() {

        BokningDTO bokningDTO = new BokningDTO();


        when(mockKundRepo.findById(anyLong())).thenReturn(Optional.of(new Kund()));
        when(mockRumRepo.findById(anyLong())).thenReturn(Optional.of(new Rum()));
        when(mockBokningRepo.save(any())).thenReturn(new Bokning());


        BokningDTO result = bokningService.addBokning(bokningDTO);


        assertNotNull(result);

    }

    @Test
    public void testDeleteBokning() {

        long bokningId = 1L;


        doNothing().when(mockBokningRepo).deleteById(bokningId);


        bokningService.deleteBokning(bokningId);


        verify(mockBokningRepo, times(1)).deleteById(bokningId);
    }

    @Test
    public void testUpdateBokning() {

        long bokningId = 1L;
        Bokning updatedBokning = new Bokning();
        updatedBokning.setId(bokningId);



        when(mockBokningRepo.findById(bokningId)).thenReturn(Optional.of(new Bokning()));
        when(mockBokningRepo.save(any(Bokning.class))).thenReturn(updatedBokning);


        BokningDTO updatedBokningDTO = new BokningDTO();

        BokningDTO result = bokningService.updateBokning(bokningId, updatedBokningDTO);


        verify(mockBokningRepo, times(1)).findById(bokningId);
        verify(mockBokningRepo, times(1)).save(any(Bokning.class));
    }

    @Test
    public void testSearchAvailableRooms() {
        // Arrange
        Date startDate = Date.valueOf(LocalDate.of(2024, 5, 1));
        Date endDate = Date.valueOf(LocalDate.of(2024, 5, 4));

        Rum availableRoom = new Rum();
        availableRoom.setId(1L);

        List<Rum> availableRooms = new ArrayList<>();
        availableRooms.add(availableRoom);

        when(mockRumRepo.findAll()).thenReturn(availableRooms);

        when(mockBokningRepo.findByRumAndStartLessThanEqualAndEndGreaterThanEqual(availableRoom, endDate, startDate))
                .thenReturn(new ArrayList<>());


        List<Rum> result = bokningService.searchAvailableRooms(startDate, endDate);

        assertEquals(1, result.size());
        assertEquals(availableRoom, result.get(0));
    }

}
