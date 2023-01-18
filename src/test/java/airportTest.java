import org.airport.module.Airport;
import org.junit.Assert;
import org.junit.Test;

public class airportTest {

    @Test
    public void add_plain_to_landingQueue() {
        Assert.assertEquals("Not able to get planes to land", true, Airport.createLanding());
    }

    @Test
    public void make_queue() {
        Assert.assertEquals("Queue not made", true, Airport.createQueue());
    }

}
