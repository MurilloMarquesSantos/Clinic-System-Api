package system.api.clinic.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import system.api.clinic.api.domain.Doctor;
import system.api.clinic.api.requests.NewDoctorRequest;

@SuppressWarnings("java:S6548")
@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    public static final DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    public abstract Doctor toDoctor(NewDoctorRequest request);
}
