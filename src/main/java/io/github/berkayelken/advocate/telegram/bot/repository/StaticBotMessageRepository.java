package io.github.berkayelken.advocate.telegram.bot.repository;

import io.github.berkayelken.advocate.telegram.bot.domain.StaticBotMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticBotMessageRepository extends CrudRepository<StaticBotMessage, String> {
}
