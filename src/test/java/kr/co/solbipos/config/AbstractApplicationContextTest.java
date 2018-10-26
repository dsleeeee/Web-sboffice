package kr.co.solbipos.config;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/java/resources/context-test.xml")
@WebAppConfiguration
public abstract class AbstractApplicationContextTest {

    @Autowired
    protected ApplicationContext ctx;

}
