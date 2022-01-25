package com.perseus.api.dto.requests;

import com.perseus.api.models.Email;
import com.perseus.api.models.PhoneNumber;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class RegisterRequest {
    @NotEmpty
    @Size(min = 2, message = "FirstName should have at least 2 characters")
    public String firstName;

    @NotEmpty
    @Size(min = 2, message = "LastName should have at least 2 characters")
    public String lastName;

    @NotEmpty
    public List<Email> emails;

    @NotEmpty
    public List<PhoneNumber> phoneNumbers ;
}
