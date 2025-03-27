import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.javeriana.proyecto.proyecto.dto.SolicitudDTO;
import com.javeriana.proyecto.proyecto.entidades.*;
import com.javeriana.proyecto.proyecto.exception.NotFoundException;
import com.javeriana.proyecto.proyecto.repositorios.*;
import com.javeriana.proyecto.proyecto.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;
    @Mock
    private ArrendadorRepository arrendadorRepository;
    @Mock
    private FincaRepository fincaRepository;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SolicitudService solicitudService;

    private Solicitud solicitud;
    private SolicitudDTO solicitudDTO;
    private Arrendador arrendador;
    private Finca finca;

    @BeforeEach
    void setUp() {
        arrendador = new Arrendador();
        arrendador.setId(1L);

        finca = new Finca();
        finca.setId(1L);
        finca.setValorNoche(100.0);

        solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setArrendador(arrendador);
        solicitud.setFinca(finca);
        solicitud.setFechasolicitud(LocalDateTime.now());
        
        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(1L);
        solicitudDTO.setIdArrendador(1L);
        solicitudDTO.setIdFinca(1L);
        solicitudDTO.setFechallegada(LocalDateTime.now());
        solicitudDTO.setFechasalida(LocalDateTime.now().plusDays(5));
    }

    @Test
    void testGetSolicitudById() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, SolicitudDTO.class)).thenReturn(solicitudDTO);

        SolicitudDTO result = solicitudService.get(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetSolicitudById_NotFound() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> solicitudService.get(1L));
    }

    @Test
    void testGetAllSolicitudes() {
        when(solicitudRepository.findAll()).thenReturn(Arrays.asList(solicitud));
        when(modelMapper.map(solicitud, SolicitudDTO.class)).thenReturn(solicitudDTO);

        List<SolicitudDTO> result = solicitudService.get();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testSaveSolicitud() {
        when(arrendadorRepository.findById(1L)).thenReturn(Optional.of(arrendador));
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        when(modelMapper.map(solicitudDTO, Solicitud.class)).thenReturn(solicitud);
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);
        when(modelMapper.map(solicitud, SolicitudDTO.class)).thenReturn(solicitudDTO);

        SolicitudDTO result = solicitudService.save(solicitudDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDeleteSolicitud() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));

        assertDoesNotThrow(() -> solicitudService.delete(1L));
        verify(solicitudRepository, times(1)).save(solicitud);
    }
}
