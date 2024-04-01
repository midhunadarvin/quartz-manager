package it.fabioformosa;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Paths;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = QuartzManagerDemoApplication.class)
@WebAppConfiguration
class QuartManagerApplicationTests {

  @Test
  void contextLoads() {
  }

  @MockBean()
  private SpringLiquibase springLiquibase;

  @MockBean(name = "entityManagerFactory")
  private Object hiberate;

  @MockBean()
  private PlatformTransactionManager platformTransactionManager;

  @Test
  public void givenPythonScriptEngineIsAvailable_whenScriptInvoked_thenOutputDisplayed() throws Exception {
    StringWriter writer = new StringWriter();
    ScriptContext context = new SimpleScriptContext();
    context.setWriter(writer);

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("python");
    URL resource = getClass().getClassLoader().getResource("hello.py");
    engine.eval(new FileReader(Paths.get(resource.toURI()).toFile()), context);
    assertEquals("Should contain script output: ", "Hello Baeldung Readers!!", writer.toString().trim());
  }

}
