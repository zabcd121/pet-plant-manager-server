package application;

import domain.model.Diary;
import domain.repository.DiaryRepository;
import dto.ModelMapper;
import dto.DiaryDTO;
import infra.database.option.account.TokenOption;

import java.util.ArrayList;
import java.util.List;

public class PostAppService {

    private DiaryRepository postRepo;

    public PostAppService(DiaryRepository postRepo){
        this.postRepo = postRepo;
    }

    public DiaryDTO createPost(DiaryDTO diaryDTO){
        Diary diary = Diary.builder()
                            .petPk(diaryDTO.getPetPk())
                            .title(diaryDTO.getTitle())
                            .content(diaryDTO.getContent())
                            .postedDate(diaryDTO.getPostedDate())
                            .imgBytes(diaryDTO.getImgBytes())
                            .build();

        long postPK = postRepo.save(diary);

        diaryDTO.setPk(postPK);

        if(postPK>0){
            return diaryDTO;
        }else{
            return null;
        }
    }

    public void delete(DiaryDTO diaryDTO){
        Diary diary = postRepo.findByID(diaryDTO.getPk());
        postRepo.remove(diary);
    }

    public List<DiaryDTO> retrieveAll(String token){
        List<Diary> diaries = postRepo.findByOption(new TokenOption(token));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            postRepo.save(p);
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public List<DiaryDTO> retrieve(long petPK){
        List<Diary> diaries = postRepo.findByOption(new infra.database.option.petPlant.PKOption(petPK));
        List<DiaryDTO> diaryDTOList = new ArrayList<>();

        for(Diary p : diaries){
            postRepo.save(p);
            diaryDTOList.add(ModelMapper.modelToDTO(p, DiaryDTO.class));
        }

        return diaryDTOList;
    }

    public DiaryDTO update(DiaryDTO diaryDTO){
        Diary diary = postRepo.findByID(diaryDTO.getPk());

        diary.setTitle(diaryDTO.getTitle());
        diary.setContent(diaryDTO.getContent());
        diary.setImgBytes(diaryDTO.getImgBytes());
        diary.setPetPk(diaryDTO.getPetPk());
        diary.setPostedDate(diaryDTO.getPostedDate());

        postRepo.save(diary);

        return ModelMapper.modelToDTO(diary, DiaryDTO.class);
    }

}
