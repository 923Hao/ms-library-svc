package com.system.ms.library.model;

import com.system.ms.library.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTO {

  @Null(message = "ID field must be null")
  private UUID id;
  @NotBlank(message = "Name field is required")
  @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Only alphanumeric characters are allowed")
  @Size(max = 100, message = "Name characters count could not be more than 100")
  private String name;
  @NotBlank(message = "Email field is required")
  @Email(message = "Email format is wrong")
  private String emailAddress;
  @Null(message = "Status field must be null")
  private Status status;
}
