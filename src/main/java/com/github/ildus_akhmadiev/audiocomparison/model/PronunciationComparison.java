package com.github.ildus_akhmadiev.audiocomparison.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pronunciation_comparisons")
public class PronunciationComparison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('pronunciation_comparisons_id_seq'")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_file_id")
    private AudioFile audioFile;

    @Column(name = "comparison_score")
    private Double comparisonScore;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "comparison_date")
    private Instant comparisonDate;

}