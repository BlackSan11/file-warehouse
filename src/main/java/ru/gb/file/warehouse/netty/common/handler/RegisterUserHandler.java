package ru.gb.file.warehouse.netty.common.handler;

import ru.gb.file.warehouse.netty.common.dto.RegisterUserRequest;
import ru.gb.file.warehouse.netty.common.dto.RegisterUserResponse;

public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {
        //... логика регистрации
        return new RegisterUserResponse("OK");
    }
}
