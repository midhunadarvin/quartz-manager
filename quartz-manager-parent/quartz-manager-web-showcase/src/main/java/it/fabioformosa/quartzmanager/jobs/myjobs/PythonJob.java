package it.fabioformosa.quartzmanager.jobs.myjobs;

import it.fabioformosa.quartzmanager.api.jobs.AbstractQuartzManagerJob;
import it.fabioformosa.quartzmanager.api.jobs.entities.LogRecord;
import it.fabioformosa.quartzmanager.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.python.util.PythonInterpreter;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class PythonJob extends AbstractQuartzManagerJob {

  @Autowired
  FileService fileService;

  @Override
  public LogRecord doIt(JobExecutionContext jobExecutionContext) {

    try (PythonInterpreter pyInterp = new PythonInterpreter()) {

      // Set the script path
      String filePath = fileService.getUploadsDirectory() + "/" + jobExecutionContext.getTrigger().getJobDataMap().getString("file");
      String inputParams = jobExecutionContext.getTrigger().getJobDataMap().getString("inputParams");
      log.info("InputParams : " + inputParams);
      ProcessBuilder processBuilder = new ProcessBuilder("python3", filePath, inputParams);
      processBuilder.redirectErrorStream(true);

      Process process = processBuilder.start();
      // this reads from the subprocess's output stream
      BufferedReader subProcessInputReader =
        new BufferedReader(new InputStreamReader(process.getInputStream()));

      int exitCode = process.waitFor();

      StringBuilder result = new StringBuilder();
      String line = null;
      while ((line = subProcessInputReader.readLine()) != null)
        result.append(line + "\n");

      subProcessInputReader.close();

      return new LogRecord(LogRecord.LogType.INFO, result.toString().trim());
    } catch (Exception e) {
      log.info(e.getMessage());
      return new LogRecord(LogRecord.LogType.ERROR, e.getMessage());
    }
  }
}
