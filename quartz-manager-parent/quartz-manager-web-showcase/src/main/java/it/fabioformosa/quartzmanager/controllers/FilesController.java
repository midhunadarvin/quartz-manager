package it.fabioformosa.quartzmanager.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.fabioformosa.quartzmanager.api.common.config.OpenAPIConfigConsts;
import it.fabioformosa.quartzmanager.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@SecurityRequirement(name = OpenAPIConfigConsts.QUARTZ_MANAGER_SEC_OAS_SCHEMA)
@RequestMapping("/files")
public class FilesController {

  @Autowired
  private FileService fileService;

  public FilesController() {
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

    try {
      fileService.uploadFile(file);
      // Return a success message
      return ResponseEntity.ok("File uploaded successfully!");
    } catch (Exception e) {
      // Handle file IO exceptions
      System.out.println(e.getMessage());
      return ResponseEntity.status(500).body("Failed to upload the file");
    }
  }
}
