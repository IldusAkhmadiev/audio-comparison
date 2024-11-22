package com.github.ildus_akhmadiev.audiocomparison.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "audio_files")
public class AudioFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('audio_files_id_seq'") // Возможно, вам стоит исправить этот момент, так как скобка не закрыта
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @Size(max = 255)
    @NotNull
    @Column(name = "filepath", nullable = false)
    private String filepath;

    @Column(name = "upload_date")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant uploadDate;

    @Lob // Используем @Lob для хранения больших бинарных данных
    @Column(name = "audio_data")
    private byte[] audioData; // Массив байтов для хранения самого аудио
}