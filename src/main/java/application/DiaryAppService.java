package application;

import domain.model.Diary;
import domain.repository.DiaryRepository;
import dto.ModelMapper;
import dto.DiaryDTO;
import infra.database.option.account.TokenOption;

import java.util.ArrayList;
import java.util.List;

public class DiaryAppService {

    private DiaryRepository diaryRepo;

    public DiaryAppService(DiaryRepository postRepo){
        this.diaryRepo = postRepo;
    }

    public DiaryDTO createDiary(DiaryDTO diaryDTO){
        Diary diary = Diary.builder()
                            .petPlantPK(diaryDTO.getPetPlantPK())
                            .userPK(diaryDTO.getUserPK())
                            .title(diaryDTO.getTitle())
                            .content(diaryDTO.getContent())
                            .date(diaryDTO.getDate())
                            .diaryImg(diaryDTO.getDiaryImg())
                            .build();

        long postPK = diaryRepo.save(diary);

        diaryDTO.setPk(postPK);

        if(postPK>0){
            return diaryDTO;
        }else{
            return null;
        }
    }

    public void delete(DiaryDTO diaryDTO){
        Diary diary = diaryRepo.findByID(diaryDTO.getPk());
        diaryRepo.remove(diary);
    }

    public List<DiaryDTO> retrieveAll(String token){
        List<Diary> diaries = diaryRepo.findByOption(new TokenOption(token));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            diaryRepo.save(p);
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public List<DiaryDTO> retrieve(long petPK){
        List<Diary> diaries = diaryRepo.findByOption(new infra.database.option.petPlant.PKOption(petPK));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            diaryRepo.save(p);
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public DiaryDTO update(DiaryDTO diaryDTO){
        Diary diary = diaryRepo.findByID(diaryDTO.getPk());

        diary.setTitle(diaryDTO.getTitle());
        diary.setContent(diaryDTO.getContent());
        diary.setDiaryImg(diaryDTO.getDiaryImg());
        diary.setPetPlantPK(diaryDTO.getPetPlantPK());
        diary.setDate(diaryDTO.getDate());

        diaryRepo.save(diary);

        return ModelMapper.modelToDTO(diary, DiaryDTO.class);
    }

}
