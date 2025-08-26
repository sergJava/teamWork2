package org.skypro.teamWork2.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.skypro.teamWork2.model.Recommendation;
import org.skypro.teamWork2.model.RecommendationResponse;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.skypro.teamWork2.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecommendationTelegramBot implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(RecommendationTelegramBot.class);
    private final RecommendationService recommendationService;
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationTelegramBot(RecommendationService recommendationService, RecommendationsRepository recommendationsRepository) {
        this.recommendationService = recommendationService;
        this.recommendationsRepository = recommendationsRepository;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init(){
        telegramBot.setUpdatesListener(this);
    }

    String messageText;
    Long chatId;

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                String inputText = update.message().text();
                chatId = update.message().chat().id();

                if (inputText.startsWith("/recommend")) {
                    handleRecommendCCommand(chatId, inputText);
                } else {
                    sendTextMessage(chatId, "Привет, я бот рекомендаций, введи команду формата /recommend Ivan Ivanov");
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void handleRecommendCCommand(Long chatId, String command) {
        //парсим команду /recommend Ivan Ivanov
        String[] parts = command.split(" ", 3);
        if (parts.length < 3) {
            sendTextMessage(chatId, "некорректная команда");
            return;
        }
        String name = parts[1];
        String lastName = parts[2];

        UUID userId = recommendationsRepository.getUserIdByNameAndLastName(name, lastName);
        logger.info("userId = " + userId);

        StringBuilder messageText = new StringBuilder();
        messageText.append("Здравствуйте, ").append(name).append(" ").append(lastName).append("!\n");

        if (userId == null) {
            messageText.append("Пользователь не найден");
            sendTextMessage(chatId, messageText.toString());
            return;
        }

        messageText.append("Новые продукты для вас:").append("\n");

        RecommendationResponse recommendationResponse = recommendationService.getRecommendations(userId);
        List<Recommendation> recommendations = recommendationResponse.recommendations();

        if (recommendations.isEmpty()) {
            messageText.append("к сожалению, для вас нет персональных рекомендаций");
        } else {
            for (Recommendation rec : recommendations) {
                messageText.append("- ").append(rec.name()).append("\n");
                messageText.append("  ").append(rec.text()).append("\n\n");
            }
        }

        sendTextMessage(chatId, messageText.toString());
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            telegramBot.execute(message);
        } catch (RuntimeException e) {
            logger.error("failed to send message to chatId: " + chatId, e);
        }
    }
}
