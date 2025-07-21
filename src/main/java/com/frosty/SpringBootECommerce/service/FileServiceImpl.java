package com.frosty.SpringBootECommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
  @Override
  public String uploadFile(String path, MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    String randomId = UUID.randomUUID().toString();
    String actualFileName = randomId.concat(fileName.substring(fileName.lastIndexOf('.')));

    String filePath = path + File.separator + actualFileName;
    File directory = new File(path);
    if (!directory.exists()) {
      directory.mkdirs();
    }

    Files.copy(file.getInputStream(), Path.of(filePath));
    return actualFileName;
  }
}
