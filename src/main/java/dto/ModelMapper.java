package dto;

import java.lang.reflect.Field;

public class ModelMapper {
    public static <M, D> D modelToDTO(M model, Class<D> dtoClass){
        D dto = null;

        try{
            Class modelClass = model.getClass();
            Field[] dtoFields = dtoClass.getDeclaredFields();
            dto = dtoClass.getDeclaredConstructor().newInstance();

            for(Field field : dtoFields){
                String varName = field.getName();
                Field modelField = modelClass.getDeclaredField(varName);

                modelField.setAccessible(true);
                field.setAccessible(true);

                field.set(dto, modelField.get(model));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return dto;
    }

    public static <D, M> M DTOToModel(D dto, Class<M> modelClass){
        M model = null;

        try{
            Class dtoClass = dto.getClass();
            Field[] modelFields = modelClass.getDeclaredFields();
            model = modelClass.getDeclaredConstructor().newInstance();

            for(Field field : modelFields){
                String varName = field.getName();
                Field dtoField = dtoClass.getDeclaredField(varName);

                dtoField.setAccessible(true);
                field.setAccessible(true);

                field.set(model, dtoField.get(dto));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return model;
    }
}
