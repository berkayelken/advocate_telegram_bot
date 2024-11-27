package io.github.berkayelken.advocate.telegram.bot.repository;

import io.github.berkayelken.advocate.telegram.bot.domain.ApproveAction;
import io.github.berkayelken.advocate.telegram.bot.domain.ApprovingRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovingRecordRepository extends CrudRepository<ApprovingRecord, String> {
	ApprovingRecord findByEmailAndCodeAndType(String email, String code, ApproveAction type);
}
