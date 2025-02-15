import com.campmaster.modelo.Entities.*;
import com.campmaster.modelo.extra.EntityMapper;
import org.junit.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class MapperTests {

    @Test
    public void testUserMapping() throws SQLException {
        ResultSet mock=mock(ResultSet.class);
        when(mock.getObject("dni")).thenReturn("12345678A");
        when(mock.getObject("Nombre")).thenReturn("Juan");
        when(mock.getObject("Correo")).thenReturn("juan@unileon.es");
        when(mock.getObject("Contraseña")).thenReturn("AbcDE_134$$");
        when(mock.getObject("tipo")).thenReturn("Monitor");
        when(mock.getObject("NombreTutor")).thenReturn("Pepe");
        when(mock.getObject("NumeroTutor")).thenReturn("666666666");
        when(mock.getObject("Apellidos")).thenReturn("Fernandez");

        User user = EntityMapper.mapRsToClass(mock, User.class);
        Assert.assertEquals("12345678A", user.getDni());
        Assert.assertEquals("Juan", user.getNombre());
        Assert.assertEquals("Fernandez", user.getApellidos());
        Assert.assertEquals("juan@unileon.es", user.getCorreo());
        Assert.assertEquals("Monitor", user.getTipo());
    }

    @Test
    public void testEventMapping() throws SQLException {
        ResultSet mock = mock(ResultSet.class);
        when(mock.getObject("id")).thenReturn(1);
        when(mock.getObject("nombre")).thenReturn("Evento1");
        when(mock.getObject("descripcion")).thenReturn("Descripcion del evento 1");
        when(mock.getObject("fecha_inicio")).thenReturn(Date.valueOf("2021-06-01"));
        when(mock.getObject("fecha_fin")).thenReturn(Date.valueOf("2021-06-15"));
        when(mock.getObject("max_participantes")).thenReturn(20);
        when(mock.getObject("lugar")).thenReturn("Leon");

        Event event = EntityMapper.mapRsToClass(mock, Event.class);
        Assert.assertNotNull(event);
        Assert.assertEquals(1, event.getId());
        Assert.assertEquals("Evento1", event.getNombre());
        Assert.assertEquals("Descripcion del evento 1", event.getDescripcion());
        Assert.assertEquals("2021-06-01", event.getFecha_inicio().toString());
        Assert.assertEquals("2021-06-15", event.getFecha_fin().toString());
        Assert.assertEquals(20, event.getMax_participantes());
        Assert.assertEquals("Leon", event.getLugar());
    }

    @Test
    public void testItemMapping() throws SQLException {
        ResultSet mock = mock(ResultSet.class);
        when(mock.getObject("id")).thenReturn(1);
        when(mock.getObject("nombre")).thenReturn("Item1");
        when(mock.getObject("marca")).thenReturn("Marca1");
        when(mock.getObject("cantidad")).thenReturn(10);

        Item item = EntityMapper.mapRsToClass(mock, Item.class);
        Assert.assertNotNull(item);
        Assert.assertEquals(1, item.getId());
        Assert.assertEquals("Item1", item.getNombre());
        Assert.assertEquals("Marca1", item.getMarca());
        Assert.assertEquals(10, item.getCantidad());
    }

    @Test
    public void testDocumentMapping() throws SQLException {
        ResultSet mock = mock(ResultSet.class);
        when(mock.getObject("name")).thenReturn("Documento1");
        when(mock.getObject("URL")).thenReturn("Descripcion del documento 1");
        when(mock.getObject("date")).thenReturn(Date.valueOf("2021-06-01"));
        when(mock.getObject("ID_Usuario")).thenReturn("12345678A");

        Document document = EntityMapper.mapRsToClass(mock, Document.class);
        Assert.assertNotNull(document);
        Assert.assertEquals("Documento1", document.getName());
        Assert.assertEquals(Date.valueOf("2021-06-01"), document.getDate());
        Assert.assertEquals("12345678A", document.getID_Usuario());
    }

    @Test
    public void testBookingMapping() throws SQLException {
        ResultSet mock = mock(ResultSet.class);
        when(mock.getObject("idReserva")).thenReturn(1);
        when(mock.getObject("idUser")).thenReturn("12345678A");
        when(mock.getObject("idEvento")).thenReturn(1);

        Booking booking = EntityMapper.mapRsToClass(mock, Booking.class);
        Assert.assertNotNull(booking);
        Assert.assertEquals(1, booking.getId());
        Assert.assertEquals("12345678A", booking.getIdUser());
        Assert.assertEquals(1, booking.getIdEvento());
    }

}
