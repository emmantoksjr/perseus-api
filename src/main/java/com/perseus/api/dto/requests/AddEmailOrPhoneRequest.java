package com.perseus.api.dto.requests;

import com.perseus.api.models.Email;
import com.perseus.api.models.PhoneNumber;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddEmailOrPhoneRequest {
    //TODO: Validations
    public Long userId;

    public List<Email> emails;

    public List<PhoneNumber> phoneNumbers ;
}
