package com.github.ildus_akhmadiev.audiocomparison.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "audio_transcriptions")
public class AudioTranscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('audio_transcriptions_id_seq'")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_file_id")
    private AudioFile audioFile;

    @Column(name = "transcription", length = Integer.MAX_VALUE)
    private String transcription;

}