package system.api.clinic.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

@SuppressWarnings("java:S6548")
@Mapper(componentModel = "spring")
public abstract class ResponseMapper {

    public static final ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    public abstract NewUserResponse toUserResponse(NewUserRequest request);

    public abstract NewAdminResponse toAdmResponse(NewAdminRequest request);

    public abstract NewDoctorResponse toDocResponse(NewDoctorRequest request);


}
