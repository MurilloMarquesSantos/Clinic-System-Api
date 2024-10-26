package system.api.clinic.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;

@SuppressWarnings("java:S6548")
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toUser(NewUserRequest request);

    public abstract User toUser(NewDoctorRequest request);

    public abstract User toUser(NewAdminRequest request);


}
