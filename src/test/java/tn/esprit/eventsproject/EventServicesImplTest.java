package tn.esprit.eventsproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import tn.esprit.eventsproject.entities.Event;

import tn.esprit.eventsproject.entities.Logistics;

import tn.esprit.eventsproject.entities.Participant;

import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;

import tn.esprit.eventsproject.repositories.LogisticsRepository;

import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;

import java.util.*;

public class EventServicesImplTest {

    @InjectMocks

    private EventServicesImpl eventServices;

    @Mock

    private EventRepository eventRepository;

    @Mock

    private ParticipantRepository participantRepository;

    @Mock

    private LogisticsRepository logisticsRepository;

    @BeforeEach

    public void setup() {

        MockitoAnnotations.openMocks(this);

    }

    @Test

    public void testAddParticipant() {

        Participant participant = new Participant();

        participant.setIdPart(1);

        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant savedParticipant = eventServices.addParticipant(participant);

        verify(participantRepository, times(1)).save(participant);

        assertEquals(participant.getIdPart(), savedParticipant.getIdPart());

    }


    @Test

    public void testAddAffectEvenParticipantWithExistingParticipantId() {

        Event event = new Event();

        event.setDescription("Test Event");

        Participant participant = new Participant();

        participant.setIdPart(1);

        participant.setEvents(new HashSet<>());

        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventServices.addAffectEvenParticipant(event, 1);

        verify(participantRepository, times(1)).findById(1);

        verify(eventRepository, times(1)).save(event);

        assertTrue(participant.getEvents().contains(event));

    }

    @Test

    public void testAddAffectEvenParticipantWithoutExistingParticipantId() {

        Event event = new Event();

        event.setDescription("Test Event");

        Participant participant = new Participant();

        participant.setIdPart(1);

        participant.setEvents(new HashSet<>());

        Set<Participant> participants = new HashSet<>();

        participants.add(participant);

        event.setParticipants(participants);

        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventServices.addAffectEvenParticipant(event);

        verify(eventRepository, times(1)).save(event);

        assertTrue(participant.getEvents().contains(event));

    }

    @Test

    public void testAddAffectLog() {

        Logistics logistics = new Logistics();

        logistics.setPrixUnit(100);

        logistics.setQuantite(2);

        Event event = new Event();

        event.setDescription("Test Event");

        event.setLogistics(new HashSet<>());

        when(eventRepository.findByDescription("Test Event")).thenReturn(event);

        when(logisticsRepository.save(any(Logistics.class))).thenReturn(logistics);

        Logistics savedLogistics = eventServices.addAffectLog(logistics, "Test Event");

        verify(eventRepository, times(1)).findByDescription("Test Event");

        verify(logisticsRepository, times(1)).save(logistics);

        assertTrue(event.getLogistics().contains(logistics));

    }

}