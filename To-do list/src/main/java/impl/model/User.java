package impl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

}

