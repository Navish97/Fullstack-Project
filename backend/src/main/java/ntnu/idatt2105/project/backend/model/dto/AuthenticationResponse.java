package ntnu.idatt2105.project.backend.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {


    @JsonIgnore
    private String token;
    private String errorMessage;
    private String userId;
    private String userRole;

}
