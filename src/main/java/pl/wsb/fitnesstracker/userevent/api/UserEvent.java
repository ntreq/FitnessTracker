package pl.wsb.fitnesstracker.userevent.api;

import jakarta.persistence.*;
import pl.wsb.fitnesstracker.event.api.Event;
import pl.wsb.fitnesstracker.training.api.Training;

@Entity
@Table(name = "user_events")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;



}
