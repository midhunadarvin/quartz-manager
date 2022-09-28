package it.fabioformosa.quartzmanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.fabioformosa.quartzmanager.dto.SchedulerDTO;
import it.fabioformosa.quartzmanager.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * This controller provides scheduler info about config and status. It provides
 * also methods to set new config and start/stop/resume the scheduler.
 *
 * @author Fabio.Formosa
 */
@Slf4j
@RestController
@SecurityRequirement(name = "basic-auth")
@RequestMapping("/quartz-manager/scheduler")
public class SchedulerController {

  private SchedulerService schedulerService;

  public SchedulerController(SchedulerService schedulerService, ConversionService conversionService) {
    this.schedulerService = schedulerService;
    this.conversionService = conversionService;
  }

  @Resource
  private ConversionService conversionService;

  @GetMapping
  @Operation(summary = "Get the scheduler details")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Return the scheduler config",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = SchedulerDTO.class)) })
  })
  public SchedulerDTO getScheduler() {
    log.trace("SCHEDULER - GET Scheduler...");
    return schedulerService.getScheduler();
  }

  @GetMapping("/pause")
  @Operation(summary = "Get paused the scheduler")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Got paused successfully")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void pause() throws SchedulerException {
    log.info("SCHEDULER - PAUSE COMMAND");
    schedulerService.standby();
  }

  @GetMapping("/resume")
  @Operation(summary = "Get resumed the scheduler")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Got resumed successfully")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resume() throws SchedulerException {
    log.info("SCHEDULER - RESUME COMMAND");
    schedulerService.start();
  }

  @GetMapping("/run")
  @Operation(summary = "Start the scheduler")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Got started successfully")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void run() throws SchedulerException {
    log.info("SCHEDULER - START COMMAND");
    schedulerService.start();
  }

  @GetMapping("/stop")
  @Operation(summary = "Stop the scheduler")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Got stopped successfully")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void stop() throws SchedulerException {
    log.info("SCHEDULER - STOP COMMAND");
    schedulerService.shutdown();
  }

}
