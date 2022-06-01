package domain.repository;

import domain.model.Diary;
import infra.database.option.Option;

import java.util.List;

public interface DiaryRepository {

    Diary findByID(long id);
    List<Diary> findByOption(Option... options);
    long save(Diary diary);
    void remove(Diary diary);
}
