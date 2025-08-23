package org.skypro.teamWork2.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecommendationTelegramBot implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(RecommendationTelegramBot.class);
    private RecommendationsRepository repository;

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    public RecommendationTelegramBot(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public int process(List<Update> updates) {
        
    }
}
