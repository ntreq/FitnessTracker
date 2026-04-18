    package pl.wsb.fitnesstracker.workoutsession;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDateTime;


    @Entity
@Table(name = "workout_sessions")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(name="timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "startLatitude", nullable = false)
    private double startLatitude;

    @Column(name = "startLongitude", nullable = false)
    private double startLongitude;

    @Column(name = "endLatitude", nullable = true)
    private double endLatitude;

    @Column(name = "endLongitude", nullable = true)
    private double endLongitude;

    @Column(name = "altitude", nullable = false)
    private double altitude;

    public WorkoutSession(
                Training training,
                LocalDateTime timestamp,
                double startLatitude,
                double startLongitude,
                double endLatitude,
                double endLongitude,
                double altitude) {
        this.training = training;
        this.timestamp = timestamp;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.altitude = altitude;
    }

}
