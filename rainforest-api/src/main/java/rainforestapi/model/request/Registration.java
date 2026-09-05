package rainforestapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Registration {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    public Registration() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}package rainforestapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Registration {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    public Registration() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
// }package rainforestapi.model.request;

// import com.fasterxml.jackson.annotation.JsonProperty;

// public class Registration {
//     @JsonProperty("username") private final String username;
//     @JsonProperty("password") private final String password;
    
//     public Registration(@JsonProperty("username") String username, 
//         @JsonProperty("password") String password){
//             if(username.isEmpty() || password.isEmpty()){
//                 this.username = null;
//                 this.password = null;
//             }


//             this.username = username;
//             this.password = password;
//         }

//     public String getUsername() {
//         return username;
//     }

//     public String getPassword() {
//         return password;
//     }
// }
