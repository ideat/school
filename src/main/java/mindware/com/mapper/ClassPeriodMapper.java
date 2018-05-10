package mindware.com.mapper;

import mindware.com.model.ClassPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassPeriodMapper {
    void insertClassPeriod(ClassPeriod classPeriod);
    void updateClassPeriod(ClassPeriod classPeriod);
    List<ClassPeriod> findAllClassPeriod();
    Integer getCountClassPeriodByYear(@Param("year") String year);
    List<ClassPeriod> findClassPeriodByState(@Param("state") String state);

}
