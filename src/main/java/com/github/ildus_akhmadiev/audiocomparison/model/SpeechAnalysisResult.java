package com.github.ildus_akhmadiev.audiocomparison.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "speech_analysis_results")
public class SpeechAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('speech_analysis_results_id_seq'")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_file_id")
    private AudioFile audioFile;

    @Column(name = "analysis_score")
    private Double analysisScore;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "analysis_date")
    private Instant analysisDate;

}