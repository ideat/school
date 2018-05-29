package mindware.com.mapper;

import mindware.com.model.Parameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParameterMapper {
    void insertParameter(Parameter parameter);
    void updateParameter(Parameter parameter);
    List<Parameter> findParameterByType(@Param("typeParameter") String typeParameter);
    Parameter findValueParameterByTypeAndName(@Param("typeParameter") String typeParameter, @Param("descriptionParameter") String descriptionParameter);
    int countParameterByNameAndType(@Param("typeParameter") String typeParameter, @Param("valueParameter") String valueParameter);
}
