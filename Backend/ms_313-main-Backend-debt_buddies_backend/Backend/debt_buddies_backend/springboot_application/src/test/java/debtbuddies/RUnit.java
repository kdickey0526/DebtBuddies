package debtbuddies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
public class RUnit {

    @Test
    public void testReverse()  {
        // create an instance of SUT

        //check if it works by calling its methods
        assertEquals("olleh", "hi");

    }

}
