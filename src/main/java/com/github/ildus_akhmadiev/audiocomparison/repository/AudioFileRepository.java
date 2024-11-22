package com.github.ildus_akhmadiev.audiocomparison.repository;

import com.github.ildus_akhmadiev.audiocomparison.model.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
    // Можно добавить дополнительные методы для поиска или фильтрации аудиофайлов
}