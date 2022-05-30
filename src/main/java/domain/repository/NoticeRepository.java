package domain.repository;

import domain.model.Notice;
import infra.database.option.Option;

import java.util.List;

public interface NoticeRepository {

    Notice findByID(long id);
    List<Notice> findByOption(Option... options);
    long save(Notice notice);
    void remove(long accPk);
}
