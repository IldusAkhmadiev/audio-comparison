package com.github.ildus_akhmadiev.audiocomparison.controller;

import com.github.ildus_akhmadiev.audiocomparison.model.AudioFile;
import com.github.ildus_akhmadiev.audiocomparison.repository.AudioFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class RestAudioController {

    @Autowired
    private AudioFileRepository audioFileRepository; // Репозиторий для работы с базой данных

    @PostMapping
    @Transactional
    public ResponseEntity<String> uploadAudio(@RequestParam("audio") MultipartFile audioFile) {
        try {
            // Получаем аудиофайл как byte[]
            byte[] audioBytes = audioFile.getBytes();

            // Создаем объект AudioFile и устанавливаем данные
            AudioFile audio = new AudioFile();
            audio.setFilename(audioFile.getOriginalFilename());
            audio.setFilepath("/path/to/saved/file");  // Если нужно сохранить путь
            audio.setUploadDate(Instant.now());
            audio.setAudioData(audioBytes);

            // Сохраняем аудиофайл в базу данных
            audioFileRepository.save(audio);

            return ResponseEntity.ok("File uploaded and saved in database successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    // Пример извлечения аудиофайла по ID
    @GetMapping("/audio/{id}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Long id) {
        AudioFile audioFile = audioFileRepository.findById(id).orElse(null);
        if (audioFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Type", "audio/wav")
                .body(audioFile.getAudioData());
    }

    @GetMapping
    public List<AudioFile> getAllAudioFiles() {
        return audioFileRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAudioFile(@PathVariable Long id) {
        if (audioFileRepository.existsById(id)) {
            audioFileRepository.deleteById(id);
            return ResponseEntity.ok("Audio file deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Audio file not found.");
        }
    }
}