package com.github.ildus_akhmadiev.audiocomparison.controller;

import com.github.ildus_akhmadiev.audiocomparison.model.AudioFile;
import com.github.ildus_akhmadiev.audiocomparison.repository.AudioFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*")
public class RestAudioController {

    @Autowired
    private AudioFileRepository audioFileRepository; // Репозиторий для работы с базой данных

    @PostMapping
    @Transactional
    public ResponseEntity<String> uploadAudio(@RequestParam("audio") MultipartFile audioFile) {
        try {
            // Конвертация в WAV
            byte[] wavBytes = convertToWav(audioFile);

            AudioFile audio = new AudioFile();
            audio.setFilename(audioFile.getOriginalFilename().replaceAll("\\.[^.]+$", ".wav"));
            audio.setFilepath("/path/to/saved/file");
            audio.setUploadDate(Instant.now());
            audio.setAudioData(wavBytes);

            audioFileRepository.save(audio);

            return ResponseEntity.ok("File converted to WAV and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

    private byte[] convertToWav(MultipartFile audioFile) throws Exception {
        File inputFile = File.createTempFile("input", audioFile.getOriginalFilename());
        File outputFile = File.createTempFile("output", ".wav");

        try {
            audioFile.transferTo(inputFile);

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("pcm_s16le");
            audio.setBitRate(192000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("wav");
            attrs.setAudioAttributes(audio);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(inputFile), outputFile, attrs);

            return Files.readAllBytes(outputFile.toPath());
        } finally {
            inputFile.delete();
            outputFile.delete();
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

    // Получение всех аудиофайлов
    @GetMapping
    public List<AudioFile> getAllAudioFiles() {
        return audioFileRepository.findAll();
    }

    // Удаление аудиофайла по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAudioFile(@PathVariable Long id) {
        if (audioFileRepository.existsById(id)) {
            audioFileRepository.deleteById(id);
            return ResponseEntity.ok("Audio file deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Audio file not found.");
        }
    }

    // Метод для сравнения двух аудиофайлов по точности
    @PostMapping("/compare")
    public ResponseEntity<String> compareAudio(@RequestParam("userAudioId") Long userAudioId, @RequestParam("referenceAudioId") Long referenceAudioId) {
        AudioFile userAudio = audioFileRepository.findById(userAudioId).orElse(null);
        AudioFile referenceAudio = audioFileRepository.findById(referenceAudioId).orElse(null);

        if (userAudio == null || referenceAudio == null) {
            return ResponseEntity.status(404).body("One or both audio files not found.");
        }

        // Логика сравнения аудио
        double accuracy = compareAudioFiles(userAudio.getAudioData(), referenceAudio.getAudioData());
        return ResponseEntity.ok("Accuracy: " + accuracy + "%");
    }

    // Логика для сравнения двух аудиофайлов
    private double compareAudioFiles(byte[] userAudioData, byte[] referenceAudioData) {
        // Замените этот метод на фактическую логику сравнения аудио
        // Например, можно использовать алгоритмы для сравнения аудиофайлов на основе характеристик звука
        // Для упрощения, вернем фиксированное значение
        return 95.0; // Пример, вернем 95% точности
    }
}